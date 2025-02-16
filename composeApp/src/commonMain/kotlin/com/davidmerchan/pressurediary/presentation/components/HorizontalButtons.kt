package com.davidmerchan.pressurediary.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class HorizontalButtonItem<T>(
    val image: DrawableResource,
    val title: String,
    val state: T
)

@Composable
fun <T> HorizontalButtons(
    modifier: Modifier = Modifier,
    buttons: List<HorizontalButtonItem<T>>,
    selectState: T,
    onSelectedState: (T) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        buttons.forEach { item ->
            HorizontalButton(
                image = item.image,
                title = item.title,
                state = item.state,
                isSelected = selectState == item.state,
                onSelectState = {
                    onSelectedState(it)
                }
            )
        }
    }
}

@Composable
fun <T> RowScope.HorizontalButton(
    image: DrawableResource,
    title: String,
    state: T,
    isSelected: Boolean = false,
    onSelectState: (T) -> Unit = {}
) {
    Button(
        onClick = {
            onSelectState(state)
        },
        modifier = Modifier
            .padding(8.dp)
            .weight(1f)
            .aspectRatio(1f)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    )
                } else Modifier
            ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}
