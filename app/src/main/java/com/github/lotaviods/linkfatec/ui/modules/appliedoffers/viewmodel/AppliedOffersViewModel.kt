package com.github.lotaviods.linkfatec.ui.modules.appliedoffers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lotaviods.linkfatec.data.repository.interfaces.JobOfferRepository
import com.github.lotaviods.linkfatec.data.repository.interfaces.UserRepository
import com.github.lotaviods.linkfatec.model.ErrorState
import com.github.lotaviods.linkfatec.model.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppliedOffersViewModel(
    private val repository: JobOfferRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var likeCoroutineJob: Job? = null

    private val mUiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState.Loading
    )
    val uiState: StateFlow<UiState> = mUiState

    fun loadAppliedJobs() = viewModelScope.launch {
        mUiState.emit(UiState.Loading)
        getUpdatedAppliedJobs()
    }

    private suspend fun getUpdatedAppliedJobs() {
        val student = userRepository.getUser()
        val resp = repository.getAppliedJobOffers(student.id)

        resp.error?.let { error ->
            mUiState.emit(UiState.Error(error))
            return
        }

        val posts = resp.data?.map {
            it.toPost(student)
        }

        if (posts?.isEmpty() == true) {
            mUiState.emit(UiState.LoadedEmpty)
            return
        }

        posts?.let { mUiState.emit(UiState.Loaded(it)) } ?: mUiState.emit(
            UiState.LoadedEmpty
        )
    }

    fun unsubscribeJobPost(post: Post) = viewModelScope.launch {
        val posts = ((mUiState.value) as? UiState.Loaded)?.posts ?: return@launch

        val resp = repository.unSubscribeJob(post.id, userRepository.getUser().id)

        if (resp.hasError) {
            return@launch
        }

        val list = mutableListOf<Post>().apply { addAll(posts) }

        list.remove(post)

        post.subscribed = false

        if (list.isEmpty()) {
            mUiState.emit(UiState.LoadedEmpty)
            return@launch
        }

        mUiState.emit(UiState.Loaded(list))
    }

    fun updateLikeCount(post: Post, liked: Boolean) = viewModelScope.launch {
        likeCoroutineJob?.cancel()

        likeCoroutineJob = viewModelScope.launch {
            delay(500)
            val student = userRepository.getUser()
            repository.likeJob(post.id, student.id, liked)
        }
    }

    fun reloadOrLoadAppliedJob() = viewModelScope.launch {
        if (mUiState.value is UiState.Loading) {
            loadAppliedJobs()
            return@launch
        }
        val posts = (mUiState.value as? UiState.Loaded)?.posts ?: listOf()

        mUiState.emit(UiState.Reloading(posts = posts))
        getUpdatedAppliedJobs()
    }

    fun closeJobModal() = viewModelScope.launch {
        val uiState = mUiState.value
        if (uiState !is UiState.ShowSubscribeModal) return@launch

        mUiState.emit(UiState.Loaded(uiState.list))
    }

    fun openJobModal(post: Post, list: List<Post>) = viewModelScope.launch {
        mUiState.emit(UiState.ShowSubscribeModal(post = post, list = list))
    }

    sealed interface UiState {
        object Loading : UiState


        data class Error(val error: ErrorState) : UiState

        open class Loaded(open val posts: List<Post>) : UiState

        class Reloading(posts: List<Post>) : Loaded(posts)

        object LoadedEmpty : UiState
        class ShowSubscribeModal(val post: Post, val list: List<Post>) :
            Loaded(list)
    }
}