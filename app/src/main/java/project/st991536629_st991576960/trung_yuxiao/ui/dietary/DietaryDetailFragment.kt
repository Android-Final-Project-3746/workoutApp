package project.st991536629_st991576960.trung_yuxiao.ui.dietary

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentDietaryDetailBinding
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentWorkoutDetailBinding
import project.st991536629_st991576960.trung_yuxiao.ui.workout.WorkoutAddViewModel

class DietaryDetailFragment : Fragment() {

    private var _binding: FragmentDietaryDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val workoutDetailViewModel: WorkoutAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDietaryDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}