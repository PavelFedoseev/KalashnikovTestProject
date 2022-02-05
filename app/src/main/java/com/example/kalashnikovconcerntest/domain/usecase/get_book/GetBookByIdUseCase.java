package com.example.kalashnikovconcerntest.domain.usecase.get_book;

import com.example.kalashnikovconcerntest.data.dto.Book;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;

import io.reactivex.Flowable;

public class GetBookByIdUseCase {
    private final LibraryRepository repo;

    public GetBookByIdUseCase(LibraryRepository repository) {
        repo = repository;
    }

    public Flowable<Book> getBook(int id) {
        return repo.getBookById(id);
    }
}
