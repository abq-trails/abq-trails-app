//Copyright 2019 Denelle Britton Linebarger, Alana Chigbrow, Anita Martin, David Nelson
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.

package edu.cnm.deepdive.abqtrailsclientside.service;

import edu.cnm.deepdive.abqtrailsclientside.BuildConfig;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 *
 */
public interface AbqTrailsService {

  /**
   * Allows initialization on demand for this service.
   *
   * @return instance of this service
   */

  static AbqTrailsService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * Specify the request type, pass the relative URL and wrap response in an Observable object with
   * the type of the expected result.
   *
   * @param fragment ??
   * @return trail specified.
   */

  @GET("trails/search")
  Single<Trail> searchById(@Query("cabqId") String fragment);

  /**
   *
   */
  @GET("trails/search")
  Observable<List<Trail>> searchByName(@Query("nameFrag") String fragment);

  /**
   *
   */
  @GET("trails")
  Observable<List<Trail>> listTrails();

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
   *
   */
  @GET("trails/{id}")
  Single<Trail> id();

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
          .readTimeout(0, TimeUnit.MILLISECONDS)
          .build();
      Retrofit retrofit = null;
      try {
        retrofit = new Retrofit.Builder()
            .client(client) // This should be removed/commented out for production release.
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()) // TODO Check; maybe change?
            .baseUrl(BuildConfig.BASE_URL)
            .build();
      } catch (Exception e) {
        e.printStackTrace();
      }
      INSTANCE = retrofit.create(AbqTrailsService.class);

    }

  }
}
