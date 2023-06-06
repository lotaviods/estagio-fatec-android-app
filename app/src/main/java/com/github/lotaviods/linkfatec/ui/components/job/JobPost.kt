package com.github.lotaviods.linkfatec.ui.components.job

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CancelScheduleSend
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.helper.TimeHelper.getElapsedTimeString
import com.github.lotaviods.linkfatec.model.Post
import com.github.lotaviods.linkfatec.ui.components.text.BodyCustomText
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor

@Composable
fun JobPost(
    post: Post,
    onLikeClicked: (liked: Boolean) -> Unit,
    onOpenDetailsJob: () -> Unit,
    onUnsubscribeJob: () -> Unit
) {
    var dropdownMenuExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                onOpenDetailsJob()
            },
    )
    {
        Column(Modifier.fillMaxWidth()) {
            Row {
                CompanyProfilePicture(post.companyProfilePicture)
                Column(Modifier.padding(top = 10.dp)) {
                    Text(
                        post.companyName,
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(16.0F, TextUnitType.Sp)
                    )

                    Text(
                        post.role,
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Gray
                    )

                    Text(
                        getElapsedTimeString(post.createdAt),
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.Gray
                    )

                }
                Spacer(modifier = Modifier.weight(1f))
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

                    Icon(
                        imageVector = Icons.Outlined.MoreHoriz,
                        contentDescription = "More options",
                        Modifier.clickable {
                            dropdownMenuExpanded = !dropdownMenuExpanded
                        }
                    )
                }
            }

            JobPostCard(post, onLikeClicked, onOpenDetailsJob, onUnsubscribeJob)
        }

    }
}

@Composable
private fun JobPostCard(
    post: Post,
    onLikeClicked: (liked: Boolean) -> Unit,
    onSubscribeJob: () -> Unit,
    onUnsubscribeJob: () -> Unit
) {
    Column(Modifier.padding(start = 10.dp, end = 10.dp)) {
        BodyCustomText(
            modifier = Modifier.padding(start = 5.dp, top = 15.dp),
            text = post.title
        )

        StatusPostSection(post, onLikeClicked, onSubscribeJob, onUnsubscribeJob)
    }
}

@Composable
fun CompanyProfilePicture(imageUrl: String) {
    val painter = rememberAsyncImagePainter(model = imageUrl)

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
private fun LikeCounter(currentLikeCount: Int) {
    Image(
        modifier = Modifier
            .height(25.dp)
            .width(25.dp),
        painter = painterResource(id = R.drawable.like_icon),
        contentDescription = null
    )
    Text(
        currentLikeCount.toString(),
        textAlign = TextAlign.Center,
        fontSize = TextUnit(15F, TextUnitType.Sp)
    )
}

@Composable
fun StatusPostSection(
    post: Post,
    onLikeClicked: (liked: Boolean) -> Unit,
    onSubscribeJob: () -> Unit,
    onUnsubscribeJob: () -> Unit
) {
    Column {
        var updatedLikeCount: Int by remember { mutableStateOf(post.likeCount) }

        Row(
            modifier = Modifier.padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            LikeCounter(updatedLikeCount)
            Spacer(modifier = Modifier.weight(1f))
            AppliedCounter(post.subscribedCount)
        }

        Divider(modifier = Modifier.padding(top = 10.dp), color = Color.LightGray)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(bottom = 10.dp, start = 2.dp)
        ) {
            LikeButton(Modifier.padding(top = 5.dp), post.liked) { liked ->
                onLikeClicked(liked)

                if (liked)
                    updatedLikeCount++
                else updatedLikeCount--
            }

            Spacer(modifier = Modifier.weight(1f))

            if (!post.subscribed) {
                ApplyJobButton {
                    onSubscribeJob()
                }

            }
            if (post.subscribed)
                UnApplyJobButton {
                    onUnsubscribeJob()
                }
        }
    }
}

@Composable
fun AppliedCounter(subscribedCount: Int) {
    Text(text = stringResource(R.string.applications_counted, subscribedCount))
}

@Composable
private fun ApplyJobButton(callback: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(
                top = 10.dp,
                end = 10.dp
            ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ThemeColor.DarkRed,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 2.dp,
            pressedElevation = 16.dp
        ),
        shape = RoundedCornerShape(24.dp),
        onClick = {
            callback()
        }
    ) {
        Icon(
            imageVector = Icons.Default.Send,
            contentDescription = "Send",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = R.string.apply_offer),
            style = TextStyle.Default.copy(
                color = Color.White,
                fontSize = TextUnit(16F, TextUnitType.Sp)
            )
        )
    }
}


@Composable
private fun UnApplyJobButton(callback: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(
                top = 10.dp,
                end = 10.dp
            ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ThemeColor.DarkRed,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 2.dp,
            pressedElevation = 16.dp
        ),
        shape = RoundedCornerShape(24.dp),
        onClick = {
            callback()
        }
    ) {
        Icon(
            imageVector = Icons.Default.CancelScheduleSend,
            contentDescription = "Send",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = (stringResource(R.string.give_up_offer)),
            style = TextStyle.Default.copy(
                color = Color.White,
                fontSize = TextUnit(16F, TextUnitType.Sp)
            )
        )
    }
}

@Composable
private fun LikeButton(modifier: Modifier, liked: Boolean, callback: (selected: Boolean) -> Unit) {
    val ripple = rememberRipple(bounded = false, color = ThemeColor.DarkRed)
    var selected: Boolean by remember { mutableStateOf(liked) }

    Row(
        modifier = modifier.selectable(
            selected = selected,
            indication = ripple,
            interactionSource = remember {
                MutableInteractionSource()
            }) {
            selected = !selected
            callback(selected)
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.ThumbUp,
            contentDescription = "like",
            tint = if (selected) ThemeColor.DarkRed else Color.LightGray,
            modifier = Modifier
                .height(20.dp)
                .width(20.dp)
        )

        Text(
            text = "Like",
            style = TextStyle.Default.copy(
                color = Color.LightGray,
                fontSize = TextUnit(15F, TextUnitType.Sp)
            )
        )
    }
}


@Composable
@Preview
fun JobPostPreview() {
    JobPost(
        post = Post(
            id = 1,
            companyName = "Company",
            companyProfilePicture = "",
            role = "Dev",
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ultrices a erat et venenatis. Donec et nisl felis. Mauris sit amet lacus porttitor, vestibulum lacus sit amet, porta metus.",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ultrices a erat et venenatis. Donec et nisl felis. Mauris sit amet lacus porttitor, vestibulum lacus sit amet, porta metus.",
            promotionalImageUrl = null,
            likeCount = 10,
            liked = false,
            subscribed = true,
            subscribedCount = 1,
            createdAt = 1L,
            experience = 1
        ),
        onLikeClicked = {},
        onOpenDetailsJob = {},
        onUnsubscribeJob = {},
    )
}

@Composable
@Preview
fun JobPostPreview2() {
    JobPost(
        post = Post(
            id = 1,
            companyName = "Company",
            companyProfilePicture = "",
            role = "Dev",
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ultrices a erat et venenatis. Donec et nisl felis. Mauris sit amet lacus porttitor, vestibulum lacus sit amet, porta metus.",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ultrices a erat et venenatis. Donec et nisl felis. Mauris sit amet lacus porttitor, vestibulum lacus sit amet, porta metus.",
            promotionalImageUrl = null,
            likeCount = 10,
            liked = true,
            subscribed = false,
            subscribedCount = 1,
            createdAt = 1L,
            experience = 1
        ),
        onLikeClicked = {},
        onOpenDetailsJob = {},
        onUnsubscribeJob = {},
    )
}