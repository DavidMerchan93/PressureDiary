package com.davidmerchan.pressurediary.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidmerchan.pressurediary.domain.model.PressureLogModel
import com.davidmerchan.pressurediary.presentation.utils.formatTimestamp
import org.jetbrains.compose.resources.stringResource
import pressurediary.composeapp.generated.resources.Res
import pressurediary.composeapp.generated.resources.title_press_item

@Composable
fun PressureLogItem(pressure: PressureLogModel) {
    Column {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = formatTimestamp(pressure.date),
                fontSize = 18.sp
            )
            Text(
                text = stringResource(Res.string.title_press_item),
                fontSize = 12.sp,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${pressure.systolic} / ${pressure.diastolic}",
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        HorizontalDivider()
    }
}
