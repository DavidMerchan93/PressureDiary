package com.davidmerchan.pressurediary.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.davidmerchan.pressurediary.presentation.newRecord.UserState
import org.jetbrains.compose.resources.stringResource
import pressurediary.composeapp.generated.resources.Res
import pressurediary.composeapp.generated.resources.rest
import pressurediary.composeapp.generated.resources.title_general_female
import pressurediary.composeapp.generated.resources.title_general_male
import pressurediary.composeapp.generated.resources.title_new_record
import pressurediary.composeapp.generated.resources.title_settings
import pressurediary.composeapp.generated.resources.title_status_rest

enum class Gender {
    MALE,
    FEMALE,
    DEFAULT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {

    var uid by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(0) }
    var weight by remember { mutableStateOf(.0) }
    var height by remember { mutableStateOf(.0) }
    var gender by remember { mutableStateOf(Gender.DEFAULT) }
    var smoker by remember { mutableStateOf(false) }

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
                    age = it.toInt()
                }
            )
            PressureTextField(
                label = "Peso",
                value = weight.toString(),
                type = KeyboardType.Decimal,
                onValueChange = {
                    weight = it.toDouble()
                }
            )
            PressureTextField(
                label = "Altura",
                value = height.toString(),
                type = KeyboardType.Decimal,
                onValueChange = {
                    height = it.toDouble()
                }
            )

            Text("Genero:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalButtons(
                buttons = listOf(
                    HorizontalButtonItem(
                        image = Res.drawable.rest,
                        title = stringResource(Res.string.title_general_female),
                        state = Gender.FEMALE
                    ),
                    HorizontalButtonItem(
                        image = Res.drawable.rest,
                        title = stringResource(Res.string.title_general_male),
                        state = Gender.MALE
                    )
                ),
                selectState = gender,
                onSelectedState = {
                    gender = it
                }
            )

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
