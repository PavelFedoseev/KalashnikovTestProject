package com.example.kalashnikovconcerntest.ui.fragment.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalashnikovconcerntest.R;
import com.example.kalashnikovconcerntest.data.dto.Book;
import com.example.kalashnikovconcerntest.ui.fragment.library.component.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookLibraryFragment extends Fragment {

    public final static String TAG = "BookLibraryFragment";
    private BookLibraryViewModel mViewModel;

    private RecyclerView mainRecyclerView;
    private RecyclerViewAdapter adapter = null;

    public static BookLibraryFragment newInstance() {
        return new BookLibraryFragment();
    }

    private List<Book> listOfBooks = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BookLibraryViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_library_fragment, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.onFragmentViewCreated();
        initObservers();
    }

    private void initObservers() {
        mViewModel.listOfBooks.observe(this.getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                listOfBooks = books;
                adapter.setList(listOfBooks);
            }
        });
    }

    private void initViews(View view) {
        mainRecyclerView = view.findViewById(R.id.recycler_main);
        adapter = new RecyclerViewAdapter(listOfBooks, new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                if (requireActivity() instanceof BookLibraryFragmentAdapter) {
                    ((BookLibraryFragmentAdapter) requireActivity()).onItemBookSelected(book.getId());
                }
            }
        });
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mainRecyclerView.setAdapter(adapter);
    }

    public interface BookLibraryFragmentAdapter {
        void onItemBookSelected(int bookId);
    }
}

