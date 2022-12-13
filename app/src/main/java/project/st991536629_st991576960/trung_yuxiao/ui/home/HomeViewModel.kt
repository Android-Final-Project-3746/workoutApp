package project.st991536629_st991576960.trung_yuxiao.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.R
import project.st991536629_st991576960.trung_yuxiao.data.PushUpExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.data.RunningExerciseRepository
import project.st991536629_st991576960.trung_yuxiao.domain.Exercise
import project.st991536629_st991576960.trung_yuxiao.domain.PushUpExercise
import project.st991536629_st991576960.trung_yuxiao.domain.RunningExercise
import project.st991536629_st991576960.trung_yuxiao.domain.WorkoutWebsiteModel
import project.st991536629_st991576960.trung_yuxiao.ui.workout.ExerciseType
import java.util.*

class HomeViewModel : ViewModel() {

    private val runningExerciseRepository = RunningExerciseRepository.get();
    private val pushUpExerciseRepository = PushUpExerciseRepository.get();

    val workoutWebsites: List<WorkoutWebsiteModel> = RecommendedWebsites.websites;

    private val _exercises: MutableStateFlow<List<Exercise>> = MutableStateFlow(emptyList());
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow();

    private val _runningExercises: MutableStateFlow<List<RunningExercise>> = MutableStateFlow(emptyList())
    val runningExercises: StateFlow<List<RunningExercise>> = _runningExercises.asStateFlow();

    private val _pushUpExercises: MutableStateFlow<List<PushUpExercise>> = MutableStateFlow(emptyList())
    val pushUpExercises: StateFlow<List<PushUpExercise>> = _pushUpExercises.asStateFlow();

    init {
        viewModelScope.launch {
            runningExerciseRepository.getAllRunningExercise().combine(pushUpExerciseRepository.getAllPushUp()) { running, pushup ->
                concat(running, pushup)
            }.collect {
                _exercises.value = it;
            }
        }

        viewModelScope.launch {
            runningExerciseRepository.getAllRunningExercise().collect { exercises ->
                _runningExercises.value = exercises
            }
        }

        viewModelScope.launch {
            pushUpExerciseRepository.getAllPushUp().collect { exercises ->
                _pushUpExercises.value = exercises
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