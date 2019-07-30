package com.example.newbooks;

import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
//import android.widget.SearchView;
import android.widget.TextView;
import android.support.v7.widget.SearchView;



import com.example.newbooks.Adapters.BooksAdapter;
import com.example.newbooks.Models.Book;
import com.example.newbooks.Utils.ApiUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ProgressBar mLoadingProgress;
    private RecyclerView rvBooks;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingProgress = (ProgressBar) findViewById(R.id.pb_loading);
        rvBooks = (RecyclerView) findViewById(R.id.rv_books);
        LinearLayoutManager booksLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        rvBooks.setLayoutManager(booksLayoutManager);
        try {
            URL bookUrl = ApiUtil.buildUrl("cooking");
            new BookQueryTask().execute(bookUrl);

//            String  jsonResult = ApiUtil.getJson(bookUrl);

        } catch (Exception e) {
//            e.printStackTrace();
            Log.d("error" , e.getMessage());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.book_list_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView= (SearchView) searchItem.getActionView();

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return  true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
         try{
             URL bookUrl = ApiUtil.buildUrl(s);
             new BookQueryTask().execute(bookUrl);

        } catch (Exception e){

             Log.d("error", e.getMessage());

         }


        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public  class BookQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
//            return null;
            URL searchURL =  urls[0];
            String result = null;

            try{

                result = ApiUtil.getJson(searchURL);

            }catch (Exception e){
                Log.d("error",e.getMessage());

            }
            return  result;
        }

        protected void onPostExecute (String result){

            TextView tvError = (TextView) findViewById(R.id.tv_error);

            mLoadingProgress.setVisibility(View.INVISIBLE);

            if (result == null){
                rvBooks.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);

            } else{
                rvBooks.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }

//            tvResult.setText(result);

            ArrayList<Book> books = ApiUtil.getBoooksFromJson(result);
            String resultString = "";

//            tvResult.setText(resultString);

            BooksAdapter adapter = new BooksAdapter(books);
            rvBooks.setAdapter(adapter);


        }


        protected void onPreExecute(){ //called before the network call
            mLoadingProgress.setVisibility(View.VISIBLE);

        }
    }






}

