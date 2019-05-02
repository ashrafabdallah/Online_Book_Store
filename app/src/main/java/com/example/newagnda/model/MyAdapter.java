package com.example.newagnda.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newagnda.R;
import com.example.newagnda.Thread;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    List<Data> list;

    public MyAdapter(Context context, List<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Data data = list.get(position);
        holder.text_title.setText(data.getTitle());
        holder.author.setText(data.getAuthors());
        holder.publisher.setText(data.getPublisher());

        if (data.getImage().length() != 0) {
            Picasso.get()
                    .load(data.getImage() + ".png")
                    .placeholder(R.drawable.books)
                    .error(R.drawable.books)
                    .resize(100, 100)

                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.books);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, Thread.class);
                i.putExtra("data",data);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView text_title, author, publisher;
CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
            text_title= (TextView) itemView.findViewById(R.id.publisher);
            author = (TextView) itemView.findViewById(R.id.date);
            publisher = (TextView) itemView.findViewById(R.id.description);
            cardView=(CardView)itemView.findViewById(R.id.cardview);
        }
    }
}
