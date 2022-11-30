package project.st991536629_st991576960.trung_yuxiao.ui.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.data.PushUpExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.data.RunningExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.domain.Exercise
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
        // TODO
    }

    private fun <T> concat(vararg lists: List<T>): List<T> {
        val newList: MutableList<T> = mutableListOf()
        for (list in lists) {
            newList.addAll(list)
        }
        return newList
    }
}


//        viewModelScope.launch {
//
//            delay(3000)
//            runningExerciseRepository.addRunningExercise(RunningExercise(
//                id = UUID.randomUUID(),
//                dateTime = Date(),
//                distance = 1.0,
//                isDone = false
//            ))
//
//            runningExerciseRepository.addRunningExercise(RunningExercise(
//                id = UUID.randomUUID(),
//                dateTime = Date(),
//                distance = 8.5,
//                isDone = true
//            ))
//
//            pushUpExerciseRepository.addPushUp(PushUpExercise(
//                id = UUID.randomUUID(),
//                dateTime = Date(),
//                times = 500,
//                isDone = true
//            ))
//        }