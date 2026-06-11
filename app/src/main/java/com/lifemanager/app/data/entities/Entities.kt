package com.lifemanager.app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val amount: Double,
    val category: String,
    val date: Long,
    val notes: String = ""
)

@Entity(tableName = "incomes")
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val amount: Double,
    val source: String,
    val date: Long,
    val notes: String = ""
)

@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val lenderBorrower: String,
    val principal: Double,
    val interestRate: Double,
    val interestType: String,
    val isCompound: Boolean,
    val startDate: Long,
    val dueDate: Long,
    val isLender: Boolean = false,
    val notes: String = ""
)

@Entity(tableName = "loan_payments")
data class LoanPaymentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val loanId: Long,
    val amount: Double,
    val date: Long,
    val notes: String = ""
)

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val registrationNumber: String,
    val fuelType: String,
    val notes: String = ""
)

@Entity(tableName = "fuel_entries")
data class FuelEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val vehicleId: Long,
    val date: Long,
    val odometer: Double,
    val quantity: Double,
    val cost: Double,
    val station: String = ""
)

@Entity(tableName = "shopping_lists")
data class ShoppingListEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val createdAt: Long,
    val isArchived: Boolean = false
)

@Entity(tableName = "shopping_items")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val listId: Long,
    val name: String,
    val quantity: String = "",
    val estimatedPrice: Double = 0.0,
    val category: String = "",
    val isCompleted: Boolean = false,
    val priority: Int = 0
)

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val address: String,
    val city: String,
    val state: String,
    val country: String,
    val postalCode: String,
    val moveInDate: Long,
    val moveOutDate: Long? = null,
    val notes: String = ""
)

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val dueDate: Long,
    val repeatInterval: String = "none",
    val category: String = "custom",
    val isActive: Boolean = true
)

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val folder: String = "default",
    val tags: String = "",
    val isFavorite: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val fields: String,
    val createdAt: Long,
    val updatedAt: Long
)

@Entity(tableName = "savings")
data class SavingsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: String,
    val amount: Double,
    val date: Long,
    val notes: String = ""
)
