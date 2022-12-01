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
import java.util.UUID

class WorkoutDetailViewModel(exerciseID: UUID, exerciseType: ExerciseType) : ViewModel() {

    private val runningExerciseRepository = RunningExerciseRepository.get();
    private val pushUpExerciseRepository = PushUpExerciseRepository.get();

    private val _exercise: MutableStateFlow<Exercise?> = MutableStateFlow(null);
    val exercise: StateFlow<Exercise?> = _exercise.asStateFlow();

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
            if ( exercise.value is RunningExercise ) {
                exercise.value?.let { runningExerciseRepository.updateRunningExercise(it as RunningExercise) }
            } else {
                exercise.value?.let { pushUpExerciseRepository.updatePushUp(it as PushUpExercise) }
            }
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