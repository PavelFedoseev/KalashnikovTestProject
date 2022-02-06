package com.example.kalashnikovconcerntest.ui.fragment.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.data.dto.Book;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;
import com.example.kalashnikovconcerntest.domain.usecase.get_book.GetAllBooksUseCase;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class BookLibraryViewModel extends ViewModel {

    private final LibraryRepository libraryRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<List<Book>> _listOfBooks = new MutableLiveData<>();
    public final LiveData<List<Book>> listOfBooks = _listOfBooks;

    @Inject
    public BookLibraryViewModel(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;

//        disposables.add(libraryRepository.insertAuthor(new Author(1,"Грибоедов", Calendar.getInstance())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(()->{
//            libraryRepository.insertBook(new Book(1, "Книга", "Очень большое описание", 1)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
//        }));

    }

    public void onFragmentViewCreated() {
        disposables.add(new GetAllBooksUseCase(libraryRepository)
                .getBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    _listOfBooks.postValue(list);
                }, throwable -> {
                    Timber.d("Error with getting books");
                }));
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}