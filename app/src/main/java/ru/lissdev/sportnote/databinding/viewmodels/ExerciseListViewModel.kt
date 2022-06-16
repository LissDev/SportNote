package ru.lissdev.sportnote.databinding.viewmodels

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ru.lissdev.sportnote.BR
import ru.lissdev.sportnote.R
import ru.lissdev.sportnote.data.model.Exercise
import ru.lissdev.sportnote.data.model.ExercisesListPage
import ru.lissdev.sportnote.data.network.wger.WgerClient
import ru.lissdev.sportnote.databinding.*


class ExerciseListViewModel : ViewModel() {

    private var categoryId = ObservableLong(0L)

    val binding =
        ItemBinding.of<Exercise>(BR.exercise, R.layout.item_exercise)
            .bindExtra(BR.vm, this)

    val loading: ObservableBoolean = ObservableBoolean(false)

    val list: RxObservableList<Exercise> = categoryId.toRx()
        .flatMapSingle { category ->
            requestPage(category)
        }.map { it.results }
        .toListBinding()

    fun requestPage(categoryId: Long): Single<ExercisesListPage> =
        WgerClient.getExercisesByCategories(categoryId)
            .subscribeOn(Schedulers.io())
            .loading(loading)
            .retryWithDelay()

    fun processDetails(view: View, image: ImageView) {
        view.visibility = if (view.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        image.setImageResource(if (view.visibility == View.VISIBLE) R.drawable.svg_button_up else R.drawable.svg_button_down)
    }

    fun onNextView(view: View, id: Long) {
        val bundle = Bundle().apply {
            putLong("exerciseId", id)
        }
        findNavController(view).navigate(R.id.action_exerciseListFragment_to_exerciseFragment, bundle)
    }

    fun updateList(categoryId: Long) {
        if (this.categoryId.value != categoryId) {
            this.categoryId.set(categoryId)
        }
    }
}
