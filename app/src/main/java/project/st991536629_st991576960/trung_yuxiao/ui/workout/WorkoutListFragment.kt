package project.st991536629_st991576960.trung_yuxiao.ui.workout


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentWorkoutListBinding
import java.util.*


class WorkoutListFragment : Fragment() {

    private val TAG = "WorkoutListFragment"

    private var _binding: FragmentWorkoutListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val workoutListViewModel: WorkoutListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutListBinding.inflate(inflater, container, false)

        binding.exercisesRecyclerView.layoutManager = LinearLayoutManager(context);

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                workoutListViewModel.exercise.collect { exercises ->
                    binding.exercisesRecyclerView.adapter = WorkoutListAdapter(exercises,
                        { id: UUID, exerciseType: ExerciseType ->  // onExerciseClicked
                            Log.d(TAG, "Exercise ID ${id} is clicked")
                            findNavController().navigate(WorkoutListFragmentDirections.showExerciseDetail(id,exerciseType))
                        },
                        { id: UUID, exerciseType: ExerciseType ->  // onExerciseDeleteClicked
                            Log.d(TAG, "Delete Exercise with ID = ${id} is clicked")
                        })
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}