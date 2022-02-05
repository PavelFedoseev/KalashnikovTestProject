package com.example.kalashnikovconcerntest.ui.fragment.info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.data.dto.Book;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;
import com.example.kalashnikovconcerntest.domain.usecase.get_author.GetAuthorByIdUseCase;
import com.example.kalashnikovconcerntest.domain.usecase.get_book.GetBookByIdUseCase;
import com.example.kalashnikovconcerntest.domain.usecase.set_author.UpdateAuthorUseCase;
import com.example.kalashnikovconcerntest.domain.usecase.set_book.UpdateBookUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class InfoViewModel extends ViewModel {

    LibraryRepository libraryRepository;

    @Inject
    public InfoViewModel(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Book> _currentBook = new MutableLiveData<>();
    private final LiveData<Book> currentBook = _currentBook;

    private final MutableLiveData<Author> _currentAuthor = new MutableLiveData<>();
    private final LiveData<Author> currentAuthor = _currentAuthor;

    private final MutableLiveData<Boolean> _isChangeMode = new MutableLiveData<>(false);
    private final LiveData<Boolean> isChangeMode = _isChangeMode;

    public void onButtonChangeClick() {
        if (_isChangeMode.getValue() == true)
            _isChangeMode.postValue(false);
        else
            _isChangeMode.postValue(true);
    }

    public void onFragmentViewCreated(int argumentBookId) {
        initInfo(argumentBookId);
    }

    public void onTextDescriptionChanged(String description) {
        Book currentBook = _currentBook.getValue();
        if (currentBook != null) {
            currentBook.setDescription(description);
            changeBookInfo(currentBook);
        }
    }

    public void onTextBookNameChanged(String name) {
        Book currentBook = _currentBook.getValue();
        if (currentBook != null) {
            currentBook.setName(name);
            changeBookInfo(currentBook);
        }
    }

    public void onTextAuthorNameChanged(String name) {
        Author currentAuthor = _currentAuthor.getValue();
        if (currentAuthor != null) {
            currentAuthor.setName(name);
            changeAuthorInfo(currentAuthor);
        }
    }

    private void changeBookInfo(Book book) {
        disposables.add(new UpdateBookUseCase(libraryRepository)
                .updateBook(book)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.d("Update book successful");
                }, throwable -> {
                    Timber.d("Error in changeBookInfo");
                    Timber.e(throwable);
                }));
    }

    private void changeAuthorInfo(Author author) {
        disposables.add(new UpdateAuthorUseCase(libraryRepository)
                .updateAuthor(author)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.d("Update author successful");
                }, throwable -> {
                    Timber.d("Error in changAuthorInfo");
                    Timber.e(throwable);
                }));
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    private void initInfo(int bookId) {
        disposables.add(new GetBookByIdUseCase(libraryRepository)
                .getBook(bookId)
                .flatMap(book -> {
                    _currentBook.postValue(book);
                    return new GetAuthorByIdUseCase(libraryRepository)
                            .getAuthor(book.getAuthorId());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_currentAuthor::postValue,
                        throwable -> {
                            Timber.d("Error in initInfo");
                            Timber.e(throwable);
                        })
        );
    }
}