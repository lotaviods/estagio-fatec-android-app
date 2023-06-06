package com.github.lotaviods.linkfatec.ui.components.job

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.github.lotaviods.linkfatec.R
import com.github.lotaviods.linkfatec.data.remote.model.JobNotificationModel
import com.github.lotaviods.linkfatec.helper.TimeHelper
import com.github.lotaviods.linkfatec.ui.theme.ThemeColor
import com.github.lotaviods.linkfatec.ui.util.toTimestamp

@Composable
fun JobNotification(modifier: Modifier = Modifier, notification: JobNotificationModel) {
    Card(modifier) {
        Column {
            Row(Modifier.fillMaxWidth()) {
                CompanyProfilePicture(notification.companyProfilePicture ?: "")
                Column(Modifier.padding(top = 10.dp)) {
                    Text(
                        notification.companyName,
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(16.0F, TextUnitType.Sp)
                    )

                    Text(
                        notification.jobTitle,
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Light,
                        fontSize = TextUnit(12.0F, TextUnitType.Sp)
                    )

                    Text(
                        notification.location,
                        modifier = Modifier.padding(start = 5.dp),
                        fontSize = TextUnit(12.0F, TextUnitType.Sp),
                        color = Color.Gray
                    )

                    Text(
                        TimeHelper.getElapsedTimeString(
                            notification.statusChangedDate?.toTimestamp() ?: 0L
                        ),
                        modifier = Modifier.padding(start = 5.dp),
                        fontSize = TextUnit(12.0F, TextUnitType.Sp),
                        color = Color.Gray
                    )

                }
                val status =
                    if (notification.approved)
                        stringResource(R.string.approved) else stringResource(R.string.rejected)
                Spacer(modifier = Modifier.weight(1F))
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (notification.approved) Color(
                                red = 6,
                                green = 162,
                                blue = 131
                            ) else Color.Gray
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = status,
                        color = Color.White
                    )
                }
            }

        }
    }
}

@Composable
@Preview
fun JobNotificationPreviewApproved() {
    JobNotification(
        modifier = Modifier.background(Color.White),
        notification = JobNotificationModel(
            companyName = "Company",
            jobTitle = "Support analyst",
            statusChangedDate = "2023-06-02T07:36:24+00:00",
            approved = true,
            companyProfilePicture = "",
            location = "São Paulo, Brazil"
        ),
    )
}

@Composable
@Preview
fun JobNotificationPreviewRejected() {
    JobNotification(
        modifier = Modifier.background(Color.White),
        notification = JobNotificationModel(
            companyName = "Company",
            jobTitle = "Support analyst",
            statusChangedDate = "2023-06-02T07:36:24+00:00",
            approved = false,
            companyProfilePicture = "",
            location = "São Paulo, Brazil"
        ),
    )
}