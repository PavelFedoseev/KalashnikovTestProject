package com.example.kalashnikovconcerntest.di;

import android.content.Context;

import com.example.kalashnikovconcerntest.data.db.LibraryDao;
import com.example.kalashnikovconcerntest.data.db.LibraryDatabase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    public LibraryDatabase provideLibraryDatabase(@ApplicationContext Context context){
        return LibraryDatabase.getInstance(context);
    }
    @Provides
    public LibraryDao provideLibraryDao(LibraryDatabase database){
        return database.libraryDao();
    }
}
