package fr.android.lsinquin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HenriPotierService {
    @GET("books")
    Call<List<Book>> listBooks();
}
