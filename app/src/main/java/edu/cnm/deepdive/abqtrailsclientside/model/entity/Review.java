package edu.cnm.deepdive.abqtrailsclientside.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(indices = {@Index(value = "cabq_id", unique = true)})
public class Review {


  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "review_id")
  @SerializedName("ignored_id")
  private long id;

  @ColumnInfo(name = "cabq_id")
  private long cabqId;

  @ColumnInfo(name = "rating")
  private int rating;

  @ColumnInfo(name = "review")
  private String review;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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
}
