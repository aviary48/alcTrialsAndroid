package com.example.newbooks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newbooks.Utils.ApiUtil;
import com.example.newbooks.Utils.SpUtil;

import java.net.URL;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText etTitle = (EditText) findViewById(R.id.etTitle);
        final EditText etAuthor = (EditText) findViewById(R.id.etAuthor);
        final EditText etPublisher = (EditText) findViewById(R.id.etPublisher);
        final EditText etIsbn = (EditText) findViewById(R.id.etIsbn);
        final  Button  button = (Button) findViewById(R.id.btnSearch);

        button.setOnClickListener( new View.OnClickListener(){


            public void onClick(View view){
                String title = etTitle.getText().toString().trim();
                String author = etAuthor.getText().toString().trim();
                String publisher = etPublisher.getText().toString().trim();
                String isbn = etIsbn.getText().toString().trim();

                if (title.isEmpty() && author.isEmpty() && publisher.isEmpty() && isbn.isEmpty()){

                    String message = getString(R.string.no_search_data);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } else{
                    URL queryURL = ApiUtil.buildUrl(title, author,publisher,isbn);

                    //shared preference
                    Context context = getApplicationContext();
                    int position = SpUtil.getPreferencesInt(context, SpUtil.POSITION);
                    if (position == 0   || position == 5){
                        position = 1;

                    } else{
                        position ++;
                    }

                    String key = SpUtil.QUERY + String.valueOf(position);
                    String value = title + "," + author + "," + publisher + "," + author + ",";
                    SpUtil.setPreferenceString(context, key, value);
                    SpUtil.setPreferenceInt(context, SpUtil.POSITION, position);



                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("Query", queryURL);
                    startActivity( intent);

                }

            }
        });


    }
}
