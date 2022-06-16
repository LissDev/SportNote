package ru.lissdev.sportnote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.lissdev.sportnote.databinding.FragmentExercisesCategoriesBinding
import ru.lissdev.sportnote.databinding.FragmentSavedExercisesBinding
import ru.lissdev.sportnote.databinding.viewmodels.ExercisesCategoriesViewModel
import ru.lissdev.sportnote.databinding.viewmodels.SavedExercisesViewModel
import ru.lissdev.sportnote.ui.base.BaseFragment

@AndroidEntryPoint
class SavedExercisesFragment :
    BaseFragment<FragmentSavedExercisesBinding>(R.layout.fragment_saved_exercises) {

    private val vm by viewModels<SavedExercisesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = vm
    }
}