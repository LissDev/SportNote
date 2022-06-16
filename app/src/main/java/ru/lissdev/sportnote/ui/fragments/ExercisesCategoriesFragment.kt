package ru.lissdev.sportnote.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import ru.lissdev.sportnote.BR
import ru.lissdev.sportnote.R
import ru.lissdev.sportnote.databinding.FragmentExercisesCategoriesBinding
import ru.lissdev.sportnote.databinding.viewmodels.ExercisesCategoriesViewModel
import ru.lissdev.sportnote.ui.base.BaseFragment


class ExercisesCategoriesFragment :
    BaseFragment<FragmentExercisesCategoriesBinding>(R.layout.fragment_exercises_categories) {

    private val vm by viewModels<ExercisesCategoriesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = vm
    }
}