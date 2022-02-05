package com.example.kalashnikovconcerntest.ui.fragment.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kalashnikovconcerntest.data.dto.Book;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;
import com.example.kalashnikovconcerntest.domain.usecase.get_book.GetAllBooksUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class BookLibraryViewModel extends ViewModel {

    LibraryRepository libraryRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<List<Book>> _listOfBooks = new MutableLiveData<>();
    private final LiveData<List<Book>> listOfBooks = _listOfBooks;

    @Inject
    public BookLibraryViewModel(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public void onActivityCreated() {
        disposables.add(new GetAllBooksUseCase(libraryRepository)
                .getBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_listOfBooks::postValue, throwable -> {
                    Timber.d("Error with getting books");
                }));
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}