package com.example.kalashnikovconcerntest;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.kalashnikovconcerntest.data.db.LibraryDao;
import com.example.kalashnikovconcerntest.data.db.LibraryDatabase;
import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.data.dto.Book;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    private LibraryDatabase database;
    private LibraryDao libraryDao;

    private final Author authorTestEnt = new Author(1, "test", 1);
    private final Author authorTestEnt2 = new Author(2, "test", 2);
    private final Book bookTestEnt = new Book(1, "test", "test", 1);
    private final Book bookTestEnt2 = new Book(2, "test", "test", 2);

    @Before
    public void setBefore() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), LibraryDatabase.class)
                .allowMainThreadQueries()
                .build();
        libraryDao = database.libraryDao();
    }

    @Test
    public void testInsertAuthor() {
        libraryDao.insertAuthor(authorTestEnt).test().assertComplete().dispose();
    }

    @Test
    public void testInsertBookWithoutAuthor() {
        libraryDao.insertBook(bookTestEnt).test().assertError(SQLiteConstraintException.class).dispose();
    }

    @Test
    public void testInsertBookCascade() {
        libraryDao.insertAuthor(authorTestEnt).test().assertComplete().dispose();
        libraryDao.insertBook(bookTestEnt).test().assertComplete().dispose();
    }


    @Test
    public void testDeleteAuthor() {
        libraryDao.insertAuthor(authorTestEnt).test().assertComplete().dispose();
        libraryDao.deleteAuthor(authorTestEnt).test().assertComplete().dispose();
    }

    @Test
    public void testDeleteAuthorCascade() {
        libraryDao.insertAuthor(authorTestEnt).test().assertComplete().dispose();
        libraryDao.insertBook(bookTestEnt).test().assertComplete().dispose();
        libraryDao.deleteAuthor(authorTestEnt).test().assertComplete().dispose();
        libraryDao.getAllBooks().test().assertValue(List::isEmpty);
    }

    @Test
    public void testDeleteBook() {
        libraryDao.insertAuthor(authorTestEnt).test().assertComplete().dispose();
        libraryDao.insertBook(bookTestEnt).test().assertComplete().dispose();
        libraryDao.deleteBook(bookTestEnt).test().assertComplete().dispose();
    }

    @Test
    public void testInsertAllAndGetAllAuthors() {
        libraryDao.insertAllAuthors(Arrays.asList(authorTestEnt, authorTestEnt2)).test().assertNoErrors().dispose();
        libraryDao.getAllAuthors().test().assertValue(list -> list.size() == 2);
    }

    @Test
    public void testInsertAllAndGetAllBooks() {
        libraryDao.insertAllAuthors(Arrays.asList(authorTestEnt, authorTestEnt2)).test().assertNoErrors().dispose();
        libraryDao.insertAllBooks(Arrays.asList(bookTestEnt, bookTestEnt2)).test().assertNoErrors().dispose();
        libraryDao.getAllBooks().test().assertValue(list -> list.size() == 2);
    }

    @Test
    public void testUpdateAuthorReturnById() {
        Author changedAuthor = authorTestEnt;
        changedAuthor.setName("Changed");
        libraryDao.insertAuthor(authorTestEnt).test().assertNoErrors().dispose();
        libraryDao.updateAuthor(changedAuthor).test().assertComplete().dispose();
        libraryDao.getAuthorById(authorTestEnt.getId()).test().assertValue(value -> value.getName().equals(changedAuthor.getName()));
    }

    @Test
    public void testUpdateBookReturnById() {
        Book changedBook = bookTestEnt;
        changedBook.setName("Changed");
        libraryDao.insertAuthor(authorTestEnt).test().assertNoErrors().dispose();
        libraryDao.insertBook(bookTestEnt).test().assertNoErrors().dispose();
        libraryDao.updateBook(changedBook).test().assertComplete().dispose();
        libraryDao.getBookById(changedBook.getId()).test().assertValue(value -> value.getName().equals(changedBook.getName()));
    }
}
