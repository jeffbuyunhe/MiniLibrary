package com.example.minilibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class BookActivity extends AppCompatActivity {

    private TextView txtTitle, txtAuthor, txtPages, txtDesc;
    private Button btnAddFavourites, btnAddAlreadyRead, btnAddCurrentlyReading, btnDelete;
    private ImageView bookImage;
    private String imgUrl;

    private static final String PAGES_TXT =  " Pages";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent = getIntent();
        String bookId = intent.getStringExtra(Utils.BOOK_ID_KEY);
        String listKey = intent.getStringExtra(Utils.LIST_ID_KEY);

        if(intent != null && bookId != null && listKey != null) {
            Book book = getBook(bookId, listKey);
            initViews();
            setData(book);

            setButtonsEnabled(book);
            setDeleteButton(book, listKey);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private Book getBook(String bookId, String listKey){
        return Utils.getInstance(this).getBookById(bookId, listKey);
    }

    private void initViews(){
        txtTitle = findViewById(R.id.txtTitleBookView);
        txtAuthor = findViewById(R.id.txtAuthorBookView);
        txtPages = findViewById(R.id.txtPagesBookView);
        txtDesc = findViewById(R.id.txtDescBookView);
        
        btnAddAlreadyRead = findViewById(R.id.btnAlreadyReadBookView);
        btnAddCurrentlyReading = findViewById(R.id.btnCurrentlyReadingBookView);
        btnAddFavourites = findViewById(R.id.btnFavouritesBookView);
        btnDelete = findViewById(R.id.btnDeleteBookView);

        bookImage = findViewById(R.id.imgBookView);
    }

    private void setData(Book book){
        if(book == null){
            return;
        }
        txtAuthor.setText(book.getAuthor());
        txtTitle.setText(book.getTitle());
        if(book.getPages() != -1) {
            txtPages.setText(String.valueOf(book.getPages()) + PAGES_TXT);
        }
        txtDesc.setText(book.getDesc());

        imgUrl = book.getImgUrl();

        if(!imgUrl.contains("https:")){
            imgUrl = imgUrl.replace("http:", "https:");
        }

        Picasso.get().load(imgUrl).into(bookImage);
    }

    private void setButtonsEnabled(Book book){
        if(Utils.getInstance(this).getBookById(book.getId(), Utils.ALREADY_READ_BOOKS_KEY) != null){
            btnAddAlreadyRead.setEnabled(false);
        }
        else{
            btnAddAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addBook(book, Utils.ALREADY_READ_BOOKS_KEY)){
                        Toast.makeText(BookActivity.this, "Book Successfully Added!", Toast.LENGTH_SHORT).show();
                        btnAddAlreadyRead.setEnabled(false);
                    }
                    else{
                        Toast.makeText(BookActivity.this, "There was an error adding your book, please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if(Utils.getInstance(this).getBookById(book.getId(), Utils.CURRENTLY_READ_BOOKS_KEY) != null){
            btnAddCurrentlyReading.setEnabled(false);
        }
        else{
            btnAddCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addBook(book, Utils.CURRENTLY_READ_BOOKS_KEY)){
                        Toast.makeText(BookActivity.this, "Book successfully added!", Toast.LENGTH_SHORT).show();
                        btnAddCurrentlyReading.setEnabled(false);
                    }
                    else{
                        Toast.makeText(BookActivity.this, "There was an error adding your book, please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if(Utils.getInstance(this).getBookById(book.getId(), Utils.FAVOURITES_KEY) != null){
            btnAddFavourites.setEnabled(false);
        }
        else{
            btnAddFavourites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addBook(book, Utils.FAVOURITES_KEY)){
                        Toast.makeText(BookActivity.this, "Book Successfully Added!", Toast.LENGTH_SHORT).show();
                        btnAddFavourites.setEnabled(false);
                    }
                    else{
                        Toast.makeText(BookActivity.this, "There was an error adding your book, please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setDeleteButton(Book book, String listKey){
        Class afterDeletion;
        if(listKey.equals(Utils.SEARCH_BOOKS_KEY)){
            btnDelete.setVisibility(View.GONE);
        }
        else{
            btnDelete.setVisibility(View.VISIBLE);

            switch (listKey){
                case Utils.ALREADY_READ_BOOKS_KEY:
                    btnDelete.setText(getResources().getString(R.string.delete) + " " + getResources().getString(R.string.from) + " " + getResources().getString(R.string.already_read));
                    afterDeletion = AlreadyReadBooksActivity.class;
                    break;
                case Utils.CURRENTLY_READ_BOOKS_KEY:
                    btnDelete.setText(getResources().getString(R.string.delete) + " " +getResources().getString(R.string.from) + " " + getResources().getString(R.string.currently_reading));
                    afterDeletion = CurrentlyReadActivity.class;
                    break;
                case Utils.FAVOURITES_KEY:
                    btnDelete.setText(getResources().getString(R.string.delete) + " " +getResources().getString(R.string.from) + " " + getResources().getString(R.string.favourite_books));
                    afterDeletion = FavouriteBooksActivity.class;
                    break;
                default:
                    afterDeletion = MainActivity.class;
                    break;
            }
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).removeBook(book, listKey)){
                        Toast.makeText(BookActivity.this, "Book removed successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, afterDeletion);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(BookActivity.this, "There was an error removing your book, please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}