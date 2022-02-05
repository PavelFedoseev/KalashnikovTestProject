package com.example.kalashnikovconcerntest.domain.usecase.get_author;

import com.example.kalashnikovconcerntest.data.dto.Author;
import com.example.kalashnikovconcerntest.domain.repo.LibraryRepository;

import java.util.List;

import io.reactivex.Flowable;

public class GetAllAuthorsUseCase  {
    private final LibraryRepository repo;
    public GetAllAuthorsUseCase(LibraryRepository repository) {
        repo = repository;
    }

    public Flowable<List<Author>> getAuthors(){
        return repo.getAllAuthors();
    }
}
