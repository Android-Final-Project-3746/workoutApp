package project.st991536629_st991576960.trung_yuxiao.ui.dietary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.data.DietaryRepository
import project.st991536629_st991576960.trung_yuxiao.domain.DietModel

import java.util.*

const val TAG = "DietListViewModel";

class DietListViewModel : ViewModel() {

    private val dietaryRepository = DietaryRepository.get();

    private val _dietaries: MutableStateFlow<List<DietModel>> = MutableStateFlow(emptyList());
    val dietaries: StateFlow<List<DietModel>> = _dietaries.asStateFlow()

    init {
        viewModelScope.launch  {
            dietaryRepository.getAllDietary().collect {
                Log.d(TAG, it.toString());
                _dietaries.value = it
            }
        }
    }

    suspend fun addDietary(dietary: DietModel) {
        dietaryRepository.addDietary(dietary);
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "DietList Fragment is destroyed")
    }
}