package edu.cnm.deepdive.abqtrailsclientside.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity
public class Review {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "app_id")
  @SerializedName("ignored_id")
  private long appId;

  @ColumnInfo(name = "cabq_id")
  private long cabqId;

  @ColumnInfo(name = "trail_rating")
  private int rating;

  @ColumnInfo(name = "trail_review")
  private String review;

  @ColumnInfo
  private String username;

  public long getAppId() {
    return appId;
  }

  public void setAppId(long appId) {
    this.appId = appId;
  }

  public long getCabqId() {
    return cabqId;
  }

  public void setCabqId(long cabqId) {
    this.cabqId = cabqId;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}