package com.lifemanager.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lifemanager.app.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleScreen(vm: AppViewModel = viewModel()) {
    val vehicles by vm.allVehicles.collectAsStateWithLifecycle()
    val fuelEntries by vm.allFuelEntries.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableStateOf(0) }
    var showAddVehicle by remember { mutableStateOf(false) }
    var showAddFuel by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Vehicle", fontWeight = FontWeight.Bold) }) }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Vehicles") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Fuel Log") })
            }
            Box(Modifier.fillMaxSize()) {
                when (selectedTab) {
                    0 -> {
                        if (vehicles.isEmpty()) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No vehicles yet", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        } else {
                            LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(vehicles) { v ->
                                    Card(Modifier.fillMaxWidth()) {
                                        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                            Column(Modifier.weight(1f)) {
                                                Text(v.name, fontWeight = FontWeight.Medium)
                                                Text("${v.registrationNumber} • ${v.fuelType}", style = MaterialTheme.typography.bodySmall)
                                            }
                                            IconButton(onClick = { vm.deleteVehicle(v) }) { Icon(Icons.Filled.Delete, null, tint = MaterialTheme.colorScheme.error) }
                                        }
                                    }
                                }
                            }
                        }
                        FloatingActionButton(onClick = { showAddVehicle = true }, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) { Icon(Icons.Filled.Add, null) }
                    }
                    1 -> {
                        if (fuelEntries.isEmpty()) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No fuel entries yet", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        } else {
                            LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                item {
                                    Card(Modifier.fillMaxWidth()) {
                                        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Column { Text("Total Fuel", style = MaterialTheme.typography.labelSmall); Text("${"%.1f".format(fuelEntries.sumOf { it.quantity })} L", fontWeight = FontWeight.Bold) }
                                            Column { Text("Total Cost", style = MaterialTheme.typography.labelSmall); Text("${"%.2f".format(fuelEntries.sumOf { it.cost })}", fontWeight = FontWeight.Bold) }
                                        }
                                    }
                                }
                                items(fuelEntries) { f ->
                                    Card(Modifier.fillMaxWidth()) {
                                        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                            Column(Modifier.weight(1f)) {
                                                Text("${f.quantity}L @ ${f.station.ifEmpty { "Unknown" }}", fontWeight = FontWeight.Medium)
                                                Text("Odometer: ${f.odometer}km", style = MaterialTheme.typography.bodySmall)
                                            }
                                            Text("${"%.2f".format(f.cost)}", fontWeight = FontWeight.Bold)
                                            IconButton(onClick = { vm.deleteFuelEntry(f) }) { Icon(Icons.Filled.Delete, null, tint = MaterialTheme.colorScheme.error) }
                                        }
                                    }
                                }
                            }
                        }
                        FloatingActionButton(onClick = { showAddFuel = true }, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) { Icon(Icons.Filled.Add, null) }
                    }
                }
            }
        }
    }

    if (showAddVehicle) {
        var name by remember { mutableStateOf("") }
        var reg by remember { mutableStateOf("") }
        var fuel by remember { mutableStateOf("Petrol") }
        AlertDialog(onDismissRequest = { showAddVehicle = false }, title = { Text("Add Vehicle") },
            text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Vehicle Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(value = reg, onValueChange = { reg = it }, label = { Text("Registration Number") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(value = fuel, onValueChange = { fuel = it }, label = { Text("Fuel Type") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            }},
            confirmButton = { Button(onClick = { if (name.isNotEmpty()) { vm.insertVehicle(name, reg, fuel); showAddVehicle = false } }) { Text("Save") } },
            dismissButton = { TextButton(onClick = { showAddVehicle = false }) { Text("Cancel") } }
        )
    }

    if (showAddFuel) {
        var odometer by remember { mutableStateOf("") }
        var quantity by remember { mutableStateOf("") }
        var cost by remember { mutableStateOf("") }
        var station by remember { mutableStateOf("") }
        AlertDialog(onDismissRequest = { showAddFuel = false }, title = { Text("Add Fuel Entry") },
            text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (vehicles.isEmpty()) {
                    Text("Please add a vehicle first!", color = MaterialTheme.colorScheme.error)
                } else {
                    OutlinedTextField(value = odometer, onValueChange = { odometer = it }, label = { Text("Odometer (km)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = quantity, onValueChange = { quantity = it }, label = { Text("Quantity (L)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = cost, onValueChange = { cost = it }, label = { Text("Cost") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = station, onValueChange = { station = it }, label = { Text("Station (optional)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                }
            }},
            confirmButton = { Button(onClick = {
                val o = odometer.toDoubleOrNull(); val q = quantity.toDoubleOrNull(); val c = cost.toDoubleOrNull()
                if (vehicles.isNotEmpty() && o != null && q != null && c != null) { vm.insertFuelEntry(vehicles[0].id, o, q, c, station); showAddFuel = false }
            }) { Text("Save") } },
            dismissButton = { TextButton(onClick = { showAddFuel = false }) { Text("Cancel") } }
        )
    }
}