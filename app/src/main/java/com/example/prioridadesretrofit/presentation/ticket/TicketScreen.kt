package com.example.prioridadesretrofit.presentation.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.prioridadesretrofit.data.remote.dto.ClienteDto
import com.example.prioridadesretrofit.data.remote.dto.PrioridadDto
import com.example.prioridadesretrofit.presentation.prioridad.PrioridadViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun TicketScreen(
    viewModelTicket: TicketViewModel = hiltViewModel(),
    viewModelPrioridad: PrioridadViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val uiStateTicket by viewModelTicket.uiState.collectAsStateWithLifecycle()
    var prioridadList by remember { mutableStateOf<List<PrioridadDto>>(emptyList()) }
    var clientesList by remember { mutableStateOf<List<ClienteDto>>(emptyList()) }

    LaunchedEffect(Unit) {
        prioridadList = viewModelPrioridad.getListPrioridades()
        clientesList = viewModelTicket.getClientes()
    }


    TicketBodyScreen(
        uiState = uiStateTicket,
        onNavigateBack = onNavigateBack,
        onDescripcionChange = viewModelTicket::onDescripcionChange,
        onAsuntoChange = viewModelTicket::onAsuntoChange,
        onPrioridadIdChange = viewModelTicket::onPrioridadIdChange,
        saveTicket = viewModelTicket::save,
        nuevoTicket = viewModelTicket::nuevo,
        prioridades = prioridadList,
        onFechaChange = viewModelTicket::onDateChange,
        convertMillisToDate = viewModelTicket::convertMillisToDate,
        onClienteChange = viewModelTicket::onClienteChange,
        clientesList = clientesList
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketBodyScreen(
    uiState: TicketViewModel.UiState,
    onNavigateBack: () -> Unit,
    onDescripcionChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onClienteChange: (Int) -> Unit,
    onPrioridadIdChange: (Int) -> Unit,
    saveTicket: () -> Unit,
    nuevoTicket: () -> Unit,
    onFechaChange: (String) -> Unit,
    convertMillisToDate: (Long) -> String,
    prioridades: List<PrioridadDto>,
    clientesList: List<ClienteDto>
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { selectedDateMillis ->
            val selectedDate = convertMillisToDate(selectedDateMillis)
            onFechaChange(selectedDate)
            delay(300)
            showDatePicker = false
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateBack,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Volver Atrás")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Registro de Tickets",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    InputSelectPrioridad(
                        label = "Prioridades",
                        prioridades = prioridades,
                        onOptionSelected = onPrioridadIdChange
                    )
                    InputSelectCliente(
                        label = "Clientes",
                        clientes = clientesList,
                        onOptionSelected = onClienteChange
                    )

                    OutlinedTextField(
                        label = { Text(text = "Asunto") },
                        value = uiState.asunto ?: "",
                        onValueChange = onAsuntoChange,
                        modifier = Modifier.fillMaxWidth(),
                    )
//                    OutlinedTextField(
//                        value = uiState.date ?: "",
//                        onValueChange = {},
//                        readOnly = true,
//                        label = { Text("Fecha") },
//                        trailingIcon = {
//                            IconButton(onClick = { showDatePicker = !showDatePicker }) {
//                                Icon(
//                                    imageVector = Icons.Default.DateRange,
//                                    contentDescription = null
//                                )
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(64.dp)
//                    )
//                    if (showDatePicker) {
//                        Popup(
//                            onDismissRequest = { showDatePicker = false },
//                            alignment = Alignment.TopStart
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .offset(y = 50.dp)
//                                    .background(MaterialTheme.colorScheme.background)
//                                    .padding(16.dp)
//                            ) {
//                                DatePicker(
//                                    state = datePickerState,
//                                    showModeToggle = false
//                                )
//                            }
//                        }
//                    }
                    OutlinedTextField(
                        label = { Text(text = "Descripción") },
                        value = uiState.descripcion ?: "",
                        onValueChange = onDescripcionChange,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Text(
                        text = uiState.message ?: "",
                        color = Color.Green,
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = nuevoTicket
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null
                            )
                            Text(text = "Nuevo")
                        }
                        OutlinedButton(
                            onClick = { saveTicket() }

                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                            Text(text = "Guardar")
                        }

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSelectPrioridad(
    prioridades: List<PrioridadDto>,
    label: String = "Seleccionar opción",
    onOptionSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            trailingIcon = {
                TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            prioridades.forEach { prioridad ->
                DropdownMenuItem(
                    text = { Text(prioridad.descripcion) },
                    onClick = {
                        selectedOption = prioridad.descripcion
                        expanded = false
                        onOptionSelected(prioridad.prioridadId)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSelectCliente(
    clientes: List<ClienteDto>,
    label: String = "Seleccionar opción",
    onOptionSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            trailingIcon = {
                TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            clientes.forEach { cliente ->
                DropdownMenuItem(
                    text = { Text(cliente.nombre) },
                    onClick = {
                        selectedOption = cliente.nombre
                        expanded = false
                        onOptionSelected(cliente.clienteId)
                    }
                )
            }
        }
    }
}