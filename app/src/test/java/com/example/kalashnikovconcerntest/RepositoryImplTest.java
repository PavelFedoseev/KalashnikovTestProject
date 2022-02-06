package com.example.kalashnikovconcerntest;

import static org.junit.Assert.assertTrue;

import com.example.kalashnikovconcerntest.data.db.LibraryDao;
import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.data.dto.Book;
import com.example.kalashnikovconcerntest.data.repository.LibraryRepositoryImpl;

import org.bouncycastle.math.raw.Mod;
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
public class RepositoryImplTest {

    LibraryRepositoryImpl libraryRepository;

    private final Author authorTestEnt = new Author(1, "test", 1);
    private final Book bookTestEnt = new Book(1, "test", "test", 1);

    @Mock
    LibraryDao libraryDao;

    @Before
    public void setBefore() {
        MockitoAnnotations.openMocks(this);
        libraryRepository = new LibraryRepositoryImpl(libraryDao);
    }

    @Test
    public void testGetAllBooks() {
        final boolean[] isCallChecked = {false};
        Mockito.when(libraryDao.getAllBooks()).then(invocation -> {
                    isCallChecked[0] = true;
                    return Flowable.empty();
                }
        );
        libraryRepository.getAllBooks();
        assertTrue(isCallChecked[0]);
    }

    @Test
    public void testGetAllAuthors() {
        final boolean[] isCallChecked = {false};
        Mockito.when(libraryDao.getAllAuthors()).then(invocation -> {
                    isCallChecked[0] = true;
                    return Flowable.empty();
                }
        );
        libraryRepository.getAllAuthors();
        assertTrue(isCallChecked[0]);
    }

    @Test
    public void testGetBookById() {
        final boolean[] isCallChecked = {false};
        Mockito.when(libraryDao.getBookById(Mockito.anyInt())).then(invocation -> {
                    isCallChecked[0] = true;
                    return Flowable.empty();
                }
        );
        libraryRepository.getBookById(1);
        assertTrue(isCallChecked[0]);
    }

    @Test
    public void testGetAuthorById() {
        final boolean[] isCallChecked = {false};
        Mockito.when(libraryDao.getAuthorById(Mockito.anyInt())).then(invocation -> {
                    isCallChecked[0] = true;
                    return Flowable.empty();
                }
        );
        libraryRepository.getAuthorById(1);
        assertTrue(isCallChecked[0]);
    }

    @Test
    public void testInsetBook() {
        final boolean[] isCallChecked = {false};
        Mockito.when(libraryDao.insertBook(Mockito.any())).then(invocation -> {
                    isCallChecked[0] = true;
                    return Completable.complete();
                }
        );
        libraryRepository.insertBook(bookTestEnt);
        assertTrue(isCallChecked[0]);
    }


    @Test
    public void testInsetAuthor() {
        final boolean[] isCallChecked = {false};
        Mockito.when(libraryDao.insertAuthor(Mockito.any())).then(invocation -> {
                    isCallChecked[0] = true;
                    return Completable.complete();
                }
        );
        libraryRepository.insertAuthor(authorTestEnt);
        assertTrue(isCallChecked[0]);
    }

    @Test
    public void testUpdateBook() {
        final boolean[] isCallChecked = {false};
        Mockito.when(libraryDao.updateBook(Mockito.any())).then(invocation -> {
                    isCallChecked[0] = true;
                    return Completable.complete();
                }
        );
        libraryRepository.updateBook(bookTestEnt);
        assertTrue(isCallChecked[0]);
    }


    @Test
    public void testUpdateAuthor() {
        final boolean[] isCallChecked = {false};
        Mockito.when(libraryDao.updateAuthor(Mockito.any())).then(invocation -> {
                    isCallChecked[0] = true;
                    return Completable.complete();
                }
        );
        libraryRepository.updateAuthor(authorTestEnt);
        assertTrue(isCallChecked[0]);
    }

    @Test
    public void testDeleteBook() {
        final boolean[] isCallChecked = {false};
        Mockito.when(libraryDao.deleteBook(bookTestEnt)).then(invocation -> {
                    isCallChecked[0] = true;
                    return Completable.complete();
                }
        );
        libraryRepository.deleteBook(bookTestEnt);
        assertTrue(isCallChecked[0]);
    }


    @Test
    public void testDeleteAuthor() {
        final boolean[] isCallChecked = {false};
        Mockito.when(libraryDao.deleteAuthor(authorTestEnt)).then(invocation -> {
                    isCallChecked[0] = true;
                    return Completable.complete();
                }
        );
        libraryRepository.deleteAuthor(authorTestEnt);
        assertTrue(isCallChecked[0]);
    }


}
