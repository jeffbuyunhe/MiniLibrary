package com.example.minilibrary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder> {

    String imgUrl, listId;
    ArrayList<Book> books = new ArrayList<>();
    Context context;

    public BookRecViewAdapter(Context context, String listId){
        this.context = context;
        this.listId = listId;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public BookRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BookRecViewAdapter.ViewHolder holder, int position) {
        initCard(holder, position);
        setCardListeners(holder, position);
    }

    private void initCard(@NonNull @NotNull BookRecViewAdapter.ViewHolder holder, int position){
        imgUrl = books.get(position).getImgUrl();

        if(!imgUrl.contains("https:")){
            imgUrl = imgUrl.replace("http:", "https:");
        }

        Picasso.get().load(imgUrl).into(holder.bookImage);

        holder.bookTitle.setText(books.get(position).getTitle());
        holder.bookAuthour.setText(books.get(position).getAuthor());
    }

    private void setCardListeners(@NonNull @NotNull BookRecViewAdapter.ViewHolder holder, int position){
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra(Utils.BOOK_ID_KEY, books.get(position).getId());
                intent.putExtra(Utils.LIST_ID_KEY, listId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView book;
        private ImageView bookImage;
        private TextView bookTitle;
        private TextView bookAuthour;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            book = itemView.findViewById(R.id.bookItem);
            bookImage = itemView.findViewById(R.id.bookItemImage);
            bookTitle = itemView.findViewById(R.id.bookItemTitle);
            bookAuthour = itemView.findViewById(R.id.bookItemAuthour);
        }
    }
}
