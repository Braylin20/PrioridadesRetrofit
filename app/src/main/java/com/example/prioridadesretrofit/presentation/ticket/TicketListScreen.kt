package com.example.prioridadesretrofit.presentation.ticket

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.prioridadesretrofit.data.remote.dto.ClienteDto
import com.example.prioridadesretrofit.data.remote.dto.PrioridadDto
import com.example.prioridadesretrofit.data.remote.dto.TicketDto
import com.example.prioridadesretrofit.presentation.prioridad.PrioridadViewModel
import kotlinx.coroutines.delay

@Composable
fun TicketListScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    goToTicketScreen: (Int) -> Unit,
    createTicket: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var listPrioridades by  remember { mutableStateOf<List<PrioridadDto>>(emptyList()) }
    var listClientes by  remember { mutableStateOf<List<ClienteDto>>(emptyList()) }
    LaunchedEffect(Unit) {
        listPrioridades = viewModel.getPrioridades()
        listClientes = viewModel.getClientes()
    }
    TicketListBody(
        uiState = uiState,
        onAddPrioridad = createTicket,
        goToTicketScreen = goToTicketScreen,
        listPrioridades = listPrioridades,
        listClientes = listClientes
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListBody(
    uiState: TicketViewModel.UiState,
    onAddPrioridad: () -> Unit,
    goToTicketScreen: (Int) -> Unit,
    listPrioridades: List<PrioridadDto>,
    listClientes: List<ClienteDto>
) {


    Scaffold (
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPrioridad) {
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
                Text(text = "Prioridad", modifier = Modifier.weight(0.3f))
                Text(text = "Cliente", modifier = Modifier.weight(0.3f))
                Text(text = "Asunto", modifier = Modifier.weight(0.3f))
            }

            Divider()

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Text(text = uiState.message?:"", color = Color.Red)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(uiState.tickets) { ticket ->
                    val prioridadDescripcion= listPrioridades
                        .find{prioridad -> prioridad.prioridadId == ticket.prioridadesId}?.descripcion?:"No tiene"
                    val nombreCliente = listClientes.find { cliente -> cliente.clienteId == ticket.clientesId }?.nombre?: "No tiene"
                    PrioridadListRow(ticket, goToTicketScreen, prioridadDescripcion,nombreCliente)
                }
            }
        }
    }
}

@Composable
fun PrioridadListRow(
    ticket: TicketDto,
    goToTicketScreen: (Int) -> Unit,
    descripcion: String,
    nombreCliente: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { goToTicketScreen(ticket.ticketId) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = ticket.ticketId.toString(), modifier = Modifier.weight(0.1f))
        Text(text = descripcion, modifier = Modifier.weight(0.3f))
        Text(text = nombreCliente, modifier = Modifier.weight(0.3f))
        Text(text = ticket.asunto.toString(), modifier = Modifier.weight(0.3f))
    }
}