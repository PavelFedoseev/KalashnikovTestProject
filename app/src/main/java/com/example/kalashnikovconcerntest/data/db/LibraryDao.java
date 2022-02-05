package com.example.kalashnikovconcerntest.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.data.dto.Book;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface LibraryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAuthor(Author author);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertBook(Book book);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllAuthors(List<Author> authors);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllBooks(List<Book> books);

    @Update()
    Completable updateAuthor(Author author);

    @Update()
    Completable updateBook(Book book);

    @Delete()
    Completable deleteAuthor(Author author);

    @Delete()
    Completable deleteBook(Book book);

    @Query("SELECT * FROM Book")
    Flowable<List<Book>> getAllBooks();

    @Query("SELECT * FROM Author")
    Flowable<List<Author>> getAllAuthors();

    @Query("SELECT * FROM Book WHERE id=:id")
    Flowable<Book> getBookById(int id);

    @Query("SELECT * FROM Author WHERE id=:id")
    Flowable<Author> getAuthorById(int id);

}
