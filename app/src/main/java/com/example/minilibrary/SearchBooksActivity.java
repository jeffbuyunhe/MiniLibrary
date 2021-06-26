package com.example.minilibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchBooksActivity extends AppCompatActivity {

    private RecyclerView recViewSearch;
    private BookRecViewAdapter adapter;
    private ImageButton btnSearch;
    private EditText edtTxtSearch;

    private JSONArray bookArray, bookAuthorArray;
    private JSONObject bookInfo, bookVolumeInfo, bookImageLinks;
    private Book book;
    private String bookId, bookTitle, bookAuthor, bookImgUrl, bookDesc;
    private int bookPages;

    public static final String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);

        initViews();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getInstance(SearchBooksActivity.this).resetSearchBooks();
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, baseUrl + edtTxtSearch.getText().toString(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.optInt("totalItems") == 0) {
                                Toast.makeText(SearchBooksActivity.this, "No results were found, please try a different query", Toast.LENGTH_LONG).show();
                            } else {
                                bookArray = response.getJSONArray("items");
                                for (int i = 0; i < bookArray.length(); i++) {
                                    bookInfo = bookArray.getJSONObject(i);
                                    bookId = bookInfo.optString("id");

                                    bookVolumeInfo = bookInfo.getJSONObject("volumeInfo");
                                    bookTitle = bookVolumeInfo.optString("title");

                                    if (bookVolumeInfo.has("authors")) {
                                        bookAuthorArray = bookVolumeInfo.getJSONArray("authors");
                                        bookAuthor = "";
                                        for (int j = 0; j < bookAuthorArray.length(); j++) {
                                            bookAuthor += bookAuthorArray.optString(j) + ", ";
                                        }
                                        if (bookAuthor.length() >= 2) {
                                            bookAuthor = bookAuthor.substring(0, bookAuthor.length() - 2);
                                        }
                                    } else {
                                        bookAuthor = "N/A";
                                    }

                                    if (bookVolumeInfo.has("pageCount")) {
                                        bookPages = bookVolumeInfo.optInt("pageCount");
                                    } else {
                                        bookPages = -1;
                                    }

                                    bookImgUrl = "-1";
                                    if (bookVolumeInfo.has("imageLinks")) {
                                        bookImageLinks = bookVolumeInfo.getJSONObject("imageLinks");
                                        if (bookImageLinks.has("thumbnail")) {
                                            bookImgUrl = bookImageLinks.optString("thumbnail");
                                        }
                                    }

                                    if (bookVolumeInfo.has("description")) {
                                        bookDesc = bookVolumeInfo.optString("description");
                                    } else {
                                        bookDesc = "No description found";
                                    }

                                    book = new Book(bookId, bookTitle, bookAuthor, bookPages, bookImgUrl, bookDesc);
                                    Utils.getInstance(SearchBooksActivity.this).addSearchBooks(book);

                                    adapter = new BookRecViewAdapter(SearchBooksActivity.this, Utils.SEARCH_BOOKS_KEY);

                                    adapter.setBooks(Utils.getInstance(SearchBooksActivity.this).getSearchBooks());
                                    recViewSearch.setAdapter(adapter);
                                    recViewSearch.setLayoutManager(new LinearLayoutManager(SearchBooksActivity.this));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SearchBooksActivity.this, "Error loading some books, try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchBooksActivity.this, "There was an error with your search, please try again later", Toast.LENGTH_LONG).show();
                    }
                });
                ApiQueue.getInstance(SearchBooksActivity.this).addToRequestQueue(request);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initViews(){
        recViewSearch = findViewById(R.id.recViewSearch);
        btnSearch = findViewById(R.id.btnSearch);
        edtTxtSearch = findViewById(R.id.edtTxtSearch);

        adapter = new BookRecViewAdapter(this, Utils.SEARCH_BOOKS_KEY);
        recViewSearch.setAdapter(adapter);
        recViewSearch.setLayoutManager(new LinearLayoutManager(this));
        adapter.setBooks(Utils.getInstance(this).getSearchBooks());
    }
}