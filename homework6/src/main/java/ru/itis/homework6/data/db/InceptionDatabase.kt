package ru.itis.homework6.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.itis.homework6.data.db.converters.DateConverters
import ru.itis.homework6.data.db.dao.SongDao
import ru.itis.homework6.data.db.dao.UserDao
import ru.itis.homework6.data.db.entities.SongEntity
import ru.itis.homework6.data.db.entities.UserEntity

@Database(
    entities = [UserEntity::class, SongEntity::class],
    version = 3
)
@TypeConverters(DateConverters::class)
abstract class InceptionDatabase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val songDao: SongDao

    companion object {
        const val DB_LOG_KEY = "InceptionDB"
    }
}