package com.example.geolocator.services.db;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

import androidx.room.TypeConverter;

public class LocalDateTimeConverter {

    @TypeConverter
    public LocalDateTime fromTimestamp(Long timestamp){
        return timestamp == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.systemDefault());
        //return new LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.systemDefault());
    }

    @TypeConverter
    public Long LocalDateTimeToTimestamp(LocalDateTime localDateTime){

        return localDateTime == null ? null : localDateTime.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }

}
