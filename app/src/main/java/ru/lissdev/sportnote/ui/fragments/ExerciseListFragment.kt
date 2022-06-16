package ru.lissdev.sportnote.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import ru.lissdev.sportnote.R
import ru.lissdev.sportnote.databinding.FragmentExerciseListBinding
import ru.lissdev.sportnote.databinding.viewmodels.ExerciseListViewModel
import ru.lissdev.sportnote.ui.base.BaseFragment


class ExerciseListFragment :
    BaseFragment<FragmentExerciseListBinding>(R.layout.fragment_exercise_list) {

    private val vm by navGraphViewModels<ExerciseListViewModel>(R.id.exercises_nav_graph_xml)
    private val args: ExerciseListFragmentArgs by navArgs()

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
        vm.updateList(args.categoryId)
    }
}