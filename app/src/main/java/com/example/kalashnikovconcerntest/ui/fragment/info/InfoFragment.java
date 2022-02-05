package com.example.kalashnikovconcerntest.ui.fragment.info;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kalashnikovconcerntest.R;

public class InfoFragment extends Fragment {

    private InfoViewModel mViewModel;

    private final static String ARGUMENT_BOOK_ID_KEY = "BookIdKey";
    public static InfoFragment newInstance(int bookId) {
        InfoFragment fragment = new InfoFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_BOOK_ID_KEY, bookId);
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InfoViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        initViews();
        return inflater.inflate(R.layout.info_fragment, container, false);
    }

    private void initViews() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers();
    }

    private void initObservers(){

    }
}