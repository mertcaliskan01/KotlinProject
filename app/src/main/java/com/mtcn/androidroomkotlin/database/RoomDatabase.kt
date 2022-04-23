package com.mtcn.androidroomkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mtcn.androidroomkotlin.dao.WordDao
import com.mtcn.androidroomkotlin.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = arrayOf(Word::class), version = 7, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    //var wordDao = database.wordDao()

                    // Delete all content here.
//                    wordDao.deleteAll()

                    // Add sample words.
//                    var word = Word("Hello")
//                    wordDao.insert(word)
//                    word = Word("World!")
//                    wordDao.insert(word)
//
//                    word = Word("TODO!")
//                    wordDao.insert(word)
                }
            }
        }
    }



    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        val MIGRATION_1_2: Migration = object : Migration(2, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE word_table "
                            + "ADD COLUMN content TEXT"
                )
            }
        }
    }
}