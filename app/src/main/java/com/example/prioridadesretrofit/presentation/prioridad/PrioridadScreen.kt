package com.example.prioridadesretrofit.presentation.prioridad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PrioridadScreen(
    viewModel: PrioridadViewModel = hiltViewModel(),
    onGoToPrioridadListScreen: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadScreenBody(
        uiState = uiState,
        goBackListScreen = onGoToPrioridadListScreen,
        onDescripcionChange = viewModel::onDescripcionChange,
        onDiasCompromisoChange = viewModel::onDiasCompromisoChange,
        save = viewModel::addPrioridad,
    )
}


@Composable
fun PrioridadScreenBody(
    uiState: PrioridadViewModel.UiState,
    goBackListScreen: () -> Unit,
    onDescripcionChange:(String) -> Unit,
    onDiasCompromisoChange:(Int)-> Unit,
    save: ()-> Unit,

) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = goBackListScreen,

            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            OutlinedTextField(
                label = { Text("Descripción") },
                value = uiState.descripcion?:"",
                onValueChange = onDescripcionChange,
                isError = uiState.descripcionError!= null,
                modifier = Modifier.fillMaxWidth()
            )
            if(uiState.descripcionError != null){
                Text(
                    text = uiState.descripcionError,
                    color = Color.Red
                )
            }
            OutlinedTextField(
                label = { Text("Días Compromiso") },
                value = uiState.diasCompromiso?.toString()?:"",
                onValueChange = {diasCompromiso -> onDiasCompromisoChange(diasCompromiso.toInt())},
                isError = uiState.descripcionError!= null,
                modifier = Modifier.fillMaxWidth()
            )
            if(uiState.diasCompromisoError != null){
                Text(
                    text = uiState.diasCompromisoError,
                    color = Color.Red
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(9.dp),
                horizontalArrangement = Arrangement.Center
            ){
                OutlinedButton(
                    onClick = save
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "save button"
                    )
                    Text(text = "Guardar")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if(uiState.message!= null){
                    Text(text = uiState.message, color = Color.Green)
                }
            }

        }
    }
}