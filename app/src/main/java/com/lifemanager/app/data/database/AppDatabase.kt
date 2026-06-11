package com.lifemanager.app.data.database

import android.content.Context
import androidx.room.*
import com.lifemanager.app.data.dao.*
import com.lifemanager.app.data.entities.*

@Database(
    entities = [
        ExpenseEntity::class,
        IncomeEntity::class,
        LoanEntity::class,
        LoanPaymentEntity::class,
        VehicleEntity::class,
        FuelEntryEntity::class,
        ShoppingListEntity::class,
        ShoppingItemEntity::class,
        AddressEntity::class,
        ReminderEntity::class,
        NoteEntity::class,
        DocumentEntity::class,
        SavingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun incomeDao(): IncomeDao
    abstract fun loanDao(): LoanDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun fuelDao(): FuelDao
    abstract fun shoppingDao(): ShoppingDao
    abstract fun addressDao(): AddressDao
    abstract fun reminderDao(): ReminderDao
    abstract fun noteDao(): NoteDao
    abstract fun documentDao(): DocumentDao
    abstract fun savingsDao(): SavingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "life_manager_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
