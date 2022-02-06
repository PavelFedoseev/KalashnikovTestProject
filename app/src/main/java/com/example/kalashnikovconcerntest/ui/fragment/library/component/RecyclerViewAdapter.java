package com.example.kalashnikovconcerntest.ui.fragment.library.component;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalashnikovconcerntest.R;
import com.example.kalashnikovconcerntest.data.dto.Book;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Book> bookList;
    private final OnItemClickListener listener;

    public RecyclerViewAdapter(List<Book> bookList, OnItemClickListener listener) {
        this.bookList = bookList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BookViewHolder) holder).bindView(position);
    }

    public void setList(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textview_book_name);
        }

        void bindView(int position) {
            Book bookItem = bookList.get(position);
            name.setText(bookItem.getName());
            itemView.setOnClickListener(view -> listener.onItemClick(bookItem));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
}
