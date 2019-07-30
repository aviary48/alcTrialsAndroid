package com.example.newbooks.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newbooks.Models.Book;
import com.example.newbooks.R;

import java.util.ArrayList;

public class BooksAdapter extends  RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    ArrayList<Book> books;


    public BooksAdapter(ArrayList<Book> books){
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //called when recycler view needs a new view holder

        Context context = viewGroup.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.book_list_item, viewGroup, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {
        //called to display the data

        Book book = books.get(i);
         bookViewHolder.bind(book);


    }

    @Override
    public int getItemCount() {

        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvAuthors,tvDate, tvPublisher;

        public BookViewHolder(View itemView){
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvAuthors = (TextView) itemView.findViewById(R.id.tvAuthors);
            tvDate = (TextView) itemView.findViewById(R.id.tvPublishedDate);
            tvPublisher = (TextView) itemView.findViewById(R.id.tvAuthors);

        }
        public void bind(Book book){
            tvTitle.setText(book.title);
            String authors = "";
            int i =0;
            for (String author:book.authors){
                authors += author;
                i++;

                if (i<book.authors.length){
                    author += ", ";
                }


            }
            tvAuthors.setText(authors);
            tvDate.setText(book.publishedDate);
            tvPublisher.setText(book.publisher);



        }

    }
}
