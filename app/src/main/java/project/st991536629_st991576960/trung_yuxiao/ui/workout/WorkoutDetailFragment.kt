package project.st991536629_st991576960.trung_yuxiao.ui.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentWorkoutDetailBinding
import project.st991536629_st991576960.trung_yuxiao.domain.Exercise
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import project.st991536629_st991576960.trung_yuxiao.ui.dialogs.DatePickerFragment
import project.st991536629_st991576960.trung_yuxiao.ui.dialogs.TimePickerFragment
import project.st991536629_st991576960.trung_yuxiao.utils.DateUtil
import java.util.*

class WorkoutDetailFragment : Fragment() {

    private var _binding: FragmentWorkoutDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: WorkoutDetailFragmentArgs by navArgs();

    // initialize the WorkoutDetailViewModel
    private val workoutDetailViewModel: WorkoutDetailViewModel by viewModels {
        WorkoutDetailViewModelFactory(args.exerciseId, args.exerciseType)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (!args.onlySetCheck) {

            binding.apply {

                isDoneCheckBox.isEnabled = false;

                if (args.exerciseType == ExerciseType.RUNNING) { // For Running Exercise
                    runningDistance.doOnTextChanged { text, start, before, count ->
                        if ( text!!.isNotEmpty() && text.toString()!!.toDoubleOrNull() != null ) {
                            workoutDetailViewModel.updateExercise { oldValue ->
                                (oldValue as RunningExercise).copy(distance = text.toString().toDouble())
                            }
                        }
                    }

                    // disable the times for pushup exercise. This is a One-time set
                    pushupTimesGroup.visibility = View.GONE;
                    exerciseTypeTitle.text = "RUNNING";
                } else {
                    pushupTime.doOnTextChanged { text, start, before, count ->
                        if ( text!!.isNotEmpty() && text.toString()!!.toDoubleOrNull() != null ) {
                            workoutDetailViewModel.updateExercise { oldValue ->
                                (oldValue as PushUpExercise).copy(times = text.toString().toLong())
                            }
                        }
                    }

                    // disable the distance edit text for Running Exercise. This is a One-time set
                    runningDistanceGroup.visibility = View.GONE;
                    exerciseTypeTitle.text = "PUSH-UP"
                }

                isDoneCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    workoutDetailViewModel.updateExercise { oldValue ->
                        oldValue.updateIsDone(isChecked)
                    }
                }

                exerciseEditBtn.setOnClickListener {
                    workoutDetailViewModel.updateToDatabase()
                }
            }

            // Listen for result
            setFragmentResultListener(
                DatePickerFragment.REQUEST_KEY_DATE
            ) { requestKey, bundle ->
                val newDate = bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date;
                workoutDetailViewModel.updateExercise { oldValue -> oldValue.updateDateTime(dateTime = newDate) }
            }

            setFragmentResultListener(
                TimePickerFragment.REQUEST_KEY_TIME
            ) { requestKey, bundle ->
                val newDate = bundle.getSerializable(TimePickerFragment.BUNDLE_KEY_TIME) as Date;
                workoutDetailViewModel.updateExercise { oldValue -> oldValue.updateDateTime(dateTime = newDate) }
            }
        } else { // Only allow the user to change the CheckBox
            binding.apply {
                isDoneCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    workoutDetailViewModel.updateExercise { oldValue ->
                        oldValue.updateIsDone(isChecked)
                    }
                }

                exerciseEditBtn.setOnClickListener {
                    workoutDetailViewModel.updateCompleteStatus();
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                workoutDetailViewModel.exercise.collect { exercise ->
                    exercise?.let { updateUI(it) }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                workoutDetailViewModel.message.collect() { message ->
                    if ( !message.isNullOrBlank() ) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                        workoutDetailViewModel.resetMessage();
                    }
                }
            }
        }
    } // End OnViewCreated()

    private fun updateUI(exercise: Exercise) {

        if ( !args.onlySetCheck ) {
            binding.apply {
                if ( args.exerciseType == ExerciseType.RUNNING ) {
                    // Cast to Running Exercise
                    exercise as RunningExercise

                    if ( runningDistance.text.toString().toDoubleOrNull() != exercise.distance ) {
                        runningDistance.setText(exercise.distance.toString())
                    }
                } else {
                    // Cast to PushUpExercise
                    exercise as PushUpExercise;
                    if ( pushupTime.text.toString().toLongOrNull() != exercise.times ) {
                        pushupTime.setText(exercise.times.toString())
                    }
                }

                isDoneCheckBox.isChecked = exercise.isDone;
                exerciseDatePicker.text = DateUtil.extractDate(exercise.dateTime);
                exerciseTimePicker.text = DateUtil.extractTime(exercise.dateTime);

                exerciseDatePicker.setOnClickListener {
                    findNavController().navigate(WorkoutDetailFragmentDirections.showWorkoutDatePicker(exercise.dateTime));
                }

                exerciseTimePicker.setOnClickListener {
                    findNavController().navigate(WorkoutDetailFragmentDirections.showWorkoutTimePicker(exercise.dateTime));
                }
            }
        } else {  // only allow to set the CheckBox
            binding.apply {
                isDoneCheckBox.isChecked = exercise.isDone;
                runningDistance.isEnabled = false;
                pushupTime.isEnabled = false;

                if ( args.exerciseType == ExerciseType.RUNNING ) {
                    // Cast to Running Exercise
                    exercise as RunningExercise

                    if ( runningDistance.text.toString().toDoubleOrNull() != exercise.distance ) {
                        runningDistance.setText(exercise.distance.toString())
                    }

                    pushupTimesGroup.visibility = View.GONE;
                    exerciseTypeTitle.text = "RUNNING";
                } else {
                    // Cast to PushUpExercise
                    exercise as PushUpExercise;
                    if ( pushupTime.text.toString().toLongOrNull() != exercise.times ) {
                        pushupTime.setText(exercise.times.toString())
                    }

                    runningDistanceGroup.visibility = View.GONE;
                    exerciseTypeTitle.text = "PUSH-UP"
                }

                exerciseDatePicker.text = DateUtil.extractDate(exercise.dateTime);
                exerciseTimePicker.text = DateUtil.extractTime(exercise.dateTime);
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}