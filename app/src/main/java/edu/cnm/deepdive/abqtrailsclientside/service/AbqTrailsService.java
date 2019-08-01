package edu.cnm.deepdive.abqtrailsclientside.service;

import edu.cnm.deepdive.abqtrailsclientside.BuildConfig;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AbqTrailsService {

  @GET("trails/search")
  Single<Trail> searchById(@Query("cabqId") String fragment);

  @GET("trails/search")
  Observable<List<Trail>> searchByName(@Query("nameFrag") String fragment);

  @GET("trails")
  Observable<List<Trail>> listTrails();

  @GET("trails/{id}")
  Single<Trail> id();

//  @GET("users/{id}")
//  Single<User> getById();
//
//  @GET("users")
//  Observable<List<User>> listUsers();
//
//  @POST("reviews")
//  Single<Review> create(@Header("Authorization") String oauthHeader, @Body Review review);
//
//  @GET("reviews")
//  Observable<List<Review>> getReviews();


  static AbqTrailsService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  class InstanceHolder {

    private static final AbqTrailsService INSTANCE;

    static {
      // Following five lines should be removed/commented out for production release.
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .client(client) // This should be removed/commented out for production release.
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create()) // TODO Check; maybe change?
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(AbqTrailsService.class);

    }

  }
}
