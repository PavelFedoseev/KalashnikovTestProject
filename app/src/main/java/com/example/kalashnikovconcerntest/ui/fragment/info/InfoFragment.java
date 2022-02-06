package com.example.kalashnikovconcerntest.ui.fragment.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kalashnikovconcerntest.R;
import com.example.kalashnikovconcerntest.data.dto.Book;

import java.text.SimpleDateFormat;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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

    private boolean isEditMode;

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
        btnEdit.setOnClickListener(view -> {
                    if (isEditMode) {
                        mViewModel.onEditComplete(
                                tvBookName.getText().toString(),
                                tvDescription.getText().toString(),
                                tvNameOfAuthor.getText().toString(),
                                tvBirthDate.getText().toString()
                        );
                    }
                    mViewModel.onButtonChangeClick();
                }
        );
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
            String dateOfBirth = new SimpleDateFormat(pattern).format(new Date(author.getBirthDate()));
            tvBirthDate.setText(dateOfBirth);
            tvNameOfAuthor.setText(author.getName());
        });
        mViewModel.isEditMode.observe(this.getViewLifecycleOwner(), isEditMode -> {
            this.isEditMode = isEditMode;
            if (isEditMode) {
                btnEdit.setText(getResources().getText(R.string.info_button_on));
            } else {
                btnEdit.setText(getResources().getText(R.string.info_button_off));
            }

            tvDescription.setFocusable(isEditMode);
            tvNameOfAuthor.setFocusable(isEditMode);
            tvBookName.setFocusable(isEditMode);
            tvBirthDate.setFocusable(isEditMode);

            tvDescription.setClickable(isEditMode);
            tvNameOfAuthor.setClickable(isEditMode);
            tvBookName.setClickable(isEditMode);
            tvBirthDate.setClickable(isEditMode);

            tvDescription.setEnabled(isEditMode);
            tvNameOfAuthor.setEnabled(isEditMode);
            tvBookName.setEnabled(isEditMode);
            tvBirthDate.setEnabled(isEditMode);

            tvDescription.setFocusableInTouchMode(isEditMode);
            tvNameOfAuthor.setFocusableInTouchMode(isEditMode);
            tvBookName.setFocusableInTouchMode(isEditMode);
            tvBirthDate.setFocusableInTouchMode(isEditMode);


        });
        mViewModel.toastMessage.observe(this.getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                mViewModel.onToastShown();
            }
        });
    }
}