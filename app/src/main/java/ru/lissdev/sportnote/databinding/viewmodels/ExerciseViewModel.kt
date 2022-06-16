package ru.lissdev.sportnote.databinding.viewmodels

import androidx.annotation.MainThread
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ru.lissdev.sportnote.BR
import ru.lissdev.sportnote.R
import ru.lissdev.sportnote.data.db.ExercisesRepository
import ru.lissdev.sportnote.data.model.ExerciseDetails
import ru.lissdev.sportnote.data.model.ExerciseEquipment
import ru.lissdev.sportnote.data.model.ExerciseImages
import ru.lissdev.sportnote.data.model.ExerciseMuscle
import ru.lissdev.sportnote.data.network.wger.WgerClient
import ru.lissdev.sportnote.databinding.*
import javax.inject.Inject

// TODO: починить множественный вызов https://wger.de/api/v2/exerciseinfo/{id}
@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {
    private var exerciseId = ObservableLong(0L)

    private val compositeDisposable = CompositeDisposable()

    var itemData: ObservableField<ExerciseDetails> = exerciseId.toRx()
        .flatMapSingle { id ->
            requestExercise(id)
                .doOnError {
                    println(it.message)
                }
        }.toBinding()

    val equipmentBinding =
        ItemBinding.of<ExerciseEquipment>(BR.exercise_equipment, R.layout.item_equipment)

    val musclesBinding =
        ItemBinding.of<ExerciseMuscle>(BR.exercise_muscle, R.layout.item_muscles)

    val imagesBinding =
        ItemBinding.of<ExerciseImages>(BR.exercise_images, R.layout.item_images)

    val equipmentData = itemData.toRx()
        .map { it.equipment ?: emptyList() }
        .toListBinding()

    val musclesData = itemData.toRx()
        .map { it.muscles ?: emptyList() }
        .toListBinding()

    val secondaryMusclesData = itemData.toRx()
        .map { it.muscles_secondary ?: emptyList() }
        .toListBinding()

    val imagesData = itemData.toRx()
        .map { it.images ?: emptyList() }
        .toListBinding()

    val isSaved: ObservableBoolean = ObservableBoolean(false)

    val loading: ObservableBoolean = ObservableBoolean(false)

    fun requestExercise(id: Long): Single<ExerciseDetails> =
        WgerClient.getExercise(id)
            .subscribeOn(Schedulers.io())
            .loading(loading)
            .retryWithDelay()

    fun updateExercise(exerciseId: Long) {
        if (this.exerciseId.value != exerciseId) {
            this.exerciseId.set(exerciseId)
        }
        isSaved.set(false)
    }

    fun saveExercise() {
        itemData.get()?.let { exercise ->
            compositeDisposable.add(
                exercisesRepository.insertExercise(exercise)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            isSaved.set(true)
                        },
                        {
                            println(it.message)
                        }
                    )
            )
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
