package ru.itis.homework6.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.homework6.data.db.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE user_id = :id")
    suspend fun getUserByUserId(id: String): UserEntity?

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("UPDATE users SET username = :username, email = :email, phone = :phone, password = :password WHERE user_id = :userId ")
    suspend fun updateUserInfo(userId: String, username: String, email: String, phone: String, password: String)

}