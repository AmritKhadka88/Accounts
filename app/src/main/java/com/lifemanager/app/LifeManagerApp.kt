package com.lifemanager.app

import android.app.Application
import com.lifemanager.app.data.database.AppDatabase
import com.lifemanager.app.data.repository.AppRepository

class LifeManagerApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy {
        AppRepository(
            database.expenseDao(),
            database.incomeDao(),
            database.loanDao(),
            database.vehicleDao(),
            database.fuelDao(),
            database.shoppingDao(),
            database.addressDao(),
            database.reminderDao(),
            database.noteDao(),
            database.documentDao(),
            database.savingsDao()
        )
    }
}
