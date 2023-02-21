package com.github.lotaviods.linkfatec.ui.modules.opportunities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lotaviods.linkfatec.data.repository.JobOfferRepository
import com.github.lotaviods.linkfatec.data.repository.UserRepository
import com.github.lotaviods.linkfatec.model.ErrorState
import com.github.lotaviods.linkfatec.model.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Collections

class OpportunitiesViewModel(
    private val repository: JobOfferRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var likeJob: Job? = null

    private val mUiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = mUiState

    init {
        getAvailableJobs()
    }

    fun getAvailableJobs() = viewModelScope.launch {
        mUiState.emit(UiState.Loading)

        val student = userRepository.getUser()
        val resp = repository.getAllAvailableJobOffers(student.course.id)

        resp.error?.let {error ->
            mUiState.emit(UiState.Error(error))
            return@launch
        }

        val posts = resp.data?.map {
            Post(
                it.id,
                it.companyName,
                it.companyProfilePicture ?: "",
                it.role ?: "",
                it.description,
                it.promotionalImageUrl,
                it.likeCount,
                it.likedBy.contains(student.id)
            )

        }
        posts?.let { mUiState.emit(UiState.Loaded(it)) } ?: mUiState.emit(UiState.Loaded(listOf()))

    }

    fun updateLikeCount(post: Post, liked: Boolean) = viewModelScope.launch {
        likeJob?.cancel()
        updateScreenState(post, liked)

        likeJob = viewModelScope.launch {
            delay(500)
            val student = userRepository.getUser()
            repository.likeJob(post.id, student.id, liked)

        }

    }

    private suspend fun updateScreenState(post: Post, liked: Boolean) {
        val state = (mUiState.value as? UiState.Loaded)?.copy() ?: return

        val selectedPost = state.posts.find { p -> p.id == post.id } ?: return
        val newLikeCount = if (liked) selectedPost.likeCount + 1 else selectedPost.likeCount - 1
        val newPost = selectedPost.copy(likeCount = newLikeCount, liked = liked)

        Collections.replaceAll(state.posts, selectedPost, newPost)

        mUiState.emit(state)
    }

    fun applyJob(post: Post) = viewModelScope.launch {
        // TODO: Make loading button animation, maybe need to change this
        // TODO: Make user feedback when apply job
        val student = userRepository.getUser()
        repository.subscribeJob(post.id, student.id)
    }


    sealed interface UiState {
        object Loading : UiState
        data class Loaded(
            val posts: List<Post>
        ) : UiState

        data class Error(val error: ErrorState) : UiState
    }
}