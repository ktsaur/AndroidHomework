package ru.itis.homework6.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.homework6.data.db.entities.SongEntity

@Dao
interface SongDao {

    @Query("SELECT * FROM song WHERE user_id = :user_id ")
    suspend fun getAllUserSongs(user_id: String): List<SongEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSongInDb(song: SongEntity)

    @Delete
    suspend fun deleteSong(song: SongEntity)

}
