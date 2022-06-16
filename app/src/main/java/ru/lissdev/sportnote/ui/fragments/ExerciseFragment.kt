package ru.lissdev.sportnote.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.lissdev.sportnote.R
import ru.lissdev.sportnote.databinding.FragmentExerciseBinding
import ru.lissdev.sportnote.databinding.viewmodels.ExerciseViewModel
import ru.lissdev.sportnote.ui.base.BaseFragment

@AndroidEntryPoint
class ExerciseFragment : BaseFragment<FragmentExerciseBinding>(R.layout.fragment_exercise) {
    private val vm by viewModels<ExerciseViewModel>()
    private val args: ExerciseFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })
        binding.vm = vm
        vm.updateExercise(args.exerciseId)
    }
}