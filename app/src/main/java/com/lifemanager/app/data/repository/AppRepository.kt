package com.lifemanager.app.data.repository

import com.lifemanager.app.data.dao.*
import com.lifemanager.app.data.entities.*

class AppRepository(
    private val expenseDao: ExpenseDao,
    private val incomeDao: IncomeDao,
    private val loanDao: LoanDao,
    private val vehicleDao: VehicleDao,
    private val fuelDao: FuelDao,
    private val shoppingDao: ShoppingDao,
    private val addressDao: AddressDao,
    private val reminderDao: ReminderDao,
    private val noteDao: NoteDao,
    private val documentDao: DocumentDao,
    private val savingsDao: SavingsDao
) {
    fun getAllExpenses() = expenseDao.getAllExpenses()
    fun getTotalExpenses(start: Long, end: Long) = expenseDao.getTotalExpenses(start, end)
    suspend fun insertExpense(expense: ExpenseEntity) = expenseDao.insertExpense(expense)
    suspend fun updateExpense(expense: ExpenseEntity) = expenseDao.updateExpense(expense)
    suspend fun deleteExpense(expense: ExpenseEntity) = expenseDao.deleteExpense(expense)
    fun getAllIncomes() = incomeDao.getAllIncomes()
    fun getTotalIncome(start: Long, end: Long) = incomeDao.getTotalIncome(start, end)
    suspend fun insertIncome(income: IncomeEntity) = incomeDao.insertIncome(income)
    suspend fun deleteIncome(income: IncomeEntity) = incomeDao.deleteIncome(income)
    fun getAllLoans() = loanDao.getAllLoans()
    suspend fun insertLoan(loan: LoanEntity) = loanDao.insertLoan(loan)
    suspend fun deleteLoan(loan: LoanEntity) = loanDao.deleteLoan(loan)
    suspend fun insertPayment(payment: LoanPaymentEntity) = loanDao.insertPayment(payment)
    fun getAllVehicles() = vehicleDao.getAllVehicles()
    suspend fun insertVehicle(vehicle: VehicleEntity) = vehicleDao.insertVehicle(vehicle)
    suspend fun deleteVehicle(vehicle: VehicleEntity) = vehicleDao.deleteVehicle(vehicle)
    fun getAllFuelEntries() = fuelDao.getAllFuelEntries()
    fun getFuelEntriesForVehicle(vehicleId: Long) = fuelDao.getFuelEntriesForVehicle(vehicleId)
    suspend fun insertFuelEntry(entry: FuelEntryEntity) = fuelDao.insertFuelEntry(entry)
    suspend fun deleteFuelEntry(entry: FuelEntryEntity) = fuelDao.deleteFuelEntry(entry)
    fun getAllShoppingLists() = shoppingDao.getAllLists()
    fun getItemsForList(listId: Long) = shoppingDao.getItemsForList(listId)
    suspend fun insertShoppingList(list: ShoppingListEntity) = shoppingDao.insertList(list)
    suspend fun insertShoppingItem(item: ShoppingItemEntity) = shoppingDao.insertItem(item)
    suspend fun updateShoppingItem(item: ShoppingItemEntity) = shoppingDao.updateItem(item)
    suspend fun deleteShoppingItem(item: ShoppingItemEntity) = shoppingDao.deleteItem(item)
    fun getAllAddresses() = addressDao.getAllAddresses()
    suspend fun insertAddress(address: AddressEntity) = addressDao.insertAddress(address)
    suspend fun deleteAddress(address: AddressEntity) = addressDao.deleteAddress(address)
    fun getAllReminders() = reminderDao.getAllReminders()
    fun getActiveReminders() = reminderDao.getActiveReminders()
    suspend fun insertReminder(reminder: ReminderEntity) = reminderDao.insertReminder(reminder)
    suspend fun updateReminder(reminder: ReminderEntity) = reminderDao.updateReminder(reminder)
    suspend fun deleteReminder(reminder: ReminderEntity) = reminderDao.deleteReminder(reminder)
    fun getAllNotes() = noteDao.getAllNotes()
    fun searchNotes(query: String) = noteDao.searchNotes(query)
    suspend fun insertNote(note: NoteEntity) = noteDao.insertNote(note)
    suspend fun updateNote(note: NoteEntity) = noteDao.updateNote(note)
    suspend fun deleteNote(note: NoteEntity) = noteDao.deleteNote(note)
    fun getAllDocuments() = documentDao.getAllDocuments()
    suspend fun insertDocument(document: DocumentEntity) = documentDao.insertDocument(document)
    suspend fun deleteDocument(document: DocumentEntity) = documentDao.deleteDocument(document)
    fun getAllSavings() = savingsDao.getAllSavings()
    fun getTotalSavings() = savingsDao.getTotalSavings()
    suspend fun insertSavings(savings: SavingsEntity) = savingsDao.insertSavings(savings)
    suspend fun deleteSavings(savings: SavingsEntity) = savingsDao.deleteSavings(savings)
}
