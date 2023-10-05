package ddwu.com.mobile.fooddbexam_arch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ddwu.com.mobile.fooddbexam_arch.data.Food
import ddwu.com.mobile.fooddbexam_arch.data.FoodRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FoodViewModel(val repository: FoodRepository) : ViewModel() {
    var allFoods: LiveData<List<Food>> = repository.allFoods.asLiveData() // 자동으로 라이브데이타로 변환

    fun addFood(food: Food) = viewModelScope.launch {
        repository.addFood(food)
    }

    fun deleteFood(food: String) = viewModelScope.launch {
        repository.deleteFood(food)
    }
}

class FoodViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modeClass: Class<T>): T {
        if(modeClass.isAssignableFrom(FoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}