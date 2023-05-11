package com.github.lotaviods.linkfatec.ui.modules.appliedoffers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lotaviods.linkfatec.data.repository.interfaces.JobOfferRepository
import com.github.lotaviods.linkfatec.data.repository.interfaces.UserRepository
import com.github.lotaviods.linkfatec.model.ErrorState
import com.github.lotaviods.linkfatec.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppliedOffersViewModel(
    private val repository: JobOfferRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val mUiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState.Loading
    )
    val uiState: StateFlow<UiState> = mUiState

    init {
        loadAppliedJobs()
    }

    fun loadAppliedJobs() = viewModelScope.launch {
        mUiState.emit(UiState.Loading)

        val student = userRepository.getUser()
        val resp = repository.getAppliedJobOffers(student.id)

        resp.error?.let { error ->
            mUiState.emit(UiState.Error(error))
            return@launch
        }

        val posts = resp.data?.map {
            it.toPost(student)
        }

        if (posts?.isEmpty() == true) {
            mUiState.emit(UiState.LoadedEmpty)
            return@launch
        }

        posts?.let { mUiState.emit(UiState.Loaded(it)) } ?: mUiState.emit(
            UiState.LoadedEmpty
        )

    }

    sealed interface UiState {
        object Loading : UiState


        data class Error(val error: ErrorState) : UiState

        data class Loaded(val posts: List<Post>) : UiState
        object LoadedEmpty : UiState
    }
}