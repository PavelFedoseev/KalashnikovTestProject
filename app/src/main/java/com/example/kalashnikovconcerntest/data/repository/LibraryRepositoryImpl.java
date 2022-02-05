package com.example.kalashnikovconcerntest.data.repository;

import com.example.kalashnikovconcerntest.data.db.LibraryDao;
import com.example.kalashnikovconcerntest.data.db.LibraryDatabase;
import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.data.dto.Book;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LibraryRepositoryImpl implements LibraryRepository {

    private final LibraryDatabase database;
    private final LibraryDao libraryDao;

    public LibraryRepositoryImpl(LibraryDatabase database) {
        this.database = database;
        this.libraryDao = this.database.libraryDao();
    }

    @Override
    public Flowable<List<Book>> getAllBooks() {
        return libraryDao.getAllBooks();
    }

    @Override
    public Flowable<List<Author>> getAllAuthors() {
        return libraryDao.getAllAuthors();
    }

    @Override
    public Flowable<Book> getBookById(int id) {
        return libraryDao.getBookById(id);
    }

    @Override
    public Flowable<Author> getAuthorById(int id) {
        return libraryDao.getAuthorById(id);
    }

    @Override
    public Completable insertBook(Book book) {
        return libraryDao.insertBook(book);
    }

    @Override
    public Completable insertAuthor(Author author) {
        return libraryDao.insertAuthor(author);
    }

    @Override
    public Completable updateBook(Book book) {
        return libraryDao.updateBook(book);
    }

    @Override
    public Completable updateAuthor(Author author) {
        return libraryDao.updateAuthor(author);
    }

    @Override
    public Completable deleteBook(Book book) {
        return libraryDao.deleteBook(book);
    }

    @Override
    public Completable deleteAuthor(Author author) {
        return libraryDao.deleteAuthor(author);
    }
}
