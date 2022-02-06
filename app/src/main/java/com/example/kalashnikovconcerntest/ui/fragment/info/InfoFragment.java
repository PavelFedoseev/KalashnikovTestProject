package com.example.kalashnikovconcerntest.ui.fragment.info;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kalashnikovconcerntest.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InfoFragment extends Fragment {

    public final static String TAG = "InfoFragment";
    private EditText tvNameOfAuthor;
    private EditText tvBirthDate;
    private EditText tvDescription;
    private EditText tvBookName;

    private Button btnEdit;

    private InfoViewModel mViewModel;

    private int argumentBookId;

    public final static String ARGUMENT_BOOK_ID_KEY = "BookIdKey";

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
        if (getArguments() != null)
            argumentBookId = getArguments().getInt(ARGUMENT_BOOK_ID_KEY, -1);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);
        initViews(view);
        initListeners();
        if (getArguments() != null) {
            int bookId = getArguments().getInt(ARGUMENT_BOOK_ID_KEY, -1);
            mViewModel.onFragmentViewCreated(bookId);
        }
        return view;
    }

    private void initViews(View view) {
        tvNameOfAuthor = view.findViewById(R.id.textview_author_name);
        tvDescription = view.findViewById(R.id.textview_description);
        tvBirthDate = view.findViewById(R.id.textview_author_birth);
        tvBookName = view.findViewById(R.id.textview_info_book_name);
        btnEdit = view.findViewById(R.id.button_edit);
    }

    private void initListeners() {
        btnEdit.setOnClickListener(view -> mViewModel.onButtonChangeClick());
        tvBookName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tvDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tvNameOfAuthor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tvBirthDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.onFragmentViewCreated(argumentBookId);
        initObservers();
    }

    private void initObservers() {
        mViewModel.currentBook.observe(this.getViewLifecycleOwner(), book -> {
            tvDescription.setText(book.getDescription());
            tvBookName.setText(book.getName());
        });
        mViewModel.currentAuthor.observe(this.getViewLifecycleOwner(), author -> {
            String pattern = "yyyy-MM-dd";
            String dateOfBirth = new SimpleDateFormat(pattern).format(author.getBirthDate().getTime());
            tvBirthDate.setText(dateOfBirth);
            tvNameOfAuthor.setText(author.getName());
        });
        mViewModel.isChangeMode.observe(this.getViewLifecycleOwner(), isChangeMode -> {
            tvDescription.setFocusable(isChangeMode);
            tvNameOfAuthor.setFocusable(isChangeMode);
            tvBookName.setFocusable(isChangeMode);
            tvBirthDate.setFocusable(isChangeMode);

            tvDescription.setClickable(isChangeMode);
            tvNameOfAuthor.setClickable(isChangeMode);
            tvBookName.setClickable(isChangeMode);
            tvBirthDate.setClickable(isChangeMode);

            tvDescription.setFocusableInTouchMode(isChangeMode);
            tvNameOfAuthor.setFocusableInTouchMode(isChangeMode);
            tvBookName.setFocusableInTouchMode(isChangeMode);
            tvBirthDate.setFocusableInTouchMode(isChangeMode);

            if (isChangeMode) {
                btnEdit.setText(getResources().getText(R.string.info_button_on));
            } else {
                btnEdit.setText(getResources().getText(R.string.info_button_off));
            }
        });
    }
}