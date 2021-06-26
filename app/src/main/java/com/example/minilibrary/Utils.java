package com.example.minilibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static Utils util = null;

    private SharedPreferences sharedPreferences;

    private ArrayList<MenuItem> menuItemArrayList;

    private static ArrayList<Book> searchBooksArrayList;

    public static final String SEARCH_BOOKS_KEY = "searchBooks";
    public static final String ALREADY_READ_BOOKS_KEY = "alreadyReadBooks";
    public static final String CURRENTLY_READ_BOOKS_KEY = "currentlyReadBooks";
    public static final String FAVOURITES_KEY = "favouriteBooks";

    public static final String BOOK_ID_KEY = "bookId";
    public static final String LIST_ID_KEY = "listId";

    public static synchronized Utils getInstance(Context context){
        if(util == null){
            util = new Utils(context);
        }
        return util;
    }

    private Utils(Context context){
        sharedPreferences = context.getSharedPreferences("user_storage", Context.MODE_PRIVATE);

        this.menuItemArrayList = new ArrayList<>();
        searchBooksArrayList = new ArrayList<>();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if(null == getAlreadyReadBooks()){
            editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
        }
        if(null == getCurrentlyReadBooks()){
            editor.putString(CURRENTLY_READ_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
        }
        if(null == getFavouriteBooks()){
            editor.putString(FAVOURITES_KEY, gson.toJson(new ArrayList<Book>()));
        }
        editor.commit();
        initMenu(menuItemArrayList);
    }

    public ArrayList<MenuItem> getMenuItemArrayList(){
        return this.menuItemArrayList;
    }

    public void addSearchBooks(Book book){
        searchBooksArrayList.add(book);
    }

    public ArrayList<Book> getSearchBooks(){
        return searchBooksArrayList;
    }

    public void resetSearchBooks(){
        searchBooksArrayList = new ArrayList<Book>();
    }

    private void initMenu(ArrayList<MenuItem> menuItemArrayList){
        menuItemArrayList.add(new MenuItem("Search Books", R.drawable.ic_search, R.color.search_colour, SearchBooksActivity.class));
        menuItemArrayList.add(new MenuItem("Currently Reading", R.drawable.ic_currently_read, R.color.currently_read_colour, CurrentlyReadActivity.class));
        menuItemArrayList.add(new MenuItem("Already Read", R.drawable.ic_already_read, R.color.already_read_colour, AlreadyReadBooksActivity.class));
        menuItemArrayList.add(new MenuItem("Favourites", R.drawable.ic_favourites, R.color.favourites_colour, FavouriteBooksActivity.class));
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS_KEY, null), type);
    }

    public ArrayList<Book> getCurrentlyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(CURRENTLY_READ_BOOKS_KEY, null), type);
    }

    public ArrayList<Book> getFavouriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(FAVOURITES_KEY, null), type);
    }

    public boolean addBook(Book book, String listId){
        ArrayList<Book> books = getListByKey(listId);
        if(books != null){
            books.add(book);
            convertArrayListToJson(books, listId);
            return true;
        }
        return false;
    }

    public boolean removeBook(Book book, String listId){
        ArrayList<Book> books = getListByKey(listId);
        if(books != null){
            int foundBookIndex = getBookIndexById(book.getId(), listId);
            System.out.println("FOUND " + foundBookIndex);

            if(foundBookIndex != -1) {
                for(Book b:books){
                    System.out.println("BEFORE REMOVAL " + b.toString());
                }
                System.out.println("REMOVE RETURNED " + books.remove(foundBookIndex));
                for(Book b:books){
                    System.out.println("AFTER REMOVAL " + b.toString());
                }
                convertArrayListToJson(books, listId);
                return true;
            }
        }
        return false;
    }

    public Book getBookById(String bookId, String keyId){
        ArrayList<Book> books;

        books = getListByKey(keyId);

        if(null != books) {
            for (Book book : books) {
                if (book.getId().equals(bookId)) {
                    return book;
                }
            }
        }
        return null;
    }

    public int getBookIndexById(String bookId, String keyId){
        ArrayList<Book> books;
        int i = 0;

        books = getListByKey(keyId);

        if(null != books) {
            for (Book book : books) {
                if (book.getId().equals(bookId)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    public ArrayList<Book> getListByKey(String keyId){
        switch(keyId){
            case SEARCH_BOOKS_KEY:
                return this.getSearchBooks();
            case ALREADY_READ_BOOKS_KEY:
                return this.getAlreadyReadBooks();
            case CURRENTLY_READ_BOOKS_KEY:
                return this.getCurrentlyReadBooks();
            case FAVOURITES_KEY:
                return this.getFavouriteBooks();
            default:
                return null;
        }
    }

    public void convertArrayListToJson(ArrayList<Book> books, String listId){
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(listId);
        editor.putString(listId, gson.toJson(books));
        editor.commit();
    }
}
