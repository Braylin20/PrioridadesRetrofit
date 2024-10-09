package com.example.prioridadesretrofit.presentation.sistema

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.prioridadesretrofit.data.remote.dto.SistemaDto

@Composable
fun SistemaListScreen(
    viewModel: SistemaViewModel = hiltViewModel(),
    goToSistemaScreen: (Int) -> Unit,
    createSistema: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaListBody(
        uiState = uiState,
        onAddSistema = createSistema,
        goToSistemaScreen = goToSistemaScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SistemaListBody(
    uiState: SistemaViewModel.UiState,
    onAddSistema: () -> Unit,
    goToSistemaScreen: (Int) -> Unit,
) {

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddSistema) {
                Icon(Icons.Filled.Add, "Agregar nueva entidad")
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "ID", modifier = Modifier.weight(0.1f))
                Text(text = "Nombre Sistema", modifier = Modifier.weight(0.3f))
                Text(text = "Descripción", modifier = Modifier.weight(0.3f))
            }

            Divider()

            Text(text = uiState.message?:"", color = Color.Red)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(uiState.sistemas) { sistema ->
                    PrioridadListRow(sistema, goToSistemaScreen)
                }
            }
        }
    }
}

@Composable
fun PrioridadListRow(
    it: SistemaDto,
    goToTicketScreen: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { goToTicketScreen(it.sistemaId) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = it.sistemaId.toString(), modifier = Modifier.weight(0.10f))
        Text(text = it.nombreSistema, modifier = Modifier.weight(0.300f))
        Text(text = it.descripcion.toString(), modifier = Modifier.weight(0.30f))
    }
}