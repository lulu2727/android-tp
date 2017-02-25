package fr.android.lsinquin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import timber.log.Timber;

public class BookDetailsFragment extends Fragment {

    private Book book;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_details, container, false);

        Timber.i("OnCreateView BookDetailsFragment");

        this.book = getArguments().getParcelable("book");
        this.initView(view);

        return view;
    }

    private void initView(View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        TextView synopsisTextView = (TextView) view.findViewById(R.id.synopsisTextView);

        ImageView bookImageView = (ImageView) view.findViewById(R.id.bookImageView);

        titleTextView.setText(book.getTitle());
        priceTextView.setText(book.getPrice() + '€');
        String synopsis = "";
        String[] synopsisArray = book.getSynopsis();

        for (int i = 0; i < synopsisArray.length; i++) {
            synopsis += synopsisArray[i] + System.getProperty("line.separator");
        }

        synopsisTextView.setText(synopsis);

        Glide.with(this.getContext())
                .load(book.getCover())
                .into(bookImageView);

    }
}
