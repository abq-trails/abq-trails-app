package edu.cnm.deepdive.abqtrailsclientside.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 *
 */
@Entity
public class Trail {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "trail_id")
  private long  id;

  @ColumnInfo(name = "trail_name")
  private String name;

  @ColumnInfo(name = "trail_length")
  private double length;

  @ColumnInfo(name = "trail_rating")
  private double rating;

  @ColumnInfo(name = "trail_head_coordinates")
  private double coordinates;

  @ColumnInfo(name = "bike_trail")
  private boolean bike;

  @ColumnInfo(name = "horse_trail")
  private boolean horse;

  @ColumnInfo(name = "dogs_allowed")
  private boolean dog;


  /**
   * Returns  for this instance.
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
  public double getCoordinates() {
    return coordinates;
  }

  /**
   * Sets coordinates for this instance.
   */
  public void setCoordinates(double coordinates) {
    this.coordinates = coordinates;
  }

  /**
   * Returns bike for this instance.
   */
  public boolean isBike() {
    return bike;
  }

  /**
   * Sets bike for this instance.
   */
  public void setBike(boolean bike) {
    this.bike = bike;
  }

  /**
   * Returns horse for this instance.
   */
  public boolean isHorse() {
    return horse;
  }

  /**
   * Sets horse for this instance.
   */
  public void setHorse(boolean horse) {
    this.horse = horse;
  }

  /**
   * Returns dog for this instance.
   */
  public boolean isDog() {
    return dog;
  }

  /**
   * Sets dog for this instance.
   */
  public void setDog(boolean dog) {
    this.dog = dog;
  }
}
