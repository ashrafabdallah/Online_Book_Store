package com.example.newagnda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newagnda.model.Data;
import com.example.newagnda.model.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener{
    RecyclerView recyclerView;
    Button search;
    EditText text_filed;
    Data data;
    List<Data> list;
    ProgressBar progressBar;
    ImageView book;

    CardView cardView;

    private String title, authors, publisher, publisher_date, description, image, categories, leng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardView = findViewById(R.id.cardview);
        book = findViewById(R.id.start_book);
        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView = findViewById(R.id.recycler);
        //  search = findViewById(R.id.button_search);
        text_filed = findViewById(R.id.text_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        text_filed.setOnKeyListener(this);


    }

    public void run(View view) {
        String book_titel = text_filed.getText().toString().trim();
        if (book_titel.length() == 0) {
            // Toast.makeText(MainActivity.this, "Please Enter Book title", Toast.LENGTH_LONG).show();
            showtoast("Please Enter Book title...");
        }

        book.setVisibility(View.GONE);

        String Google_api = "https://www.googleapis.com/books/v1/volumes?q=" + book_titel;
        new MayTask().execute(Google_api);
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        text_filed.setText("");

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            run(v);
        }
        return false;
    }




    public class MayTask extends AsyncTask<String, Void, List<Data>> {
        StringBuilder builder = new StringBuilder();

        @Override
        protected List<Data> doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String s = "";
                while ((s = br.readLine()) != null) {
                    builder.append(s);
                }

                Log.i("json", builder.toString());

            } catch (MalformedURLException e) {

            } catch (IOException e) {
                e.printStackTrace();
            }


            return parrsBook(builder.toString());
        }

        private List<Data> parrsBook(String json) {
            List<Data> list = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray itemarray = jsonObject.getJSONArray("items");
                for (int i = 0; i < itemarray.length(); i++) {
                    JSONObject fristitem = itemarray.getJSONObject(i);
                    JSONObject volumeInfo = fristitem.getJSONObject("volumeInfo");
                    if (volumeInfo.has("title")) {
                        title = volumeInfo.getString("title");
                    } else {
                        title = "title not found";
                    }
                    if (volumeInfo.has("authors")) {

                        authors = volumeInfo.getJSONArray("authors").getString(0);

                    } else {
                        authors = "authors not found";
                    }
                    if (volumeInfo.has("publisher")) {
                        publisher = volumeInfo.getString("publisher");
                    } else {
                        publisher = "publisher not found";
                    }
                    if (volumeInfo.has("publishedDate")) {
                        publisher_date = volumeInfo.getString("publishedDate");
                    } else {
                        publisher_date = "publishedDate not found";
                    }
                    if (volumeInfo.has("description")) {
                        description = volumeInfo.getString("description");
                    } else {
                        description = "description not found";
                    }
                    if (volumeInfo.has("categories")) {
                        categories = volumeInfo.getJSONArray("categories").getString(0);
                    } else {
                        categories = "categories not found";
                    }
                    if (volumeInfo.has("imageLinks")) {
                        JSONObject imageobject = volumeInfo.getJSONObject("imageLinks");
                        image = imageobject.getString("thumbnail");
                    }else {image="";}
                    if (volumeInfo.has("language"))
                    {
                        leng=volumeInfo.getString("language");
                    }else {leng="language not found";}
                    list.add(new Data(title,authors,publisher,publisher_date,description,image,categories,leng));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(haveNetwork())
            {
                progressBar.setVisibility(View.VISIBLE);
            }else if(!haveNetwork()) {
                showtoast("Network Connection Is Not Available.... please try again");
            }

        }

        @Override
        protected void onPostExecute(final List<Data> data) {
            super.onPostExecute(data);
            MyAdapter adapter = new MyAdapter(MainActivity.this, data);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
            /*
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(MainActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            // do whatever
                         Data da= data.get(position);
                            Intent i=new Intent(MainActivity.this, Second.class);
                            i.putExtra("data",da);
                            startActivity(i);

                        }

                        @Override public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
           adapter.notifyDataSetChanged();
*/
        }
    }

    public void showtoast(String toast_text) {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup group = findViewById(R.id.cardview);
        View v = inflater.inflate(R.layout.customtoast, group, false);
        TextView textView = (TextView) v.findViewById(R.id.text_toast);
        Toast toast = new Toast(MainActivity.this);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        textView.setText(toast_text);
        toast.setView(v);
        toast.show();

    }
    public boolean haveNetwork()
    {
        boolean have_wifi=false;
        boolean have_mobiledata=false;
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos)
        {
            if(info.getTypeName().equalsIgnoreCase("WIFI"))
                if(info.isConnected())
                    have_wifi=true;
            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                if(info.isConnected())
                    have_mobiledata=true;

        }
        return have_mobiledata||have_wifi;
    }
}
