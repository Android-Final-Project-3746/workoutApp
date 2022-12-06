package project.st991536629_st991576960.trung_yuxiao.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentHomeBinding
import project.st991536629_st991576960.trung_yuxiao.domain.Exercise
import project.st991536629_st991576960.trung_yuxiao.ui.workout.ExerciseType
import project.st991536629_st991576960.trung_yuxiao.ui.workout.WorkoutListAdapter
import project.st991536629_st991576960.trung_yuxiao.ui.workout.WorkoutListFragmentDirections
import project.st991536629_st991576960.trung_yuxiao.utils.DateFormatUtil
import java.util.*

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // initialize viewModel
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.subtitle = ""

        binding.workoutWebsitesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        val websites = homeViewModel.workoutWebsites;
        val adapter = WorkoutWebsiteAdapter(websites) { url ->
            findNavController().navigate(HomeFragmentDirections.openInWebview(url))
        };
        binding.workoutWebsitesRecyclerView.adapter = adapter;
        // Having the snap animation like in Google Play Store
        val linearSnapHelper: LinearSnapHelper = LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(binding.workoutWebsitesRecyclerView)


        binding.nextExercisesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        //LinearSnapHelper().attachToRecyclerView(binding.nextExercisesRecyclerView);

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.exercises.transform<List<Exercise>, List<Exercise>> { value ->
                    if (value.size == 0) {
                        Log.d(TAG, "There is no exercise for today")
                        //emit(listOf())
                    } else {
                        Log.d(TAG, "There is some exercise for today")
                        val result = value.filter { it -> DateFormatUtil.compareCurrentDateToDate(it.dateTime) }.sortedBy { it.dateTime }
                        Log.d(TAG, result.toString())
                        emit(result)
                    }
                }.collect { exercises ->
                    binding.nextExercisesRecyclerView.adapter = NextExercisesAdapter(exercises) { exerciseID, exerciseType ->
                        findNavController().navigate(HomeFragmentDirections.showExerciseDetails(exerciseID, exerciseType))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}