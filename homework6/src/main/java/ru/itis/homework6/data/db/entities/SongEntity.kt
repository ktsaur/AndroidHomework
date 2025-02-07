package ru.itis.homework6.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "song",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class SongEntity(
    @PrimaryKey
    @ColumnInfo(name = "song_id")
    val songId: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "singer")
    val singer: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "genre")
    val genre: String
)