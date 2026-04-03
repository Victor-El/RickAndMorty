package com.turingheights.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.turingheights.data.DB_NAME
import com.turingheights.data.db.dao.EntityDao
import com.turingheights.data.db.entities.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1,
)
abstract class RickAndMortyDatabase: RoomDatabase() {

    abstract fun entityDao(): EntityDao

    companion object {
        fun getInstance(
            context: Context,
        ): RickAndMortyDatabase {
            return Room.databaseBuilder(
                context,
                RickAndMortyDatabase::class.java,
                DB_NAME
            ).build()
        }
    }

}