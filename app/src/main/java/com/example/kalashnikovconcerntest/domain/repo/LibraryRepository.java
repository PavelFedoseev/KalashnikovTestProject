package com.example.kalashnikovconcerntest.domain.repo;

import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.data.dto.Book;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface LibraryRepository {
    Flowable<List<Book>> getAllBooks();
    Flowable<List<Author>> getAllAuthors();

    Flowable<Book> getBookById(int id);
    Flowable<Author> getAuthorById(int id);

    Completable insertBook(Book book);
    Completable insertAuthor(Author author);

    Completable updateBook(Book book);
    Completable updateAuthor(Author author);

    Completable deleteBook(Book book);
    Completable deleteAuthor(Author author);
}
