package ru.lissdev.sportnote.databinding

import android.view.View
import androidx.annotation.IdRes
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import ru.lissdev.sportnote.R
import java.util.concurrent.TimeUnit

inline fun <reified T : Any> View.lazy(
    @IdRes id: Int,
    create: () -> T,
    onCreated: (T) -> Unit = {}
): T = (getTag(id) as? T) ?: create().also { onCreated(it) }.also { setTag(id, it) }

val View.binding: BindingsCache
    get() = lazy(
        R.id.binding,
        create = { BindingsCache(this) },
        onCreated = ::addOnAttachStateChangeListener
    )

fun <V : View, DATA : Any> V.addBinding(flowable: Flowable<DATA>, setter: V.(DATA) -> Unit) {
    binding.addBinding(object : Binding<V, DATA>(flowable, this) {
        override fun V.set(data: DATA) = setter(data)
    })
}

// ------ Rx
fun <T : Any> ObservableField<T>.toRx(): Flowable<T> = Flowable.create<T>({ emitter ->
    val callback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            get()?.also(emitter::onNext)
        }
    }
    addOnPropertyChangedCallback(callback)
    emitter.setCancellable { removeOnPropertyChangedCallback(callback) }
    get()?.also(emitter::onNext)
}, BackpressureStrategy.LATEST)

fun <T : Any> Flowable<T>.toBinding() = RxObservableField(this)

fun <T : Any> Single<T>.toBinding() = toFlowable().toBinding()

fun <T : Any> Flowable<List<T>>.toListBinding() = RxObservableList(this)

fun <T: Any> Single<T>.loading(observable: ObservableBoolean) =
    doOnSubscribe { observable.set(true) }.doOnTerminate { observable.set(false) }

fun <T : Any> Single<T>.retryWithDelay(
    delay: Long = 5000L,
    filter: (Throwable) -> Boolean = { true }
): Single<T> {
    return retryWhen { errorThrowable ->
        errorThrowable.flatMap {
            if (filter(it)) {
                Flowable.timer(delay, TimeUnit.MILLISECONDS)
            } else {
                Flowable.error(it)
            }
        }
    }
}
// ------