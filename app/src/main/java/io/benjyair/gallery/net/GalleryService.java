package io.benjyair.gallery.net;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GalleryService {

    @GET("j")
    Observable<Gallery> getGallery(@Query("q") String key, @Query("sn") int index, @Query("pn") int size);

}
