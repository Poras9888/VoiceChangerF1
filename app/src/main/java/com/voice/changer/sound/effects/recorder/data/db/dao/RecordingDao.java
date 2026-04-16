package com.voice.changer.sound.effects.recorder.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;

import java.util.List;

@Dao
public interface RecordingDao {
    @Query("SELECT * FROM recordings ORDER BY createdAt DESC")
    LiveData<List<RecordingEntity>> getAllRecordings();

    @Query("SELECT * FROM recordings ORDER BY createdAt DESC")
    List<RecordingEntity> getAllRecordingsSync();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecordingEntity recording);

    @Delete
    void delete(RecordingEntity recording);

    @Query("DELETE FROM recordings")
    void deleteAll();

    @Query("UPDATE recordings SET isFavorite = :fav WHERE id = :id")
    void setFavorite(String id, boolean fav);

    @Query("SELECT * FROM recordings ORDER BY durationMs ASC")
    LiveData<List<RecordingEntity>> getAllSortedByDuration();

    @Query("SELECT * FROM recordings ORDER BY fileName ASC")
    LiveData<List<RecordingEntity>> getAllSortedByName();
}
