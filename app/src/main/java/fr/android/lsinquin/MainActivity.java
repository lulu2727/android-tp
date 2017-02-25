package fr.android.lsinquin;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements ListFragment.OnSelectBookListener {

    private Book selectedBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        Timber.i("Creating Main Activity");

        setContentView(R.layout.activity_library);


        if (savedInstanceState != null) {
            this.selectedBook = savedInstanceState.getParcelable("selectedBook");
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.setUpLandscapeScreen();
        } else {
            this.setUpPortraitScreen();
        }

//
    }

    @Override
    public void onSelectBook(Book book) {
        Timber.i("Selecting book " + book.getTitle());
        this.selectedBook = book;

        Bundle bundle = new Bundle();
        bundle.putParcelable("book", book);
        Fragment fragment = new BookDetailsFragment();
        fragment.setArguments(bundle);

        int containerId = R.id.containerFrameLayout;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            containerId = R.id.rightContainerFrameLayout;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment, BookDetailsFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("selectedBook", this.selectedBook);
    }

    private void setUpLandscapeScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.leftContainerFrameLayout, new ListFragment(), ListFragment.class.getSimpleName())
                .commit();

        //Set up details view for the last selected book. If no book has been selected, does nothing.
        if (this.selectedBook != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("book", this.selectedBook);
            Fragment fragment = new BookDetailsFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rightContainerFrameLayout, fragment, BookDetailsFragment.class.getSimpleName())
                    .commit();
        }
    }

    private void setUpPortraitScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerFrameLayout, new ListFragment(), ListFragment.class.getSimpleName())
                .commit();
    }
}
