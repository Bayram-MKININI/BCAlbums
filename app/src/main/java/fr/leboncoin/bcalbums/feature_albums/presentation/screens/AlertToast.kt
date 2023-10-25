package fr.leboncoin.bcalbums.feature_albums.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.leboncoin.bcalbums.R

@Composable
fun AlertToast(
    modifier: Modifier = Modifier,
    visible: Boolean,
    text: String
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { +40 }) + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        ToastContent(modifier = modifier, text = text)
    }
}

@Composable
private fun ToastContent(
    modifier: Modifier = Modifier,
    text: String
) {
    val shape = RoundedCornerShape(4.dp)
    Box(
        modifier = modifier
            .clip(shape)
            .background(Color.White)
            .border(1.dp, Color.Black, shape)
            .padding(horizontal = 8.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_album
                ),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text)
        }
    }
}

@Preview
@Composable
fun ToastContentPreview() {
    AlertToast(visible = true, text = "this is a test")
}