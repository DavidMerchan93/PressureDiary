package com.davidmerchan.pressurediary.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davidmerchan.pressurediary.domain.model.IMCClassification
import com.davidmerchan.pressurediary.domain.model.IMCModel
import org.jetbrains.compose.resources.stringResource
import pressurediary.composeapp.generated.resources.Res
import pressurediary.composeapp.generated.resources.title_imc_result
import pressurediary.composeapp.generated.resources.title_low_weight
import pressurediary.composeapp.generated.resources.title_no_imc
import pressurediary.composeapp.generated.resources.title_normal_weight
import pressurediary.composeapp.generated.resources.title_obese
import pressurediary.composeapp.generated.resources.title_overweight

@Composable
fun IMCComponent(modifier: Modifier = Modifier, imcModel: IMCModel?) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            if (imcModel != null && imcModel.classification != IMCClassification.DEFAULT) {
                Text(
                    stringResource(
                        Res.string.title_imc_result,
                        imcModel.imc.toString(),
                        when(imcModel.classification) {
                            IMCClassification.LOW_WEIGHT -> stringResource(Res.string.title_low_weight)
                            IMCClassification.NORMAL_WEIGHT -> stringResource(Res.string.title_normal_weight)
                            IMCClassification.OVERWEIGHT -> stringResource(Res.string.title_overweight)
                            IMCClassification.OBESE -> stringResource(Res.string.title_obese)
                            IMCClassification.DEFAULT -> ""
                        }
                    )
                )
            } else {
                Text(stringResource(Res.string.title_no_imc))
            }
        }
    }
}