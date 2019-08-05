package edu.cnm.deepdive.abqtrailsclientside.service;

import android.app.Application;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import edu.cnm.deepdive.abqtrailsclientside.BuildConfig;

/**
 *
 */
public class GoogleSignInService {

  private static Application context;

  private GoogleSignInAccount account;
  private GoogleSignInClient client;

  private GoogleSignInService() {
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId() //can use to identify user as unique identifier not primary key
        .requestProfile() //get image
        .requestIdToken(BuildConfig.CLIENT_ID) //passport stamp for the webservice
        .build();
    client = GoogleSignIn.getClient(context, options);
  }

  /**
   * Sets context for this instance.
   * @param context sets context
   */
  public static void setContext(Application context) {
    GoogleSignInService.context = context;
  }

  /**
   * Returns client for this instance.
   */
  public GoogleSignInClient getClient() {
    return client;
  }

  /**
   * Returns account for this instance.
   */
  public GoogleSignInAccount getAccount() {
    return account;
  }

  /**
   * Sets account for this instance.
   */
  public void setAccount(GoogleSignInAccount account) {
    this.account = account;
  }

  /**
   * Returns instance.
   */
  public static GoogleSignInService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {

    private static final GoogleSignInService INSTANCE = new GoogleSignInService();

  }

}
