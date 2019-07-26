package edu.cnm.deepdive.abqtrailsclientside.model.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import edu.cnm.deepdive.abqtrailsclientside.model.entity.Trail;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TrailDao_Impl implements TrailDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTrail;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfTrail;

  public TrailDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTrail = new EntityInsertionAdapter<Trail>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Trail`(`trail_id`,`trail_name`,`trail_length`,`trail_rating`,`trail_head_coordinates`,`bike_trail`,`horse_trail`,`dogs_allowed`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Trail value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindDouble(3, value.getLength());
        stmt.bindDouble(4, value.getRating());
        stmt.bindDouble(5, value.getCoordinates());
        final int _tmp;
        _tmp = value.isBike() ? 1 : 0;
        stmt.bindLong(6, _tmp);
        final int _tmp_1;
        _tmp_1 = value.isHorse() ? 1 : 0;
        stmt.bindLong(7, _tmp_1);
        final int _tmp_2;
        _tmp_2 = value.isDog() ? 1 : 0;
        stmt.bindLong(8, _tmp_2);
      }
    };
    this.__deletionAdapterOfTrail = new EntityDeletionOrUpdateAdapter<Trail>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Trail` WHERE `trail_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Trail value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public long insert(final Trail trail) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfTrail.insertAndReturnId(trail);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final Trail... trail) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfTrail.handleMultiple(trail);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Trail>> getAll() {
    final String _sql = "SELECT * FROM Trail";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Trail"}, false, new Callable<List<Trail>>() {
      @Override
      public List<Trail> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_name");
          final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_length");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_rating");
          final int _cursorIndexOfCoordinates = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_head_coordinates");
          final int _cursorIndexOfBike = CursorUtil.getColumnIndexOrThrow(_cursor, "bike_trail");
          final int _cursorIndexOfHorse = CursorUtil.getColumnIndexOrThrow(_cursor, "horse_trail");
          final int _cursorIndexOfDog = CursorUtil.getColumnIndexOrThrow(_cursor, "dogs_allowed");
          final List<Trail> _result = new ArrayList<Trail>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Trail _item;
            _item = new Trail();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final double _tmpLength;
            _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
            _item.setLength(_tmpLength);
            final double _tmpRating;
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
            _item.setRating(_tmpRating);
            final double _tmpCoordinates;
            _tmpCoordinates = _cursor.getDouble(_cursorIndexOfCoordinates);
            _item.setCoordinates(_tmpCoordinates);
            final boolean _tmpBike;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfBike);
            _tmpBike = _tmp != 0;
            _item.setBike(_tmpBike);
            final boolean _tmpHorse;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfHorse);
            _tmpHorse = _tmp_1 != 0;
            _item.setHorse(_tmpHorse);
            final boolean _tmpDog;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfDog);
            _tmpDog = _tmp_2 != 0;
            _item.setDog(_tmpDog);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Trail findById(final Long id) {
    final String _sql = "SELECT * FROM Trail WHERE trail_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, id);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_name");
      final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_length");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_rating");
      final int _cursorIndexOfCoordinates = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_head_coordinates");
      final int _cursorIndexOfBike = CursorUtil.getColumnIndexOrThrow(_cursor, "bike_trail");
      final int _cursorIndexOfHorse = CursorUtil.getColumnIndexOrThrow(_cursor, "horse_trail");
      final int _cursorIndexOfDog = CursorUtil.getColumnIndexOrThrow(_cursor, "dogs_allowed");
      final Trail _result;
      if(_cursor.moveToFirst()) {
        _result = new Trail();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _result.setName(_tmpName);
        final double _tmpLength;
        _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
        _result.setLength(_tmpLength);
        final double _tmpRating;
        _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
        _result.setRating(_tmpRating);
        final double _tmpCoordinates;
        _tmpCoordinates = _cursor.getDouble(_cursorIndexOfCoordinates);
        _result.setCoordinates(_tmpCoordinates);
        final boolean _tmpBike;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfBike);
        _tmpBike = _tmp != 0;
        _result.setBike(_tmpBike);
        final boolean _tmpHorse;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfHorse);
        _tmpHorse = _tmp_1 != 0;
        _result.setHorse(_tmpHorse);
        final boolean _tmpDog;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfDog);
        _tmpDog = _tmp_2 != 0;
        _result.setDog(_tmpDog);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Trail search(final String searchString) {
    final String _sql = "SELECT * FROM Trail WHERE trail_name LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (searchString == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, searchString);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_name");
      final int _cursorIndexOfLength = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_length");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_rating");
      final int _cursorIndexOfCoordinates = CursorUtil.getColumnIndexOrThrow(_cursor, "trail_head_coordinates");
      final int _cursorIndexOfBike = CursorUtil.getColumnIndexOrThrow(_cursor, "bike_trail");
      final int _cursorIndexOfHorse = CursorUtil.getColumnIndexOrThrow(_cursor, "horse_trail");
      final int _cursorIndexOfDog = CursorUtil.getColumnIndexOrThrow(_cursor, "dogs_allowed");
      final Trail _result;
      if(_cursor.moveToFirst()) {
        _result = new Trail();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _result.setName(_tmpName);
        final double _tmpLength;
        _tmpLength = _cursor.getDouble(_cursorIndexOfLength);
        _result.setLength(_tmpLength);
        final double _tmpRating;
        _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
        _result.setRating(_tmpRating);
        final double _tmpCoordinates;
        _tmpCoordinates = _cursor.getDouble(_cursorIndexOfCoordinates);
        _result.setCoordinates(_tmpCoordinates);
        final boolean _tmpBike;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfBike);
        _tmpBike = _tmp != 0;
        _result.setBike(_tmpBike);
        final boolean _tmpHorse;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfHorse);
        _tmpHorse = _tmp_1 != 0;
        _result.setHorse(_tmpHorse);
        final boolean _tmpDog;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfDog);
        _tmpDog = _tmp_2 != 0;
        _result.setDog(_tmpDog);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
