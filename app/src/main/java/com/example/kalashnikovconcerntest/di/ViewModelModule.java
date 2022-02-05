package com.example.kalashnikovconcerntest.di;

import androidx.room.Insert;

import com.example.kalashnikovconcerntest.data.db.LibraryDatabase;
import com.example.kalashnikovconcerntest.data.repository.LibraryRepositoryImpl;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class ViewModelModule {
    @Provides
    public LibraryRepository provideLibraryRepo(LibraryDatabase database){
        return new LibraryRepositoryImpl(database);
    }
}
