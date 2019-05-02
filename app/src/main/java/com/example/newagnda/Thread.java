package com.example.newagnda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newagnda.model.Data;
import com.squareup.picasso.Picasso;

public class Thread extends AppCompatActivity {
    ImageView imageView;
    TextView text_titel,text_publisher,text_date,text_auther,text_description,text_categroy,text_leng;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        getSupportActionBar().hide();

        // ini views

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        imageView=findViewById(R.id.aa_thumbnail);
        text_titel=findViewById(R.id.aa_anime_name);
        text_auther=findViewById(R.id.authors);
        text_publisher=findViewById(R.id.publisher);
        text_date=findViewById(R.id.date);
        text_description=findViewById(R.id.aa_description);
        text_categroy=findViewById(R.id.catedories);
        text_leng=findViewById(R.id.lang);
        Intent i=getIntent();
        Data data = i.getExtras().getParcelable("data");
        if (data.getImage().length() != 0) {
            Picasso.get()
                    .load(data.getImage() + ".png")
                    .placeholder(R.drawable.books)
                    .error(R.drawable.books)
                    .resize(100, 100)

                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.books);
        }
        text_leng.setText(data.getLeng());
        text_categroy.setText(data.getCategories());
        text_description.setText(data.getDescription());
        text_date.setText(data.getPublisher_date());
        text_auther.setText(data.getAuthors());
        text_publisher.setText(data.getPublisher());
        text_titel.setText(data.getTitle());
        collapsingToolbarLayout.setTitle(data.getTitle());
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
