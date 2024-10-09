package com.example.prioridadesretrofit.presentation.cliente

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
import com.example.prioridadesretrofit.data.remote.dto.ClienteDto
import com.example.prioridadesretrofit.data.remote.dto.SistemaDto
import com.example.prioridadesretrofit.presentation.sistema.SistemaViewModel

@Composable
fun ClienteListScreen(
    viewModel: CLienteViewModel = hiltViewModel(),
    goToClienteScreen: (Int) -> Unit,
    createCliente: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ClienteListBody(
        uiState = uiState,
        onAddCliente = createCliente,
        goToClienteScreen = goToClienteScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteListBody(
    uiState: CLienteViewModel.UiState,
    onAddCliente: () -> Unit,
    goToClienteScreen: (Int) -> Unit,
) {

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCliente) {
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
                Text(text = "Nombre ", modifier = Modifier.weight(0.3f))
                Text(text = "Email", modifier = Modifier.weight(0.3f))
                Text(text = "Teléfono", modifier = Modifier.weight(0.3f))
                Text(text = "Dirección", modifier = Modifier.weight(0.3f))
            }

            Divider()


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(uiState.clientes) { cliente ->
                    PrioridadListRow(cliente, goToClienteScreen)
                }
            }
        }
    }
}

@Composable
fun PrioridadListRow(
    it: ClienteDto,
    goToClienteScreen: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { goToClienteScreen(it.clienteId) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = it.clienteId.toString(), modifier = Modifier.weight(0.1f))
        Text(text = it.nombre, modifier = Modifier.weight(0.2f))
        Text(text = it.email, modifier = Modifier.weight(0.4f).padding(horizontal = 5.dp))
        Text(text = it.telefono, modifier = Modifier.weight(0.4f))
        Text(text = it.direccion, modifier = Modifier.weight(0.3f))
    }
}