package com.example.kalashnikovconcerntest.domain.usecase.get_book;

import com.example.kalashnikovconcerntest.data.dto.Book;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;

import java.util.List;

import io.reactivex.Flowable;

public class GetAllBooksUseCase {
    private final LibraryRepository repo;

    public GetAllBooksUseCase(LibraryRepository repository) {
        repo = repository;
    }

    public Flowable<List<Book>> getBooks() {
        return repo.getAllBooks();
    }
}
