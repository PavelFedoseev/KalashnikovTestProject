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

import java.util.Calendar;

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
    public final LiveData<Book> currentBook = _currentBook;

    private final MutableLiveData<Author> _currentAuthor = new MutableLiveData<>();
    public final LiveData<Author> currentAuthor = _currentAuthor;

    private final MutableLiveData<Boolean> _isEditMode = new MutableLiveData<>(false);
    public final LiveData<Boolean> isEditMode = _isEditMode;

    private final MutableLiveData<String> _toastMessage = new MutableLiveData<>(null);
    public final LiveData<String> toastMessage = _toastMessage;

    private int bookId;

    public void onButtonChangeClick() {
        if (_isEditMode.getValue() == true)
            _isEditMode.postValue(false);
        else
            _isEditMode.postValue(true);
    }

    public void onFragmentViewCreated(int argumentBookId) {
        bookId = argumentBookId;
        initInfo(argumentBookId);
    }

    public void onEditComplete(String bookName, String description, String authorName, String birthday) {
        Book currentBook = _currentBook.getValue();
        if (currentBook != null) {
            currentBook.setDescription(description);
            currentBook.setName(bookName);
            changeBookInfo(currentBook);
        }
        Author currentAuthor = _currentAuthor.getValue();
        if (currentAuthor != null) {
            currentAuthor.setName(authorName);
            String[] date = birthday.split("-");
            if(date.length == 3){
                Calendar calendar = Calendar.getInstance();
                try {
                    calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
                    currentAuthor.setBirthDate(calendar.getTimeInMillis());
                }
                catch (NumberFormatException e){
                    Timber.d("NumberFormatException in birthday string: %s", birthday);
                    _toastMessage.postValue("Неправильный формат даты: yyyy-MM-dd");
                }
            }
            else{
                _toastMessage.postValue("Неправильный формат даты: yyyy-MM-dd");
            }
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
        initInfo(bookId);
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
        initInfo(bookId);
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

    public void onToastShown() {
        _toastMessage.postValue(null);
    }
}