package ru.itis.homework6.data.db.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.itis.homework6.data.db.dao.SongDao
import ru.itis.homework6.data.db.dao.UserDao
import ru.itis.homework6.data.db.entities.SongEntity
import ru.itis.homework6.data.db.entities.UserEntity
import kotlin.math.sin

class UserRepository(
    private val userDao: UserDao,
    private val songDao: SongDao,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun getUserByUsernameAndPassword(username: String, password: String): UserEntity? {
        return withContext(ioDispatcher) {
            userDao.getUserByUsernameAndPassword(username = username, password = password)
//                ?: throw IllegalStateException("User with given not found")
        }
    }

    suspend fun saveUser(user: UserEntity) {
        return withContext(ioDispatcher) {
            userDao.saveUser(user = user)
        }
    }

    suspend fun getAllUserSongs(user_id: String): List<SongEntity> {
        return withContext(ioDispatcher) {
            songDao.getAllUserSongs(user_id = user_id)
        }
    }

    suspend fun saveSong(song: SongEntity) {
        return withContext(ioDispatcher) {
            songDao.saveSongInDb(song = song)
        }
    }

    suspend fun deleteSong(song: SongEntity) {
        return withContext(ioDispatcher) {
            songDao.deleteSong(song = song)
        }
    }
}