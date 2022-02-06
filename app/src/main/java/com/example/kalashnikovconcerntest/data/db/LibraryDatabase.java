package com.example.kalashnikovconcerntest.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.data.dto.Book;
import com.example.kalashnikovconcerntest.util.Config;

import java.util.concurrent.Executors;

@Database(entities = {Author.class, Book.class}, version = 1, exportSchema = false)
//@TypeConverters(CalendarTypeConverter.class)
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
