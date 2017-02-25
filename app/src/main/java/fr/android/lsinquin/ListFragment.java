package fr.android.lsinquin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ListFragment extends Fragment implements BookRecyclerAdapter.OnItemClickListener {

    private final String url = "http://henri-potier.xebia.fr/";

    private RecyclerView recyclerView;
    private BookRecyclerAdapter adapter;
    public ArrayList<Book> books;
    public OnSelectBookListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (OnSelectBookListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.i("OnCreateView ListFragment");
        View view = inflater.inflate(R.layout.list_book, container, false);

        if (savedInstanceState == null) {
            //conditional to not load data if the user come from details view thanks to the back button;
            if (this.books == null) {
                this.books = new ArrayList<>();
                getBooksData();
            }
        } else {
            Timber.i("Retrieving saved data");
            ArrayList<Book> savedBooks = savedInstanceState.<Book>getParcelableArrayList("books");
            this.books = savedBooks;
        }
        this.recyclerView = (RecyclerView) view.findViewById(R.id.bookListView);
        this.adapter = new BookRecyclerAdapter(LayoutInflater.from(getActivity()), books, ListFragment.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void getBooksData() {

        // Plant logger cf. Android Timbe
        Timber.i("Loading Books data");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HenriPotierService service = retrofit.create(HenriPotierService.class);

        Call<List<Book>> booksCall = service.listBooks();

        // TODO enqueue call and display book title
        // TODO log books
        booksCall.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                books.addAll(response.body());
                adapter.notifyDataSetChanged();

                for(int i = 0; i < books.size(); i++){
                    Timber.d(books.get(i).getTitle() + " - " + books.get(i).getPrice());
                }

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Timber.e(t.getMessage());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("books", books);
    }

    @Override
    public void onItemClick(Book book) {
        listener.onSelectBook(book);
    }

    public interface OnSelectBookListener {
        void onSelectBook(Book book);
    }
}
