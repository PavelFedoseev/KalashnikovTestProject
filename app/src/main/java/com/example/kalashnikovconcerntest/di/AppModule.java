package com.example.kalashnikovconcerntest.di;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kalashnikovconcerntest.data.db.LibraryDatabase;
import com.example.kalashnikovconcerntest.data.repository.LibraryRepositoryImpl;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;
import com.example.kalashnikovconcerntest.util.Config;

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
}
