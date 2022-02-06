package com.example.kalashnikovconcerntest;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.kalashnikovconcerntest.data.db.LibraryDao;
import com.example.kalashnikovconcerntest.data.db.LibraryDatabase;
import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.data.dto.Book;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
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

    /*
      Тест базы данных должен проходить, но я работаю на M1 чипе, а Robolectric собирает SQLite артифакты на x86_64, поэтому выскакивает
      com.almworks.sqlite4java.SQLiteException: [-91] cannot load library: java.lang.UnsatisfiedLinkError:
      /private/var/folders/49/p6w0_0qs6b9d30wmdxxrd8xc0000gn/T/1644157210396-0/libsqlite4java.dylib: dlopen(/private/var/folders/49/p6w0_0qs6b9d30wmdxxrd8xc0000gn/T/1644157210396-0/libsqlite4java.dylib, 0x0001):
      tried: '/private/var/folders/49/p6w0_0qs6b9d30wmdxxrd8xc0000gn/T/1644157210396-0/libsqlite4java.dylib' (fat file, but missing compatible architecture (have 'i386,x86_64', need 'arm64e')),
      '/usr/lib/libsqlite4java.dylib' (no such file)
     */

    /** Сделал этот тест инструментным */

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
