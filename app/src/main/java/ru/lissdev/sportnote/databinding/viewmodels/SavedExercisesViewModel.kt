package ru.lissdev.sportnote.databinding.viewmodels

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ru.lissdev.sportnote.BR
import ru.lissdev.sportnote.R
import ru.lissdev.sportnote.data.db.ExercisesRepository
import ru.lissdev.sportnote.data.model.*
import ru.lissdev.sportnote.databinding.*
import javax.inject.Inject

@HiltViewModel
class SavedExercisesViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {

    val binding =
        ItemBinding.of<ExerciseDetails>(BR.exercise, R.layout.item_exercise_offline)
            .bindExtra(BR.vm, this)

    val equipmentBinding =
        ItemBinding.of<ExerciseEquipment>(BR.exercise_equipment, R.layout.item_equipment)

    val musclesBinding =
        ItemBinding.of<ExerciseMuscle>(BR.exercise_muscle, R.layout.item_muscles)

    val list: RxObservableList<ExerciseDetails> = exercisesRepository.getExercises()
        .toFlowable()
        .toListBinding()

    fun processDetails(view: View, image: ImageView) {
        view.visibility = if (view.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        image.setImageResource(if (view.visibility == View.VISIBLE) R.drawable.svg_button_up else R.drawable.svg_button_down)
    }
}
