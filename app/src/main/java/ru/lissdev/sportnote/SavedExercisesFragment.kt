package ru.lissdev.sportnote

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.lissdev.sportnote.databinding.FragmentSavedExercisesBinding
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