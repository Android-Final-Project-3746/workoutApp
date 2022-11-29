package project.st991536629_st991576960.trung_yuxiao.ui.dietary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import project.st991536629_st991576960.trung_yuxiao.data.DietaryRepository
import project.st991536629_st991576960.trung_yuxiao.domain.DietModel
import java.util.*

class DietaryDetailViewModel(dietaryId: UUID) : ViewModel() {

    private val dietaryRepository: DietaryRepository = DietaryRepository.get();

    private val _dietary: MutableStateFlow<DietModel?> = MutableStateFlow(null);
    val dietary: StateFlow<DietModel?> = _dietary.asStateFlow();

    init {
        viewModelScope.launch {
            _dietary.value = dietaryRepository.getDietaryById(dietaryId);
        }
    }

    fun updateDietary(onUpdate: (DietModel) -> DietModel) {
        _dietary.update { oldValue ->
            oldValue?.let { onUpdate(it) }
        }
    }

    fun updateToDatabase() {
        viewModelScope.launch {
            dietary.value?.let {dietaryRepository.updateDietary(it)}
        }
    }
}


class DietaryDetailViewModelFactory(private val dietaryId: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DietaryDetailViewModel(dietaryId) as T;
    }
}