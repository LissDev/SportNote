package ru.lissdev.sportnote.databinding.viewmodels

import android.os.Bundle
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ru.lissdev.sportnote.BR
import ru.lissdev.sportnote.R
import ru.lissdev.sportnote.data.model.ExercisesCategoriesPage
import ru.lissdev.sportnote.data.model.ExercisesCategory
import ru.lissdev.sportnote.data.network.wger.WgerClient
import ru.lissdev.sportnote.databinding.*

class ExercisesCategoriesViewModel() : ViewModel() {

    val binding =
        ItemBinding.of<ExercisesCategory>(BR.exercises_category, R.layout.item_exercises_category)
            .bindExtra(BR.vm, this)

    val loading: ObservableBoolean = ObservableBoolean(false)

    val list: RxObservableList<ExercisesCategory> = Flowable
        .fromSingle(
            updateList()
        )
        .map { it.results }
        .toListBinding()

    private fun updateList(): Single<ExercisesCategoriesPage> = WgerClient.getExercisesCategories()
        .subscribeOn(Schedulers.io())
        .loading(loading)
        .retryWithDelay()


    fun onNextView(view: View, categoryId: Long) {
        val bundle = Bundle().apply {
            putLong("categoryId", categoryId)
        }
        findNavController(view).navigate(R.id.action_exercisesCategoriesFragment_to_exerciseListFragment, bundle)
    }
}
