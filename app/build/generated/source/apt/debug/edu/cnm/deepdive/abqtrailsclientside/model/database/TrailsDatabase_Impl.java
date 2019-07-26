package edu.cnm.deepdive.abqtrailsclientside.model.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import edu.cnm.deepdive.abqtrailsclientside.model.dao.TrailDao;
import edu.cnm.deepdive.abqtrailsclientside.model.dao.TrailDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TrailsDatabase_Impl extends TrailsDatabase {
  private volatile TrailDao _trailDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Trail` (`trail_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `trail_name` TEXT, `trail_length` REAL NOT NULL, `trail_rating` REAL NOT NULL, `trail_head_coordinates` REAL NOT NULL, `bike_trail` INTEGER NOT NULL, `horse_trail` INTEGER NOT NULL, `dogs_allowed` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '6c775f3f05fd5f5d304f6a90cd9d2919')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Trail`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTrail = new HashMap<String, TableInfo.Column>(8);
        _columnsTrail.put("trail_id", new TableInfo.Column("trail_id", "INTEGER", true, 1));
        _columnsTrail.put("trail_name", new TableInfo.Column("trail_name", "TEXT", false, 0));
        _columnsTrail.put("trail_length", new TableInfo.Column("trail_length", "REAL", true, 0));
        _columnsTrail.put("trail_rating", new TableInfo.Column("trail_rating", "REAL", true, 0));
        _columnsTrail.put("trail_head_coordinates", new TableInfo.Column("trail_head_coordinates", "REAL", true, 0));
        _columnsTrail.put("bike_trail", new TableInfo.Column("bike_trail", "INTEGER", true, 0));
        _columnsTrail.put("horse_trail", new TableInfo.Column("horse_trail", "INTEGER", true, 0));
        _columnsTrail.put("dogs_allowed", new TableInfo.Column("dogs_allowed", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrail = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTrail = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrail = new TableInfo("Trail", _columnsTrail, _foreignKeysTrail, _indicesTrail);
        final TableInfo _existingTrail = TableInfo.read(_db, "Trail");
        if (! _infoTrail.equals(_existingTrail)) {
          throw new IllegalStateException("Migration didn't properly handle Trail(edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail).\n"
                  + " Expected:\n" + _infoTrail + "\n"
                  + " Found:\n" + _existingTrail);
        }
      }
    }, "6c775f3f05fd5f5d304f6a90cd9d2919", "cfbaf32b76d098f15385983b0e72e22d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Trail");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Trail`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public TrailDao trailDao() {
    if (_trailDao != null) {
      return _trailDao;
    } else {
      synchronized(this) {
        if(_trailDao == null) {
          _trailDao = new TrailDao_Impl(this);
        }
        return _trailDao;
      }
    }
  }
}
