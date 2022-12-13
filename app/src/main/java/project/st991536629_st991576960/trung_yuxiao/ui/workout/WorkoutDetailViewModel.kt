package project.st991536629_st991576960.trung_yuxiao.ui.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
import java.util.UUID

class WorkoutDetailViewModel(exerciseID: UUID, exerciseType: ExerciseType) : ViewModel() {

    private val runningExerciseRepository = RunningExerciseRepository.get();
    private val pushUpExerciseRepository = PushUpExerciseRepository.get();

    private val _exercise: MutableStateFlow<Exercise?> = MutableStateFlow(null);
    val exercise: StateFlow<Exercise?> = _exercise.asStateFlow();

    private val _message: MutableStateFlow<String?> = MutableStateFlow(null);
    val message: StateFlow<String?> = _message.asStateFlow();

    init {
        viewModelScope.launch {
            if ( exerciseType == ExerciseType.RUNNING ) {
                _exercise.value = runningExerciseRepository.getRunningExerciseById(exerciseID);
            } else {
                _exercise.value = pushUpExerciseRepository.getPushUpById(exerciseID);
            }
        }
    }

    fun updateExercise(onUpdate: (Exercise) -> Exercise) {
        _exercise.update { oldValue ->
            oldValue?.let { onUpdate(it) }
        }
    }

    fun updateToDatabase() {

        viewModelScope.launch {

            val runningExercises = runningExerciseRepository.getAllOneTime();
            val pushUpExercises = pushUpExerciseRepository.getAllOneTime();

            if (canUpdate(runningExercises, pushUpExercises)) {
                if ( exercise.value is RunningExercise ) {
                    exercise.value?.let { runningExerciseRepository.updateRunningExercise(it as RunningExercise) }
                } else {
                    exercise.value?.let { pushUpExerciseRepository.updatePushUp(it as PushUpExercise) }
                }

                _message.value = "Updated Successfully"
            }
        }
    }

    fun updateCompleteStatus() {
        viewModelScope.launch {
            if ( exercise.value is RunningExercise ) {
                exercise.value?.let { runningExerciseRepository.updateRunningExercise(it as RunningExercise) }
            } else {
                exercise.value?.let { pushUpExerciseRepository.updatePushUp(it as PushUpExercise) }
            }

            if(exercise.value?.isDone == true) {
                _message.value = "Congratulation!!!"
            }
        }
    }

    fun resetMessage() {
        _message.value = null;
    }

    private fun canUpdate(runningExercises: List<RunningExercise>, pushUpExercises: List<PushUpExercise>): Boolean {
        return  runningExercises.filter { exercise -> inTimeRange(exercise, this.exercise.value, 120) }.isEmpty() &&
                pushUpExercises.filter  { exercise -> inTimeRange(exercise, this.exercise.value, 120) }.isEmpty()
    }

    private fun inTimeRange(exercise: Exercise, exerciseToBeUpdated: Exercise?, range: Long): Boolean {

        if ( exercise.id == exerciseToBeUpdated?.id ) {
            return false;
        }

        val existedTime = DateUtil.convertDateToLocalDate(exercise.dateTime);
        val toBeAddedTime = DateUtil.convertDateToLocalDate(exerciseToBeUpdated!!.dateTime);

        return when {
            existedTime == toBeAddedTime -> {
                _message.value = "There is already an exercise at this time"
                return true
            };

            existedTime.plus(Duration.of(range, ChronoUnit.MINUTES)) >= toBeAddedTime && existedTime.minus(
                Duration.of(range, ChronoUnit.MINUTES)) <= toBeAddedTime -> {
                _message.value = "There is an exercise at ${DateUtil.extractLocalDateTime(existedTime)}"
                return true;
            };
            else -> false
        }
    }
}


class WorkoutDetailViewModelFactory(
    private val exerciseID: UUID,
    private val exerciseType: ExerciseType
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WorkoutDetailViewModel(exerciseID, exerciseType) as T;
    }
}