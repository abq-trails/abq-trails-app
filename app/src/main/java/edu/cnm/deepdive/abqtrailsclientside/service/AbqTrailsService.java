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

  /**
   * Specify the request type, pass the relative URL and wrap response in an Observable object with
   * the type of the expected result.
   * @param fragment
   * @return trail object
   */
  @GET("trails/search")
  Single<Trail> searchById(@Query("cabqId") String fragment);


  /**
   *
   * @param fragment
   * @return list of trails by name
   */
  @GET("trails/search")
  Observable<List<Trail>> searchByName(@Query("nameFrag") String fragment);


  /**
   *
   * @return list of trails
   */
  @GET("trails")
  Observable<List<Trail>> listTrails();

  /**
   *
   * @return trail id
   */
  @GET("trails/{id}")
  Single<Trail> id();

  @GET("trails/coordinates")
  List<Trail> searchByCoordinates(@Query("coordinates") String fragment);

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


  /**
   * Allows initialization on demand for this service.
   * @return instance of this service
   */
  static AbqTrailsService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * Implements singleton pattern for this service.
   */
  class InstanceHolder {

    private static final AbqTrailsService INSTANCE;

    /**
     * Creates a retrofit object and an instance of this service for making requests to the backend.
     */
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
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(AbqTrailsService.class);

    }

  }
}
