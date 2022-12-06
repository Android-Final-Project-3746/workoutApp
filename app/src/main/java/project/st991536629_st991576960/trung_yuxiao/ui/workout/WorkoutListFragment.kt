package project.st991536629_st991576960.trung_yuxiao.ui.workout


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Delete
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentWorkoutListBinding
import project.st991536629_st991576960.trung_yuxiao.domain.Exercise
import project.st991536629_st991576960.trung_yuxiao.ui.dialogs.Confirmation
import project.st991536629_st991576960.trung_yuxiao.ui.dialogs.DeleteConfirmationDialogFragment
import java.util.*


class WorkoutListFragment : Fragment(), MenuProvider {

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

        // Create option menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED);

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                workoutListViewModel.exercise.transform<List<Exercise>, List<Exercise>> { value ->
                    val sortedResult = value.sortedByDescending { it.dateTime }
                    emit(sortedResult);
                }.collect { exercises ->
                    binding.exercisesRecyclerView.adapter = WorkoutListAdapter(exercises,
                        { id: UUID, exerciseType: ExerciseType ->  // onExerciseClicked
                            Log.d(TAG, "Exercise ID ${id} is clicked")
                            findNavController().navigate(WorkoutListFragmentDirections.showExerciseDetail(id,exerciseType))
                        },
                        { id: UUID, exerciseType: ExerciseType ->  // onExerciseDeleteClicked
                            Log.d(TAG, "Delete Exercise with ID = ${id} is clicked")
                            findNavController().navigate(WorkoutListFragmentDirections.showDeleteConfirmDialog(id, exerciseType));
                        })
                }
            }
        }

        setFragmentResultListener(
            DeleteConfirmationDialogFragment.REQUEST_KEY_CONFIRM
        ) { requestKey, bundle ->
            val confirmation = bundle.getSerializable(DeleteConfirmationDialogFragment.BUNDLE_KEY_CONFIRM) as Confirmation

            if ( confirmation.confirm ) {
                workoutListViewModel.deleteExerciseById(confirmation.exerciseID, confirmation.exerciseType);
            } else {}
        }
    }

    // Inflating option menu
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.fragment_list_diary, menu);
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.new_diary -> {
                // Navigate to Fragment_workout_add

                findNavController().navigate(WorkoutListFragmentDirections.addWorkoutPlan())
                true
            }
            else -> super.onOptionsItemSelected(menuItem);
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}