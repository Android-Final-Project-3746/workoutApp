package project.st991536629_st991576960.trung_yuxiao.ui.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.data.PushUpExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.data.RunningExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.domain.Exercise
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import java.util.*


class WorkoutListViewModel : ViewModel() {
    private val runningExerciseRepository = RunningExerciseRepository.get();
    private val pushUpExerciseRepository = PushUpExerciseRepository.get();

    private val _exercises: MutableStateFlow<List<Exercise>> = MutableStateFlow(emptyList());
    val exercise: StateFlow<List<Exercise>> = _exercises.asStateFlow();

    init {
        viewModelScope.launch {
            runningExerciseRepository.getAllRunningExercise().combine(pushUpExerciseRepository.getAllPushUp()) { running, pushup ->
                concat(running, pushup)
            }.collect {
                _exercises.value = it;
            }
        }
    }

    fun deleteExerciseById(id: UUID, exerciseType: ExerciseType) {
        viewModelScope.launch {
            if ( exerciseType == ExerciseType.RUNNING ) {
                runningExerciseRepository.deleteById(id)
            } else if (exerciseType == ExerciseType.PUSHUP) {
                pushUpExerciseRepository.deleteById(id);
            }
        }
    }

    private fun <T> concat(vararg lists: List<T>): List<T> {
        val newList: MutableList<T> = mutableListOf()
        for (list in lists) {
            newList.addAll(list)
        }
        return newList
    }
}