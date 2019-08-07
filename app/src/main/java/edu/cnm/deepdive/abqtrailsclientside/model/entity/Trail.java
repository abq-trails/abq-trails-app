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

package edu.cnm.deepdive.abqtrailsclientside.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

/**
 * Returns and sets data for <code>Trail</code> entity.
 */
@Entity(indices = {@Index(value = "cabq_id", unique = true)})
public class Trail {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "trail_id")
  @SerializedName("ignored_id")
  private long id;

  //change to Long if long does not work.
  @ColumnInfo(name = "cabq_id")
  private long cabqId;

  @ColumnInfo(name = "trail_name")
  private String name;

  @ColumnInfo(name = "trail_length")
  private double length;

  @ColumnInfo(name = "trail_rating")
  private double rating;

  @ColumnInfo(name = "trail_head_coordinates")
  private String coordinates;

  @ColumnInfo(name = "bike_trail")
  private boolean bike;

  @ColumnInfo(name = "horse_trail")
  private boolean horse;

  /**
   * Returns id for this instance.
   */
  public long getId() {
    return id;
  }

  /**
   * Sets id for this instance.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns name for this instance.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name for this instance.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns length for this instance.
   */
  public double getLength() {
    return length;
  }

  /**
   * Sets length for this instance.
   */
  public void setLength(double length) {
    this.length = length;
  }

  /**
   * Returns rating for this instance.
   */
  public double getRating() {
    return rating;
  }

  /**
   * Sets rating for this instance.
   */
  public void setRating(double rating) {
    this.rating = rating;
  }

  /**
   * Returns coordinates for this instance.
   */
  public String getCoordinates() {
    return coordinates;
  }

  /**
   * Sets coordinates for this instance.
   */
  public void setCoordinates(String coordinates) {
    this.coordinates = coordinates;
  }

  /**
   * Returns CabqId for this instance.
   */
  public long getCabqId() {
    return cabqId;
  }

  /**
   * Sets CabqId for this instance.
   */
  public void setCabqId(long cabqId) {
    this.cabqId = cabqId;
  }

  /**
   * Returns bike flag for this instance.
   */
  public boolean isBike() {
    return bike;
  }

  /**
   * Sets bike flag for this instance.
   */
  public void setBike(boolean bike) {
    this.bike = bike;
  }

  /**
   * Returns horse flag for this instance.
   */
  public boolean isHorse() {
    return horse;
  }

  /**
   * Sets horse flag for this instance.
   */
  public void setHorse(boolean horse) {
    this.horse = horse;
  }

}
