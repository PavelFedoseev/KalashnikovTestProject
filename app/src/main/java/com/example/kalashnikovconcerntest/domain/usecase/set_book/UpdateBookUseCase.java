package com.example.kalashnikovconcerntest.domain.usecase.set_book;

import com.example.kalashnikovconcerntest.data.dto.Book;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;

import io.reactivex.Completable;

public class UpdateBookUseCase {
    private final LibraryRepository repo;

    public UpdateBookUseCase(LibraryRepository repository) {
        repo = repository;
    }

    public Completable updateBook(Book book) {
        return repo.updateBook(book);
    }
}
