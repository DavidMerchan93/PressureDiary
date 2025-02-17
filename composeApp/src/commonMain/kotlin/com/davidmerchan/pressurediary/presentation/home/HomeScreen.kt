package com.davidmerchan.pressurediary.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davidmerchan.pressurediary.domain.model.CardiovascularRiskModel
import com.davidmerchan.pressurediary.domain.model.IMCClassification
import com.davidmerchan.pressurediary.presentation.home.components.IMCComponent
import com.davidmerchan.pressurediary.presentation.home.components.LastRecords
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pressurediary.composeapp.generated.resources.Res
import pressurediary.composeapp.generated.resources.title_cardio_risk
import pressurediary.composeapp.generated.resources.title_high_risk
import pressurediary.composeapp.generated.resources.title_home
import pressurediary.composeapp.generated.resources.title_imc_result
import pressurediary.composeapp.generated.resources.title_low_risk
import pressurediary.composeapp.generated.resources.title_low_weight
import pressurediary.composeapp.generated.resources.title_medium_risk
import pressurediary.composeapp.generated.resources.title_no_cardio_risk
import pressurediary.composeapp.generated.resources.title_no_imc
import pressurediary.composeapp.generated.resources.title_normal_weight
import pressurediary.composeapp.generated.resources.title_obese
import pressurediary.composeapp.generated.resources.title_overweight
import pressurediary.composeapp.generated.resources.title_very_high_risk

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAddNewRecord: () -> Unit,
    onGoToSettings: () -> Unit,
    onGotToHistory: () -> Unit
) {
    val homeViewModel = koinViewModel<HomeViewModel>()
    val homeState = homeViewModel.homeState.value

    LaunchedEffect(Unit) {
        homeViewModel.handleEvent(HomeScreenEvents.LoadData)
        homeViewModel.handleEvent(HomeScreenEvents.GetIMC)
        homeViewModel.handleEvent(HomeScreenEvents.GetCardiovascularRisk)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.title_home))
                },
                actions = {
                    TextButton(
                        onClick = {
                            onGoToSettings()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddNewRecord()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp),
        ) {
            LazyColumn {
                item {
                    LastRecords(
                        pressureLogs = homeState.homeRecords,
                        onGotToHistory = onGotToHistory
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    IMCComponent(imcModel = homeState.imcResult)
                    Spacer(modifier = Modifier.height(8.dp))
                    CardiovascularRisk(cardiovascularRisk = homeState.cardiovascularRisk)
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
fun CardiovascularRisk(
    modifier: Modifier = Modifier,
    cardiovascularRisk: CardiovascularRiskModel?
) {
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
            if (cardiovascularRisk != null && cardiovascularRisk != CardiovascularRiskModel.NOT_APPLICABLE) {
                Text(
                    stringResource(
                        Res.string.title_cardio_risk,
                        when(cardiovascularRisk) {
                            CardiovascularRiskModel.LOW -> stringResource(Res.string.title_low_risk)
                            CardiovascularRiskModel.MEDIUM -> stringResource(Res.string.title_medium_risk)
                            CardiovascularRiskModel.HIGH -> stringResource(Res.string.title_high_risk)
                            CardiovascularRiskModel.VERY_HIGH -> stringResource(Res.string.title_very_high_risk)
                            CardiovascularRiskModel.NOT_APPLICABLE -> ""
                        }
                    )
                )
            } else {
                Text(stringResource(Res.string.title_no_cardio_risk))
            }
        }
    }
}
