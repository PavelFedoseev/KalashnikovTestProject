package com.example.kalashnikovconcerntest.domain.usecase.set_author;

import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;

import io.reactivex.Completable;

public class UpdateAuthorUseCase {
    private final LibraryRepository repo;

    public UpdateAuthorUseCase(LibraryRepository repository) {
        repo = repository;
    }

    public Completable updateAuthor(Author author) {
        return repo.updateAuthor(author);
    }
}
