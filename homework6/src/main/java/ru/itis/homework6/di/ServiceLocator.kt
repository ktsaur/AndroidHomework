package ru.itis.homework6.di

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import ru.itis.homework6.data.db.InceptionDatabase
import ru.itis.homework6.data.db.migrations.Migration_1_2
import ru.itis.homework6.data.db.migrations.Migration_2_3
import ru.itis.homework6.data.db.repository.UserRepository


object ServiceLocator { //реализация паттерна синглтон

    private const val DATABASE_NAME = "InceptionDB"

    private var dbInstance: InceptionDatabase? = null
    private var userRepository: UserRepository? = null

    private fun initDatabase(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, InceptionDatabase::class.java, DATABASE_NAME)
            .addMigrations(
                Migration_1_2(),
                Migration_2_3()
            )
            .build()
    }

    fun initDataLayerDependencies(ctx: Context) {
        if(dbInstance == null) {
            initDatabase(ctx)
            dbInstance?.let {
                userRepository = UserRepository(
                    userDao = it.userDao,
                    songDao = it.songDao,
                    ioDispatcher = Dispatchers.IO
                )
            }
        }
    }

    fun getUserRepository(): UserRepository =
        userRepository ?: throw IllegalStateException("User repository not initialized")
}