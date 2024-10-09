package com.example.prioridadesretrofit.presentation.cliente

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
import com.example.prioridadesretrofit.presentation.prioridad.PrioridadViewModel

@Composable
fun ClienteScreen(
    viewModel: CLienteViewModel = hiltViewModel(),
    onGoToClienteListScreen: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadScreenBody(
        uiState = uiState,
        goBackListScreen = onGoToClienteListScreen,
        onNameChange = viewModel::onNombreChange,
        onTelefonoChange = viewModel::onTelefonoChange,
        onEmailChange = viewModel::onEmailChange,
        onDireccionChange = viewModel::onDireccionChange,
        save = viewModel::addCliente,
    )
}


@Composable
fun PrioridadScreenBody(
    uiState: CLienteViewModel.UiState,
    goBackListScreen: () -> Unit,
    onNameChange:(String) -> Unit,
    onTelefonoChange:(String)-> Unit,
    onEmailChange:(String)-> Unit,
    onDireccionChange:(String)-> Unit,
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
                label = { Text("Nombre") },
                value = uiState.nombre?:"",
                onValueChange = onNameChange,
                isError = uiState.nombreError!= null,
                modifier = Modifier.fillMaxWidth()
            )
            if(uiState.nombreError != null){
                Text(
                    text = uiState.nombreError,
                    color = Color.Red
                )
            }
            OutlinedTextField(
                label = { Text("Teléfono") },
                value = uiState.telefono?:"",
                onValueChange = onTelefonoChange,
                isError = uiState.telefonoError!= null,
                modifier = Modifier.fillMaxWidth()
            )
            if(uiState.telefonoError != null){
                Text(
                    text = uiState.telefonoError,
                    color = Color.Red
                )
            }
            OutlinedTextField(
                label = { Text("Email") },
                value = uiState.email?:"",
                onValueChange = onEmailChange,
                isError = uiState.emailError!= null,
                modifier = Modifier.fillMaxWidth()
            )
            if(uiState.emailError != null){
                Text(
                    text = uiState.emailError,
                    color = Color.Red
                )
            }
            OutlinedTextField(
                label = { Text("Dirección") },
                value = uiState.direccion?:"",
                onValueChange = onDireccionChange,
                isError = uiState.direccionError!= null,
                modifier = Modifier.fillMaxWidth()
            )
            if(uiState.direccionError != null){
                Text(
                    text = uiState.direccionError,
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