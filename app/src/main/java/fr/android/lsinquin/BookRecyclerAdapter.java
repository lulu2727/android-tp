package fr.android.lsinquin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BookRecyclerAdapter extends RecyclerView.Adapter {

    private final LayoutInflater inflater;
    private final List<Book> books;
    private OnItemClickListener listener;

    public BookRecyclerAdapter(LayoutInflater from, List<Book> books, OnItemClickListener listener) {
        this.inflater = from;
        this.books = books;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BookItemView itemView =  (BookItemView) holder.itemView;
        final Book book = books.get(position);
        itemView.bindView(book);
        itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(book);
            }
        });
    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView){
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
}