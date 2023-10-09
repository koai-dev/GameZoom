package com.koai.gamezoom.db.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.koai.gamezoom.db.repository.GameRepository
import com.koai.gamezoom.models.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(private val repository: GameRepository) : ViewModel() {
    val allGameLocal: LiveData<List<Game>?> =
        repository.allGameByName().asLiveData()

    fun insert(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(game)
        }
    }

    fun delete(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(game)
        }
    }
}

class GameViewModelFactory(private val repository: GameRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}