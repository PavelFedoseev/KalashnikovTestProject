package com.example.kalashnikovconcerntest;

import static org.junit.Assert.assertTrue;

import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.data.dto.Book;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;
import com.example.kalashnikovconcerntest.domain.usecase.get_author.GetAllAuthorsUseCase;
import com.example.kalashnikovconcerntest.domain.usecase.get_author.GetAuthorByIdUseCase;
import com.example.kalashnikovconcerntest.domain.usecase.get_book.GetAllBooksUseCase;
import com.example.kalashnikovconcerntest.domain.usecase.get_book.GetBookByIdUseCase;
import com.example.kalashnikovconcerntest.domain.usecase.set_author.UpdateAuthorUseCase;
import com.example.kalashnikovconcerntest.domain.usecase.set_book.UpdateBookUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@RunWith(MockitoJUnitRunner.class)
public class UseCaseTest {

    private final Author authorTestEnt = new Author(1, "test", 1);
    private final Book bookTestEnt = new Book(1, "test", "test", 1);
    @Mock
    private LibraryRepository repository;

    @Before
    public void setBefore(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateAuthorUseCase(){
        final boolean[] isUpdateChecked = {false};

        Mockito.when(repository.updateAuthor(Mockito.any())).then(invocation -> {
            isUpdateChecked[0] = true;
            return Completable.complete();
        });
        new UpdateAuthorUseCase(repository).updateAuthor(authorTestEnt);
        assertTrue(isUpdateChecked[0]);
    }

    @Test
    public void testUpdateBookUseCase(){
        final boolean[] isUpdateChecked = {false};

        Mockito.when(repository.updateBook(Mockito.any())).then(invocation -> {
            isUpdateChecked[0] = true;
            return Completable.complete();
        });
        new UpdateBookUseCase(repository).updateBook(bookTestEnt);
        assertTrue(isUpdateChecked[0]);
    }

    @Test
    public void testGetAuthorByIdUseCase(){
        final boolean[] isGetChecked = {false};

        Mockito.when(repository.getAuthorById(Mockito.anyInt())).then(invocation -> {
            isGetChecked[0] = true;
            return Flowable.empty();
        });
        new GetAuthorByIdUseCase(repository).getAuthor(1);
        assertTrue(isGetChecked[0]);
    }

    @Test
    public void testGetBookByIdUseCase(){
        final boolean[] isGetChecked = {false};

        Mockito.when(repository.getBookById(Mockito.anyInt())).then(invocation -> {
            isGetChecked[0] = true;
            return Flowable.empty();
        });
        new GetBookByIdUseCase(repository).getBook(1);
        assertTrue(isGetChecked[0]);
    }

    @Test
    public void testGetAllAuthorsUseCase(){
        final boolean[] isGetChecked = {false};

        Mockito.when(repository.getAllAuthors()).then(invocation -> {
            isGetChecked[0] = true;
            return Flowable.empty();
        });
        new GetAllAuthorsUseCase(repository).getAuthors();
        assertTrue(isGetChecked[0]);
    }

    @Test
    public void testGetAllBooksUseCase(){
        final boolean[] isGetChecked = {false};

        Mockito.when(repository.getAllBooks()).then(invocation -> {
            isGetChecked[0] = true;
            return Flowable.empty();
        });
        new GetAllBooksUseCase(repository).getBooks();
        assertTrue(isGetChecked[0]);
    }
}
