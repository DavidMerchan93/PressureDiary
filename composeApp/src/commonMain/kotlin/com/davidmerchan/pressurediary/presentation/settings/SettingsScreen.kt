package com.davidmerchan.pressurediary.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidmerchan.pressurediary.presentation.components.HorizontalButtonItem
import com.davidmerchan.pressurediary.presentation.components.HorizontalButtons
import com.davidmerchan.pressurediary.presentation.components.PressureDialog
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pressurediary.composeapp.generated.resources.Res
import pressurediary.composeapp.generated.resources.btn_ok_dialog
import pressurediary.composeapp.generated.resources.btn_save_record
import pressurediary.composeapp.generated.resources.female
import pressurediary.composeapp.generated.resources.male
import pressurediary.composeapp.generated.resources.title_failure_save_settings
import pressurediary.composeapp.generated.resources.title_gender
import pressurediary.composeapp.generated.resources.title_general_female
import pressurediary.composeapp.generated.resources.title_general_male
import pressurediary.composeapp.generated.resources.title_is_smoke
import pressurediary.composeapp.generated.resources.title_settings
import pressurediary.composeapp.generated.resources.title_success_save_settings

enum class Gender(val id: Int) {
    FEMALE(0),
    MALE(1),
    DEFAULT(2)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    val settingsViewModel = koinViewModel<SettingsViewModel>()
    val settingsState = settingsViewModel.settingsState.value

    var uid by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(0) }
    var weight by remember { mutableStateOf(.0) }
    var height by remember { mutableStateOf(.0) }
    var gender by remember { mutableStateOf(Gender.DEFAULT) }
    var smoker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        settingsViewModel.handleEvent(SettingsScreenEvents.LoadData)
    }

    when {
        settingsState.userData != null -> {
            with(settingsState.userData) {
                uid = this.uid
                age = this.age
                weight = this.weight
                height = this.height
                smoker = this.smoke
                gender = when (this.gender) {
                    0 -> Gender.FEMALE
                    1 -> Gender.MALE
                    else -> Gender.DEFAULT
                }
            }
        }

        settingsState.isSaved == true -> {
            PressureDialog(
                detail = stringResource(Res.string.title_success_save_settings),
                onDismissRequest = { onBackPressed() },
                buttonOk = stringResource(Res.string.btn_ok_dialog)
            )
        }

        settingsState.isSaved == false -> {
            PressureDialog(
                detail = stringResource(Res.string.title_failure_save_settings),
                onDismissRequest = { onBackPressed() },
                buttonOk = stringResource(Res.string.btn_ok_dialog)
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.title_settings))
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding).padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            PressureTextField(
                label = "Numero de identificacion",
                value = uid,
                onValueChange = {
                    uid = it
                }
            )
            PressureTextField(
                label = "Edad",
                value = age.toString(),
                type = KeyboardType.Number,
                onValueChange = {
                    age = if (it.isNotEmpty()) {
                        it.toInt()
                    } else 0
                }
            )
            PressureTextField(
                label = "Peso",
                value = weight.toString(),
                type = KeyboardType.Decimal,
                onValueChange = {
                    weight = if (it.isNotEmpty()) {
                        it.toDouble()
                    } else .0
                }
            )
            PressureTextField(
                label = "Altura",
                value = height.toString(),
                type = KeyboardType.Decimal,
                onValueChange = {
                    height = if (it.isNotEmpty()) {
                        it.toDouble()
                    } else .0
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(Res.string.title_is_smoke), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(20.dp))
                Switch(
                    checked = smoker,
                    onCheckedChange = { smoker = it }
                )
            }


            Spacer(modifier = Modifier.height(8.dp))
            Text(stringResource(Res.string.title_gender), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            HorizontalButtons(
                modifier = Modifier.padding(12.dp),
                buttons = listOf(
                    HorizontalButtonItem(
                        image = Res.drawable.female,
                        title = stringResource(Res.string.title_general_female),
                        state = Gender.FEMALE
                    ),
                    HorizontalButtonItem(
                        image = Res.drawable.male,
                        title = stringResource(Res.string.title_general_male),
                        state = Gender.MALE
                    )
                ),
                selectState = gender,
                onSelectedState = { gender = it }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    settingsViewModel.handleEvent(
                        SettingsScreenEvents.SaveUserSettings(
                            uid = uid,
                            age = age,
                            weight = weight,
                            height = height,
                            smoke = smoker,
                            gender = gender
                        )
                    )
                }
            ) {
                Text(stringResource(Res.string.btn_save_record))
            }

        }
    }
}

@Composable
fun PressureTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    type: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(label)
        },
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontSize = 18.sp
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = type
        ),
    )
    Spacer(modifier = Modifier.height(4.dp))
}
