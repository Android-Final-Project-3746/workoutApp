package project.st991536629_st991576960.trung_yuxiao.ui.workout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.data.PushUpExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.data.RunningExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.domain.Exercise
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import project.st991536629_st991576960.trung_yuxiao.utils.DateUtil
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

class WorkoutAddViewModel : ViewModel() {

    private val TAG = "WorkoutAddViewModel";

    private val runningExerciseRepository = RunningExerciseRepository.get();
    private val pushUpExerciseRepository = PushUpExerciseRepository.get();

    private val _exercise: MutableStateFlow<Exercise?> = MutableStateFlow(null);
    val exercise: StateFlow<Exercise?> = _exercise.asStateFlow();

    private val _exerciseType: MutableStateFlow<ExerciseType> = MutableStateFlow(ExerciseType.RUNNING);
    val exerciseType: StateFlow<ExerciseType> = _exerciseType.asStateFlow();

    private val _message: MutableStateFlow<String?> = MutableStateFlow(null);
    val message: StateFlow<String?> = _message.asStateFlow();

    init {
        reInitialExercise();
    }

    private fun reInitialExercise() {
        if ( exerciseType.value == ExerciseType.RUNNING ) {
            _exercise.value = RunningExercise(
                id = UUID.randomUUID(),
                dateTime = Date(),
                distance = 0.0,
                isDone = false,
            )
        } else {
            _exercise.value = PushUpExercise(
                id = UUID.randomUUID(),
                dateTime = Date(),
                times = 0,
                isDone = false,
            )
        }
    }

    fun updateExerciseType(exerciseType: ExerciseType) {
        _exerciseType.value = exerciseType;
        reInitialExercise()
    }

    fun updateExercise(onUpdate: (Exercise) -> Exercise) {
        _exercise.update { oldValue ->
            oldValue?.let { onUpdate(it) }
        }
    }

    fun addToDatabase() {
        viewModelScope.launch {

            val runningExercises = runningExerciseRepository.getAllOneTime();
            val pushUpExercises = pushUpExerciseRepository.getAllOneTime();

            if (canAdd(runningExercises, pushUpExercises)) {
                if ( exercise.value is RunningExercise ) {
                    exercise.value?.let { runningExerciseRepository.addRunningExercise(it as RunningExercise) }
                } else {
                    exercise.value?.let { pushUpExerciseRepository.addPushUp(it as PushUpExercise) }
                }

                _message.value = "Added Successfully"
            }
        }
    }

    fun resetMessage() {
        _message.value = null;
    }

    private fun canAdd(runningExercises: List<RunningExercise>, pushUpExercises: List<PushUpExercise>): Boolean {
        return  runningExercises.filter { exercise -> inTimeRange(exercise, this.exercise.value, 120) }.isEmpty() &&
                pushUpExercises.filter  { exercise -> inTimeRange(exercise, this.exercise.value, 120) }.isEmpty()
    }

    private fun inTimeRange(exercise: Exercise, exerciseToBeAdded: Exercise?, range: Long): Boolean {
        val existedTime = DateUtil.convertDateToLocalDate(exercise.dateTime);
        val toBeAddedTime = DateUtil.convertDateToLocalDate(exerciseToBeAdded!!.dateTime);

        return when {
            existedTime == toBeAddedTime -> {
                Log.d(TAG, "== There are already exercise exist at: ${DateUtil.extractLocalDateTime(existedTime)}");
                _message.value = "There is already an exercise at this time"
                return true
            };

            existedTime.plus(Duration.of(range, ChronoUnit.MINUTES)) >= toBeAddedTime && existedTime.minus(Duration.of(range, ChronoUnit.MINUTES)) <= toBeAddedTime -> {
                Log.d(TAG, "<> There are already exercise exist at: ${DateUtil.extractLocalDateTime(existedTime)}")
                _message.value = "There is an exercise at ${DateUtil.extractLocalDateTime(existedTime)}"
                return true;
            };
            else -> false
        }
    }

}