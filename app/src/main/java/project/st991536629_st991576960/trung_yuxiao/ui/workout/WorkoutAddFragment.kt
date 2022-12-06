package project.st991536629_st991576960.trung_yuxiao.ui.workout

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.databinding.FragmentWorkoutAddBinding
import project.st991536629_st991576960.trung_yuxiao.domain.Exercise
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import project.st991536629_st991576960.trung_yuxiao.ui.dialogs.DatePickerFragment
import project.st991536629_st991576960.trung_yuxiao.ui.dialogs.TimePickerFragment
import project.st991536629_st991576960.trung_yuxiao.utils.DateFormatUtil
import java.util.*


class WorkoutAddFragment : Fragment() {

    private var _binding: FragmentWorkoutAddBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val workoutAddViewModel: WorkoutAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutAddBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.running_radio_btn ->
                        workoutAddViewModel.updateExerciseType(ExerciseType.RUNNING);

                    R.id.pushup_radio_btn ->
                        workoutAddViewModel.updateExerciseType(ExerciseType.PUSHUP);
                }
            }

            addRunningDistance.doOnTextChanged { text, start, before, count ->
                if ( text!!.isNotEmpty() && text.toString()!!.toDoubleOrNull() != null ) {
                    workoutAddViewModel.updateExercise { oldValue ->
                        (oldValue as RunningExercise).copy(distance = text.toString().toDouble())
                    }
                }
            }

            addPushupTime.doOnTextChanged { text, start, before, count ->
                if ( text!!.isNotEmpty() && text.toString()!!.toDoubleOrNull() != null  ) {
                    workoutAddViewModel.updateExercise { oldValue ->
                        (oldValue as PushUpExercise).copy(times = text.toString().toLong())
                    }
                }
            }

            addIsDoneCheckBox.setOnCheckedChangeListener { _, isChecked ->
                workoutAddViewModel.updateExercise { oldValue ->
                    oldValue.updateIsDone(isChecked)
                }
            }

            exerciseAddBtn.setOnClickListener {
                workoutAddViewModel.addToDatabase()
            }
        }

        // Listen for result
        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE
        ) { requestKey, bundle ->
            val newDate = bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date;
            workoutAddViewModel.updateExercise { oldValue -> oldValue.updateDateTime(dateTime = newDate) }
        }

        setFragmentResultListener(
            TimePickerFragment.REQUEST_KEY_TIME
        ) { requestKey, bundle ->
            val newDate = bundle.getSerializable(TimePickerFragment.BUNDLE_KEY_TIME) as Date;
            workoutAddViewModel.updateExercise { oldValue -> oldValue.updateDateTime(dateTime = newDate) }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                workoutAddViewModel.exercise.collect() { exercise ->
                    exercise?.let { updateUI(it) }
                }
            }
        }
    }

    private fun updateUI(exercise: Exercise) {
        binding.apply {
            if ( exercise is RunningExercise ) {

                addRunningDistanceGroup.visibility = View.VISIBLE
                addPushupTimesGroup.visibility = View.GONE

                runningRadioBtn.isChecked = true;

                if ( addRunningDistance.text.toString().toDoubleOrNull() != exercise.distance ) {
                    addRunningDistance.setText(exercise.distance.toString())
                }
            } else if (exercise is PushUpExercise) {

                addPushupTimesGroup.visibility = View.VISIBLE;
                addRunningDistanceGroup.visibility = View.GONE;

                pushupRadioBtn.isChecked = true;

                if ( addPushupTime.text.toString().toLongOrNull() != exercise.times ) {
                    addPushupTime.setText(exercise.times.toString())
                }
            }

            addIsDoneCheckBox.isChecked = exercise.isDone;
            addExerciseDatePicker.text = DateFormatUtil.extractDate(exercise.dateTime);
            addExerciseTimePicker.text = DateFormatUtil.extractTime(exercise.dateTime);

            addExerciseDatePicker.setOnClickListener {
                findNavController().navigate(WorkoutAddFragmentDirections.showWorkoutAddDatePicker(exercise.dateTime));
            }

            addExerciseTimePicker.setOnClickListener {
                findNavController().navigate(WorkoutAddFragmentDirections.showWorkoutAddTimePicker(exercise.dateTime));
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}