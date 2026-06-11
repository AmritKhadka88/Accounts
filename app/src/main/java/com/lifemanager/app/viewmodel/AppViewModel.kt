package com.lifemanager.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lifemanager.app.LifeManagerApp
import com.lifemanager.app.data.entities.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as LifeManagerApp).repository

    val allExpenses = repository.getAllExpenses().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allIncomes = repository.getAllIncomes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allLoans = repository.getAllLoans().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allVehicles = repository.getAllVehicles().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allFuelEntries = repository.getAllFuelEntries().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allNotes = repository.getAllNotes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allReminders = repository.getAllReminders().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allSavings = repository.getAllSavings().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val totalSavings = repository.getTotalSavings().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun insertExpense(title: String, amount: Double, category: String, notes: String = "") = viewModelScope.launch {
        repository.insertExpense(ExpenseEntity(title=title, amount=amount, category=category, date=System.currentTimeMillis(), notes=notes))
    }
    fun deleteExpense(e: ExpenseEntity) = viewModelScope.launch { repository.deleteExpense(e) }
    fun insertIncome(title: String, amount: Double, source: String, notes: String = "") = viewModelScope.launch {
        repository.insertIncome(IncomeEntity(title=title, amount=amount, source=source, date=System.currentTimeMillis(), notes=notes))
    }
    fun deleteIncome(i: IncomeEntity) = viewModelScope.launch { repository.deleteIncome(i) }
    fun insertLoan(name: String, lenderBorrower: String, principal: Double, interestRate: Double, interestType: String, startDate: Long, dueDate: Long) = viewModelScope.launch {
        repository.insertLoan(LoanEntity(name=name, lenderBorrower=lenderBorrower, principal=principal, interestRate=interestRate, interestType=interestType, isCompound=false, startDate=startDate, dueDate=dueDate))
    }
    fun deleteLoan(l: LoanEntity) = viewModelScope.launch { repository.deleteLoan(l) }
    fun insertVehicle(name: String, regNumber: String, fuelType: String) = viewModelScope.launch {
        repository.insertVehicle(VehicleEntity(name=name, registrationNumber=regNumber, fuelType=fuelType))
    }
    fun deleteVehicle(v: VehicleEntity) = viewModelScope.launch { repository.deleteVehicle(v) }
    fun insertFuelEntry(vehicleId: Long, odometer: Double, quantity: Double, cost: Double, station: String = "") = viewModelScope.launch {
        repository.insertFuelEntry(FuelEntryEntity(vehicleId=vehicleId, date=System.currentTimeMillis(), odometer=odometer, quantity=quantity, cost=cost, station=station))
    }
    fun deleteFuelEntry(f: FuelEntryEntity) = viewModelScope.launch { repository.deleteFuelEntry(f) }
    fun insertNote(title: String, content: String) = viewModelScope.launch {
        val now = System.currentTimeMillis()
        repository.insertNote(NoteEntity(title=title, content=content, createdAt=now, updatedAt=now))
    }
    fun deleteNote(n: NoteEntity) = viewModelScope.launch { repository.deleteNote(n) }
    fun insertReminder(title: String, description: String, dueDate: Long) = viewModelScope.launch {
        repository.insertReminder(ReminderEntity(title=title, description=description, dueDate=dueDate))
    }
    fun deleteReminder(r: ReminderEntity) = viewModelScope.launch { repository.deleteReminder(r) }
    fun insertSavings(name: String, type: String, amount: Double) = viewModelScope.launch {
        repository.insertSavings(SavingsEntity(name=name, type=type, amount=amount, date=System.currentTimeMillis()))
    }
    fun deleteSavings(s: SavingsEntity) = viewModelScope.launch { repository.deleteSavings(s) }
}

class AppViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
