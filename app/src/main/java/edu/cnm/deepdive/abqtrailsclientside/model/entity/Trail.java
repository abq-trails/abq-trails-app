package edu.cnm.deepdive.abqtrailsclientside.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Trail {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "trail_id")
  private long  id;

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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public String getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(String coordinates) {
    this.coordinates = coordinates;
  }

  public long getCabqId() {
    return cabqId;
  }

  public void setCabqId(long cabqId) {
    this.cabqId = cabqId;
  }

  public boolean isBike() {
    return bike;
  }

  public void setBike(boolean bike) {
    this.bike = bike;
  }

  public boolean isHorse() {
    return horse;
  }

  public void setHorse(boolean horse) {
    this.horse = horse;
  }

}
