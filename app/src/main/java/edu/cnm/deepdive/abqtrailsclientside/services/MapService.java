package edu.cnm.deepdive.abqtrailsclientside.services;

import edu.cnm.deepdive.abqtrailsclientside.BuildConfig;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapService {

    static MapService getInstance() {
        return MapService.InstanceHolder.INSTANCE;
    }
// TODO if anything is needed add more after access to the database.
    @GET("search/coordinates")
    Single getCoordinates(@Query("q") String fragment);

    class InstanceHolder {

        private static final MapService INSTANCE;

        static {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl((BuildConfig.BASE_URL))
                    .build();
            INSTANCE = retrofit.create(MapService.class);
        }
    }
}
