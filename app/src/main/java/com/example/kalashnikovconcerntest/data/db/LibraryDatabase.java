package com.example.kalashnikovconcerntest.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.data.dto.Book;

@Database(entities = {Author.class, Book.class}, version = 1, exportSchema = false)
public abstract class LibraryDatabase extends RoomDatabase {
    public abstract LibraryDao libraryDao();

    private static LibraryDatabase INSTANCE = null;

    public static LibraryDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static LibraryDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, LibraryDatabase.class, "LibraryDatabase.db")
                .fallbackToDestructiveMigrationOnDowngrade()
                .createFromAsset("database/sqlite.db")
                .build();

    }
}
