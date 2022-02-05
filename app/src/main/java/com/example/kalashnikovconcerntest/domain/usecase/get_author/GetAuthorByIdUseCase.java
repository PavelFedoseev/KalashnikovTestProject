package com.example.kalashnikovconcerntest.domain.usecase.get_author;

import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;

import java.util.List;

import io.reactivex.Flowable;

public class GetAuthorByIdUseCase {
    private final LibraryRepository repo;
    public GetAuthorByIdUseCase(LibraryRepository repository) {
        repo = repository;
    }

    public Flowable<Author> getAuthor(int id){
        return repo.getAuthorById(id);
    }
}
