package com.ktiger.crete.model;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static androidx.room.ForeignKey.CASCADE;


@Entity(foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "category_id",
        onDelete = CASCADE))
public class Memo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "contents")
    private String contents;
    @ColumnInfo(name = "time")
    private String time;
    @ColumnInfo(name = "category_id")
    private int category_id;

    public Memo(String title, String contents, int category_id) {
        this.title = title;
        this.contents = contents;
        this.fillTime();
        this.category_id = category_id;
    }

    public Memo() { }

    public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void fillTime() {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat date = new SimpleDateFormat("yy/MM/dd");
        SimpleDateFormat time = new SimpleDateFormat("hh:mm");

        String dateStr = date.format(System.currentTimeMillis());
        String timeStr = time.format(System.currentTimeMillis());

        if (cal.get(Calendar.AM_PM) == 0)
            this.time =  dateStr + " 오전 " + timeStr;
        else
            this.time =  dateStr + " 오후 " + timeStr;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @NonNull
    @Override
    public String toString() {
        return id + "\n" + title + "\n" + contents + "\n" + time + "\n" + category_id;
    }

}

