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
    PrioridadListBody(
        uiState = uiState,
        onAddPrioridad = createTicket,
        goToTicketScreen = goToTicketScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadListBody(
    uiState: TicketViewModel.UiState,
    onAddPrioridad: () -> Unit,
    goToTicketScreen: (Int) -> Unit,
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
                Text(text = "Fecha", modifier = Modifier.weight(0.3f))
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
                    PrioridadListRow(ticket, goToTicketScreen)
                }
            }
        }
    }
}

@Composable
fun PrioridadListRow(
    it: TicketDto,
    goToTicketScreen: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { goToTicketScreen(it.ticketId) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = it.ticketId.toString(), modifier = Modifier.weight(0.10f))
        Text(text = it.prioridadId.toString(), modifier = Modifier.weight(0.300f))
        Text(text = it.date.toString(), modifier = Modifier.weight(0.30f))
        Text(text = it.clienteId.toString(), modifier = Modifier.weight(0.30f))
        Text(text = it.asunto.toString(), modifier = Modifier.weight(0.30f))
    }
}