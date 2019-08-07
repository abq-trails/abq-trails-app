/*
Copyright 2019 Denelle Britton Linebarger, Alana Chigbrow, Anita Martin, David Nelson

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package edu.cnm.deepdive.abqtrailsclientside;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.abqtrailsclientside.model.database.TrailsDatabase;
import edu.cnm.deepdive.abqtrailsclientside.service.GoogleSignInService;

/**
 * Called when this application is starting.
 */
public class ABQTrailsApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    GoogleSignInService.setContext(this);
    new Thread(() -> TrailsDatabase.getInstance(this).trailDao().delete()).start();
  }
}
