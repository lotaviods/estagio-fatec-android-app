package com.github.lotaviods.linkfatec.ui.mainscreen.opportunities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lotaviods.linkfatec.model.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Collections

class OpportunitiesViewModel : ViewModel() {
    private var likeJob: Job? = null

    private val mUiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = mUiState

    init {
        getAvailableJobs()
    }

    fun getAvailableJobs() = viewModelScope.launch {
        mUiState.emit(UiState.Loading)

        delay(1500)

        val posts = mutableListOf<Post>().apply {
            add(
                Post(
                    1,
                    "Paschoalotto ",
                    "https://s3.amazonaws.com/gupy5/production/companies/27592/career/66043/images/2022-05-20_17-46_logo.jpg",
                    "Mobile developer",
                    "Emprego: Paschoalotto realiza seleção presencial em Garça nesta segunda e terça \nveja: https://www.garcaemfoco.com.br/noticia/15432/emprego-paschoalotto-realiza-selecao-presencial-em-garca-nesta-segunda-e-terca",
                    "https://www.garcaemfoco.com.br/images/noticias/15432/d319db928671ff29b3f436d07d68ab73.jpeg",
                    5
                ),
            )
            add(
                Post(
                    2,
                    "Paschoalotto ",
                    "https://s3.amazonaws.com/gupy5/production/companies/27592/career/66043/images/2022-05-20_17-46_logo.jpg",
                    "Mobile developer",
                    "Emprego: Paschoalotto realiza seleção presencial em Garça nesta segunda e terça \nveja: https://www.garcaemfoco.com.br/noticia/15432/emprego-paschoalotto-realiza-selecao-presencial-em-garca-nesta-segunda-e-terca",
                    "https://www.garcaemfoco.com.br/images/noticias/15432/d319db928671ff29b3f436d07d68ab73.jpeg",
                    5
                ),
            )
            add(
                Post(
                    3,
                    "Paschoalotto ",
                    "https://s3.amazonaws.com/gupy5/production/companies/27592/career/66043/images/2022-05-20_17-46_logo.jpg",
                    "Mobile developer",
                    "Emprego: Paschoalotto realiza seleção presencial em Garça nesta segunda e terça \nveja: https://www.garcaemfoco.com.br/noticia/15432/emprego-paschoalotto-realiza-selecao-presencial-em-garca-nesta-segunda-e-terca",
                    "https://www.garcaemfoco.com.br/images/noticias/15432/d319db928671ff29b3f436d07d68ab73.jpeg",
                    5
                ),
            )
            add(
                Post(
                    4,
                    "Paschoalotto ",
                    "https://s3.amazonaws.com/gupy5/production/companies/27592/career/66043/images/2022-05-20_17-46_logo.jpg",
                    "Mobile developer",
                    "Emprego: Paschoalotto realiza seleção presencial em Garça nesta segunda e terça \nveja: https://www.garcaemfoco.com.br/noticia/15432/emprego-paschoalotto-realiza-selecao-presencial-em-garca-nesta-segunda-e-terca",
                    "https://www.garcaemfoco.com.br/images/noticias/15432/d319db928671ff29b3f436d07d68ab73.jpeg",
                    5
                ),
            )
        }

        mUiState.emit(UiState.Loaded(posts))
    }

    fun updateLikeCount(post: Post, liked: Boolean) = viewModelScope.launch {
        likeJob?.cancel()
        updateScreenState(post, liked)

        likeJob = viewModelScope.launch {
            delay(500)
            // TODO: Call api to apply like count
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

    }


    sealed interface UiState {
        object Loading : UiState
        data class Loaded(
            val posts: List<Post>
        ) : UiState

        object Error : UiState
    }
}