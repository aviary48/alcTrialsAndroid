package com.example.newbooks.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class SpUtil {
    private SpUtil(){}

    public static final String  PREF_NAME  = "BooksPreferences";
    public static final String POSITION = "position";
    public static final String QUERY = "query";


    //only the application creating the shared preference can access the shared preferece

    public static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

    }

    //method to read a string from the preference

    public static String getPreferenceString (Context context, String key){
        return  getPrefs(context).getString(key, "");

    }

    public static int getPreferencesInt(Context context, String key){

        return getPrefs(context).getInt(key, 0);
    }
    //methods to write string and int

    public static void setPreferenceInt(Context context, String key, int value){

        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public static void setPreferenceString(Context context, String key, String value){

        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static ArrayList<String> getQueryList(Context context){
        ArrayList<String> queryList = new ArrayList<String>();

        for (int i = 1; i <5; i++){
            String query = getPrefs(context).getString(QUERY + String.valueOf(i), "");
            if (!query.isEmpty()){
                query=query.replace(",", " ");
                queryList.add(query.trim());

            }
        }
        return queryList;

    }




}
