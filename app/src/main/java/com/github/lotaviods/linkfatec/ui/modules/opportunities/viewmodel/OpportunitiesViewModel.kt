package com.github.lotaviods.linkfatec.ui.modules.opportunities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lotaviods.linkfatec.data.repository.interfaces.JobOfferRepository
import com.github.lotaviods.linkfatec.data.repository.interfaces.UserRepository
import com.github.lotaviods.linkfatec.model.ErrorState
import com.github.lotaviods.linkfatec.model.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OpportunitiesViewModel(
    private val repository: JobOfferRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var likeJob: Job? = null

    private val mUiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = mUiState

    private val mUiMessages: MutableSharedFlow<UiMessages> = MutableSharedFlow()
    val uiMessages: SharedFlow<UiMessages> = mUiMessages

    init {
        getAvailableJobs()
    }

    fun getAvailableJobs() = viewModelScope.launch {
        mUiState.emit(UiState.Loading)

        val student = userRepository.getUser()
        val resp = repository.getAllAvailableJobOffers(student.course.id)

        resp.error?.let { error ->
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
                it.likedBy.contains(student.id),
                it.appliedStudentsCount,
                it.subscribedBy.contains(student.id)
            )

        }

        if (posts?.isEmpty() == true) {
            mUiState.emit(UiState.NoPostsFound)
            return@launch
        }

        posts?.let { mUiState.emit(UiState.Loaded(it)) } ?: mUiState.emit(UiState.NoPostsFound)

    }

    fun updateLikeCount(post: Post, liked: Boolean) = viewModelScope.launch {
        likeJob?.cancel()

        likeJob = viewModelScope.launch {
            delay(500)
            val student = userRepository.getUser()
            repository.likeJob(post.id, student.id, liked)
        }
    }

    fun showModalSubscribeJob(posts: List<Post>, position: Int) = viewModelScope.launch {
        val post = posts.getOrNull(position) ?: return@launch

        mUiState.emit(UiState.ShowSubscribeModal(posts, post))
    }

    fun closeModalSubscribeJob() = viewModelScope.launch {
        val state = (mUiState.value as? UiState.Loaded) ?: return@launch

        mUiState.emit(UiState.Loaded(state.posts))
    }

    fun applyToJobOffer(post: Post) = viewModelScope.launch {
        val resp = repository.subscribeJob(post.id, userRepository.getUser().id)

        mUiMessages.emit(UiMessages.HasFinishedToAppliedToJob)
        if (!resp.hasError) {
            updateUiForAppliedJobOffer(post, true)
        }

    }

    fun unSubscribeJob(post: Post) = viewModelScope.launch {
        repository.unSubscribeJob(post.id, userRepository.getUser().id)

        updateUiForAppliedJobOffer(post, false)
    }

    private suspend fun updateUiForAppliedJobOffer(post: Post, apply: Boolean) {
        val state = (mUiState.value as? UiState.Loaded) ?: return

        val list = mutableListOf<Post>().apply { addAll(state.posts) }
        val newPost = list.find { p -> p.id == post.id }
        val index = list.indexOf(post)

        list.remove(post)
        newPost?.subscribed = apply

        if (newPost?.subscribedCount != null) {
            if (apply) newPost.subscribedCount += 1
            else newPost.subscribedCount -= 1
        }

        if (newPost != null) {
            list.add(index, post)
        }

        mUiState.emit(UiState.Loaded(list))
    }

    sealed interface UiState {
        object Loading : UiState
        open class Loaded(
            open val posts: List<Post>
        ) : UiState

        object NoPostsFound : UiState
        data class ShowSubscribeModal(override val posts: List<Post>, val post: Post) :
            Loaded(posts)

        data class Error(val error: ErrorState) : UiState
    }

    sealed interface UiMessages {
        object HasFinishedToAppliedToJob : UiMessages
    }
}