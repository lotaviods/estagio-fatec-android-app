@file:OptIn(ExperimentalMaterialApi::class)

package com.github.lotaviods.linkfatec.ui.mainscreen.opportunities

import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.util.LinkifyCompat
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.model.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OpportunitiesScreen() {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableStateOf(1) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        itemCount += 1
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    Box(Modifier.pullRefresh(state)) {

        LazyColumn(Modifier.fillMaxSize()) {
            if (!refreshing) {
                items(itemCount) {
                    JobPost(
                        Post(
                            1,
                            "Paschoalotto ",
                            "https://s3.amazonaws.com/gupy5/production/companies/27592/career/66043/images/2022-05-20_17-46_logo.jpg",
                            "Mobile developer",
                            "Emprego: Paschoalotto realiza seleção presencial em Garça nesta segunda e terça \nveja: https://www.garcaemfoco.com.br/noticia/15432/emprego-paschoalotto-realiza-selecao-presencial-em-garca-nesta-segunda-e-terca",
                            "https://www.garcaemfoco.com.br/images/noticias/15432/d319db928671ff29b3f436d07d68ab73.jpeg",
                            5
                        )
                    )
                }
            }
        }

        var height by remember {
            mutableStateOf(0.dp)
        }

        val localDensity = LocalDensity.current

        Box(modifier = Modifier
            .onGloballyPositioned {
                height = with(localDensity) { it.size.height.toDp() }
            }
            .align(Alignment.TopCenter)
            .offset(y = height - (height.times(0.1f)))
        ) {
            PullRefreshIndicator(
                refreshing,
                state
            )
        }

    }
}

@Composable
fun JobPost(post: Post) {
    Row {
        CompanyProfilePicture(post)
        JobPostCard(post)
    }
}

@Composable
fun JobPostCard(post: Post) {
    var dropdownMenuExpanded by remember { mutableStateOf(false) }

    Card(Modifier.padding(10.dp)) {
        Column(Modifier.padding(start = 10.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(post.companyName, modifier = Modifier.padding(start = 5.dp))
                Text(" - ")
                Text(post.role)
                Spacer(Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Outlined.MoreHoriz,
                    contentDescription = "More options",
                    Modifier.clickable {
                        dropdownMenuExpanded = !dropdownMenuExpanded
                    }
                )
                Box {
                    DropdownMenu(
                        expanded = dropdownMenuExpanded,
                        onDismissRequest = { dropdownMenuExpanded = false },
                        modifier = Modifier.align(Alignment.TopEnd),
                        offset = DpOffset(x = 0.dp, y = (30).dp)
                    ) {
                        DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
                            Text("Send Feedback")
                        }
                    }
                }

            }

            BodyText(modifier = Modifier.padding(start = 5.dp), text = post.description)

            if (post.promotionalImageUrl != null)
                PromotionalImage(post.promotionalImageUrl)

            StatusPostSection(post.likeCount)
        }
    }
}

@Composable
private fun BodyText(modifier: Modifier = Modifier, text: String?) {
    val context = LocalContext.current
    val customLinkifyTextView = remember {
        TextView(context)
    }
    AndroidView(modifier = modifier, factory = { customLinkifyTextView }) { textView ->
        textView.text = text ?: ""
        textView.setTextColor(R.color.black)
        LinkifyCompat.addLinks(textView, Linkify.WEB_URLS)
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}

@Composable
fun CompanyProfilePicture(post: Post) {
    val painter = rememberAsyncImagePainter(model = post.companyProfilePicture)

    Image(
        painter = painter,
        contentDescription = "Company profile picture",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(top = 10.dp, start = 5.dp)
            .size(35.dp)
            .clip(CircleShape)
            .border(1.dp, Color.LightGray, CircleShape)
    )
}

@Composable
fun StatusPostSection(likeCount: Int) {
    val ripple = rememberRipple(bounded = false, color = Color.Red)
    var selected: Boolean by remember { mutableStateOf(false) }
    var updatedLikeCount: Int by remember { mutableStateOf(likeCount) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Text(updatedLikeCount.toString(), textAlign = TextAlign.Center)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = "like",
                tint = if (selected) Color.Red else Color.LightGray,
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .selectable(
                        selected = selected,
                        indication = ripple,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }) {
                        selected = !selected

                        if (selected) {
                            updatedLikeCount = likeCount + 1
                            return@selectable
                        }

                        updatedLikeCount = likeCount
                    }
            )

            Text(text = "Like")
        }
    }
}

@Composable
fun PromotionalImage(url: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(10.dp)) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            model = url,
            contentDescription = "promotional_job_image",
            contentScale = ContentScale.Fit
        )
    }

}

@Preview
@Composable
fun JobPostPreview() {
    JobPost(
        Post(
            1,
            "Paschoalotto ",
            "https://s3.amazonaws.com/gupy5/production/companies/27592/career/66043/images/2022-05-20_17-46_logo.jpg",
            "Mobile developer",
            "Emprego: Paschoalotto realiza seleção presencial em Garça nesta segunda e terça \nveja: https://www.garcaemfoco.com.br/noticia/15432/emprego-paschoalotto-realiza-selecao-presencial-em-garca-nesta-segunda-e-terca",
            "https://www.garcaemfoco.com.br/images/noticias/15432/d319db928671ff29b3f436d07d68ab73.jpeg",
            5
        )
    )
}
