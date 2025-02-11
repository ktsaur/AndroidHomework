package ru.itis.homework6.data.db.migrations

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.itis.homework6.data.db.InceptionDatabase

class Migration_1_2: Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS `new_song` " +
                    "(`song_id` TEXT NOT NULL, `user_id` TEXT NOT NULL, `title` TEXT NOT NULL, `singer` TEXT NOT NULL," +
                    " `author` TEXT NOT NULL, `genre` TEXT NOT NULL, PRIMARY KEY(`song_id`), " +
                    "FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE )")

            db.execSQL("INSERT INTO new_song (song_id, user_id, title, singer, author, genre)" +
                    " SELECT song_id, user_id, title, singer, author, genre FROM song")

            db.execSQL("DROP TABLE song")

            db.execSQL("ALTER TABLE new_song RENAME TO song")

        }catch (e: Exception){
            Log.e(InceptionDatabase.DB_LOG_KEY, "Error while 1_2 migration: ${e.message}")
        }
    }
}