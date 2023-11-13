package com.example.memegeneratorabschlussprojektgoekhankorkmaz.local;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.memegeneratorabschlussprojektgoekhankorkmaz.data.Meme;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MemeDataBaseDao_Impl implements MemeDataBaseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Meme> __insertionAdapterOfMeme;

  private final EntityDeletionOrUpdateAdapter<Meme> __deletionAdapterOfMeme;

  private final EntityDeletionOrUpdateAdapter<Meme> __updateAdapterOfMeme;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByID;

  public MemeDataBaseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMeme = new EntityInsertionAdapter<Meme>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Meme` (`id`,`url`,`box_count`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Meme value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getUrl() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUrl());
        }
        stmt.bindLong(3, value.getBox_count());
      }
    };
    this.__deletionAdapterOfMeme = new EntityDeletionOrUpdateAdapter<Meme>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Meme` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Meme value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfMeme = new EntityDeletionOrUpdateAdapter<Meme>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Meme` SET `id` = ?,`url` = ?,`box_count` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Meme value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getUrl() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUrl());
        }
        stmt.bindLong(3, value.getBox_count());
        if (value.getId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteByID = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Meme WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Meme meme, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMeme.insert(meme);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteMeme(final Meme meme, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMeme.handle(meme);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object update(final Meme meme, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMeme.handle(meme);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteByID(final String key, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByID.acquire();
        int _argIndex = 1;
        if (key == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, key);
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteByID.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Meme>> getAll() {
    final String _sql = "SELECT * FROM Meme";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Meme"}, false, new Callable<List<Meme>>() {
      @Override
      public List<Meme> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfBoxCount = CursorUtil.getColumnIndexOrThrow(_cursor, "box_count");
          final List<Meme> _result = new ArrayList<Meme>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Meme _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final int _tmpBox_count;
            _tmpBox_count = _cursor.getInt(_cursorIndexOfBoxCount);
            _item = new Meme(_tmpId,_tmpUrl,_tmpBox_count);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
