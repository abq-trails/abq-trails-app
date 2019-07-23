package edu.cnm.deepdive.abqtrailsclientside.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

}
