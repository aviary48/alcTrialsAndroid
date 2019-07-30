package com.example.newbooks.Utils;

import android.net.Uri;
import android.util.Log;

import com.example.newbooks.Models.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {

    //app will never be instantiated  so we remove the constractor

    private ApiUtil(){}


    public static final String BASE_API_URL ="https://www.googleapis.com/books/v1/volumes";

    public static final String QUERY_PARAMETER_KEY = "q";
    public static final String KEY ="key";
    public static final String API_KEY = "AIzaSyAydSMOLx66MFP0pfeNU3iAsqK5Z34vmRs";

    public static URL buildUrl(String title){


        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY,title)
//                .appendQueryParameter(KEY, API_KEY)  <<<<<<< this is commented out because i can't figure out what the bug is
                .build();

        try{
            url  = new URL(uri.toString());

//            url = new URL(fullUrl);

        } catch (Exception e){
//            e.printStackTrace();
            Log.d("err2", e.getMessage());

        }

        return url;


    }

    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //reading the data can be even a web page


        try{
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);

            scanner.useDelimiter("\\A");//partern regex


            boolean hasData = scanner.hasNext();
            if (hasData){
                return scanner.next();
            }
            else {
                return null;
            }

        } catch (IOException e){

            Log.d("Error", e.toString());

            return null;
        }

        finally {
            connection.disconnect();
        }


    }

    public static ArrayList<Book> getBoooksFromJson( String json){

        //best practice to use constants instead of strings in code

        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER ="publisher";
        final String PUBLISHED_DATE ="publishedDate";
        final String ITEMS = "items";
        final String VOLUMEINFO = "volumeInfo";


//        ArrayList<Book> books = null;
        ArrayList<Book> books = new ArrayList<Book>();
        try{
            JSONObject jsonBooks = new JSONObject(json);
            JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);
            //getting No of books
            int numberOfBooks = arrayBooks.length();

            //looping thru the json array
            for ( int i = 0; i < numberOfBooks; i++){
                JSONObject bookJSON =  arrayBooks.getJSONObject(i);
                JSONObject volumeInfoJSON = bookJSON.getJSONObject(VOLUMEINFO);

                //getting authors and put them in a string array

                int authorNum = volumeInfoJSON.getJSONArray(AUTHORS).length();
                String[] authors = new String[authorNum];

                for (int j = 0; j<authorNum; j++){

                    authors[j] = volumeInfoJSON.getJSONArray(AUTHORS).get(j).toString();
                }

                //creating a new book
                Book book = new Book(
                        bookJSON.getString(ID),
                        volumeInfoJSON.getString(TITLE),
                        (volumeInfoJSON.isNull(SUBTITLE) ? "" : volumeInfoJSON.getString(SUBTITLE)),
                        authors,
                        volumeInfoJSON.getString(PUBLISHER),
                        volumeInfoJSON.getString(PUBLISHED_DATE)

                );
                //adding the new book to  books list array

                books.add(book);





            }


        }catch (JSONException e){
            e.printStackTrace();

        }

        return books;

    }


}
