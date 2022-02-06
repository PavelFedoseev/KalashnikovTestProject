package com.example.kalashnikovconcerntest.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.kalashnikovconcerntest.R;
import com.example.kalashnikovconcerntest.ui.fragment.info.InfoFragment;
import com.example.kalashnikovconcerntest.ui.fragment.library.BookLibraryFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements BookLibraryFragment.BookLibraryFragmentAdapter {

    private final static String TAG = "MainActivity";

    NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.fragment_container);
    }

    private void openInfoFragment(int bookId) {
        Bundle bundle = new Bundle();
        bundle.putInt(InfoFragment.ARGUMENT_BOOK_ID_KEY, bookId);
        navController.navigate(R.id.infoFragment, bundle);
    }

    @Override
    public void onItemBookSelected(int bookId) {
        openInfoFragment(bookId);
    }
}


