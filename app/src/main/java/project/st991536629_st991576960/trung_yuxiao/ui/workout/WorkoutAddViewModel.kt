package project.st991536629_st991576960.trung_yuxiao.ui.workout

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
import java.util.*

class WorkoutAddViewModel : ViewModel() {
    private val runningExerciseRepository = RunningExerciseRepository.get();
    private val pushUpExerciseRepository = PushUpExerciseRepository.get();

    private val _exercise: MutableStateFlow<Exercise?> = MutableStateFlow(null);
    val exercise: StateFlow<Exercise?> = _exercise.asStateFlow();

    private val _exerciseType: MutableStateFlow<ExerciseType> = MutableStateFlow(ExerciseType.RUNNING);
    val exerciseType: StateFlow<ExerciseType> = _exerciseType.asStateFlow();

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
            if ( exercise.value is RunningExercise ) {
                exercise.value?.let { runningExerciseRepository.addRunningExercise(it as RunningExercise) }
            } else {
                exercise.value?.let { pushUpExerciseRepository.addPushUp(it as PushUpExercise) }
            }
        }
    }


}