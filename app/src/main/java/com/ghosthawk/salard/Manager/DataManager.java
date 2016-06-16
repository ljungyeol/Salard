package com.ghosthawk.salard.Manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;


import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.Message;
import com.ghosthawk.salard.MyApplication;
import com.ghosthawk.salard.R;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dongja94 on 2016-05-17.
 */
public class DataManager extends SQLiteOpenHelper {
    private static DataManager instance;
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    private static final String DB_NAME = "chat";
    private static final int DB_VERSION = 1;

    private DataManager() {
        super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
//        DateTime.now().getMillis();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DataConstant.ChatUserTable.TABLE_NAME + "(" +
                DataConstant.ChatUserTable.COLUMN_MEM_ID + " TEXT PRIMARY KEY," +
                DataConstant.ChatUserTable.COLUMN_MEM_NAME + " TEXT," +
                DataConstant.ChatUserTable.COLUMN_MEM_PICTURE + " TEXT," +
                DataConstant.ChatUserTable.COLUMN_MEM_SELL_COUNT + " INTEGER,"+
                DataConstant.ChatUserTable.COLUMN_DATE +" TEXT," +
                DataConstant.ChatUserTable.COLUMN_LAST_MESSAGE +" TEXT);";
        db.execSQL(query);

        query = "CREATE TABLE " + DataConstant.ChatTable.TABL_NAME + "(" +
                DataConstant.ChatTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataConstant.ChatTable.COLUMN_PACKAGE_ID + " INTEGER," +
                DataConstant.ChatTable.COLUMN_USER_ID + " TEXT," +
                DataConstant.ChatTable.COLUMN_TYPE + " INTEGER," +
                DataConstant.ChatTable.COLUMN_MESSAGE + " TEXT," +
                DataConstant.ChatTable.COLUMN_DATE + " TEXT,"+
                DataConstant.ChatTable.COLUMN_MEM_PICTURE + " TEXT," +
                DataConstant.ChatTable.COLUMN_MEM_SELL_COUNT + " INTEGER,"+
                DataConstant.ChatTable.COLUMN_READ +" INTEGER);";
        db.execSQL(query);
    }






    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //--TODO _ID와 MEM_ID를 비교해 일치하는 ID가 잇으면 MEM_ID를 반환
    public static final String INVALID_ID = "";
    public boolean getChatUserId(String senderid) {

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {DataConstant.ChatUserTable.COLUMN_MEM_ID};
        String selection = DataConstant.ChatUserTable.COLUMN_MEM_ID + " = ?";
        String[] selectionArgs = {senderid};
        Cursor c = db.query(DataConstant.ChatUserTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (!c.moveToNext()) {
            c.close();
            return false;
        }

        c.close();
        return true;
    }

    ContentValues values = new ContentValues();
    public String getUserTableId(Message message) {
        boolean id = getChatUserId(message.member.mem_id);
        SQLiteDatabase db = getWritableDatabase();

        if (id){
            values.clear();
            String whereClause = "id = ?";
            String[] whereArgs = new String[] {message.member.mem_id};
//            values.put(DataConstant.ChatUserTable.COLUMN_MEM_ID,message.member.mem_id);
            values.put(DataConstant.ChatUserTable.COLUMN_MEM_NAME,message.member.mem_name);
            values.put(DataConstant.ChatUserTable.COLUMN_MEM_PICTURE,message.member.mem_picture);
            values.put(DataConstant.ChatUserTable.COLUMN_MEM_SELL_COUNT,message.member.mem_sellcount);
            values.put(DataConstant.ChatUserTable.COLUMN_DATE,message.msg_date);
            values.put(DataConstant.ChatUserTable.COLUMN_LAST_MESSAGE,message.msg_content);
            db.update(DataConstant.ChatUserTable.TABLE_NAME, values,whereClause,whereArgs);
            return message.member.mem_id;
        }

        values.clear();
        values.put(DataConstant.ChatUserTable.COLUMN_MEM_ID,message.member.mem_id);
        values.put(DataConstant.ChatUserTable.COLUMN_MEM_NAME,message.member.mem_name);
        values.put(DataConstant.ChatUserTable.COLUMN_MEM_PICTURE,message.member.mem_picture);
        values.put(DataConstant.ChatUserTable.COLUMN_MEM_SELL_COUNT,message.member.mem_sellcount);
        values.put(DataConstant.ChatUserTable.COLUMN_DATE,message.msg_date);
        values.put(DataConstant.ChatUserTable.COLUMN_LAST_MESSAGE,message.msg_content);
        db.insert(DataConstant.ChatUserTable.TABLE_NAME, null, values);
        return message.member.mem_id;
    }

    public String getLastDate(String serverid) {
//        String id = getChatUserId(serverid);
//        if (id == INVALID_ID) return null;

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {DataConstant.ChatTable.COLUMN_DATE};
        String selection = DataConstant.ChatTable.COLUMN_TYPE + " = 2";
//        String[] selectionArgs = {serverid};
        String orderBy = DataConstant.ChatTable.COLUMN_DATE + " DESC";
        String limit = "1";
        Cursor c = db.query(DataConstant.ChatTable.TABL_NAME, columns, selection, null, null, null, orderBy, limit);
        if (!c.moveToNext()) {
            c.close();
            return null;
        }
        String date = c.getString(c.getColumnIndex(DataConstant.ChatTable.COLUMN_DATE));
        c.close();
        return date;
    }

    public long addChatMessage(String id,Member member, int msg_packagenum, int type, String message, String date) {
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DataConstant.ChatTable.COLUMN_USER_ID, id);
        values.put(DataConstant.ChatTable.COLUMN_PACKAGE_ID,msg_packagenum);
        values.put(DataConstant.ChatTable.COLUMN_TYPE, type);
        values.put(DataConstant.ChatTable.COLUMN_MESSAGE, message);

        if (!TextUtils.isEmpty(date)) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                Date a = formatter.parse(date);

                date = convertDateString(a);
            }catch (Exception e){

            }

        }

        values.put(DataConstant.ChatTable.COLUMN_DATE, date);
        values.put(DataConstant.ChatTable.COLUMN_MEM_PICTURE,member.mem_picture);
        values.put(DataConstant.ChatTable.COLUMN_MEM_SELL_COUNT,member.mem_sellcount);
        values.put(DataConstant.ChatTable.COLUMN_READ,0);
        return db.insert(DataConstant.ChatTable.TABL_NAME, null, values);
    }

//    public Cursor getChatUserList() {
//        String[] columns = {DataConstant.ChatUserTable._ID, DataConstant.ChatUserTable.COLUMN_MEM_ID,
//                DataConstant.ChatUserTable.COLUMN_MEM_NAME, DataConstant.ChatUserTable.COLUMN_MEM_PICTURE,
//                DataConstant.ChatUserTable.COLUMN_MEM_SELL_COUNT,DataConstant.ChatUserTable.COLUMN_LAST_MESSAGE};
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor c = db.query(DataConstant.ChatUserTable.TABLE_NAME, columns, null, null, null, null, null);
//        return c;
//    }

    public List<Message> getChatUserList() {
        List<Message> messages = new ArrayList<>();
        String[] columns = {DataConstant.ChatUserTable.COLUMN_MEM_ID, DataConstant.ChatUserTable.COLUMN_MEM_NAME, DataConstant.ChatUserTable.COLUMN_MEM_PICTURE,
                DataConstant.ChatUserTable.COLUMN_MEM_SELL_COUNT, DataConstant.ChatUserTable.COLUMN_DATE, DataConstant.ChatUserTable.COLUMN_LAST_MESSAGE};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(DataConstant.ChatUserTable.TABLE_NAME, columns, null, null, null, null, null);
        while(c.moveToNext()){
            Message message = new Message();
            message.member.mem_id = c.getString(c.getColumnIndex(DataConstant.ChatUserTable.COLUMN_MEM_ID));
            message.member.mem_name = c.getString(c.getColumnIndex(DataConstant.ChatUserTable.COLUMN_MEM_NAME));
            message.member.mem_picture = c.getString(c.getColumnIndex(DataConstant.ChatUserTable.COLUMN_MEM_PICTURE));
            message.member.mem_sellcount = c.getInt(c.getColumnIndex(DataConstant.ChatUserTable.COLUMN_MEM_SELL_COUNT));
            message.msg_date = c.getString(c.getColumnIndex(DataConstant.ChatUserTable.COLUMN_DATE));
            if (!TextUtils.isEmpty(message.msg_date)) {
                SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                SimpleDateFormat new_format = new SimpleDateFormat("HH:mm");
                try{
                    Date a = original_format.parse(message.msg_date);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(a);
                    cal.add(Calendar.HOUR,9);
                    a = cal.getTime();
                    String new_date = new_format.format(a);

                    message.msg_date = new_date;
                }catch (Exception e){
                }
            }

            message.msg_content = c.getString(c.getColumnIndex(DataConstant.ChatUserTable.COLUMN_LAST_MESSAGE));
            messages.add(message);
        }
        return messages;
    }

    public void deleteMessage(String userid){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = DataConstant.ChatTable.COLUMN_USER_ID + " = ?";
        String[] whereArgs = new String[] {userid};
        db.delete(DataConstant.ChatTable.TABL_NAME,whereClause,whereArgs);

        SQLiteDatabase db1 = getWritableDatabase();
        String whereClause1 = DataConstant.ChatUserTable.COLUMN_MEM_ID + " = ?";
        String[] whereArgs1 = new String[] {userid};
        db.delete(DataConstant.ChatUserTable.TABLE_NAME,whereClause1,whereArgs1);




    }

    public List<Message> getChatList(String userid) {
        List<Message> messages = new ArrayList<>();
        String[] columns = {DataConstant.ChatTable.COLUMN_USER_ID, DataConstant.ChatTable.COLUMN_PACKAGE_ID, DataConstant.ChatTable.COLUMN_TYPE,DataConstant.ChatTable.COLUMN_MESSAGE,DataConstant.ChatTable.COLUMN_DATE,DataConstant.ChatTable.COLUMN_MEM_PICTURE,DataConstant.ChatTable.COLUMN_MEM_SELL_COUNT,DataConstant.ChatTable.COLUMN_READ};
        String selection = DataConstant.ChatTable.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {userid};
        String orderBy = DataConstant.ChatTable.COLUMN_DATE + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(DataConstant.ChatTable.TABL_NAME, columns, selection, selectionArgs, null, null, orderBy);

        while(c.moveToNext()){
            Message message = new Message();
            message.member.mem_name = c.getString(c.getColumnIndex(DataConstant.ChatTable.COLUMN_USER_ID));
            message.msg_packagenum = c.getInt(c.getColumnIndex(DataConstant.ChatTable.COLUMN_PACKAGE_ID));
            message.type = c.getInt(c.getColumnIndex(DataConstant.ChatTable.COLUMN_TYPE));
            message.msg_content = c.getString(c.getColumnIndex(DataConstant.ChatTable.COLUMN_MESSAGE));
            message.msg_date = c.getString(c.getColumnIndex(DataConstant.ChatTable.COLUMN_DATE));
            if (!TextUtils.isEmpty(message.msg_date)) {
                SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                SimpleDateFormat new_format = new SimpleDateFormat("HH:mm");
                try{
                    Date a = original_format.parse(message.msg_date);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(a);
                    cal.add(Calendar.HOUR,9);
                    a = cal.getTime();
                    String new_date = new_format.format(a);

                    message.msg_date = new_date;
                }catch (Exception e){
                }
            }
            message.member.mem_picture = c.getString(c.getColumnIndex(DataConstant.ChatTable.COLUMN_MEM_PICTURE));
            message.member.mem_sellcount = c.getInt(c.getColumnIndex(DataConstant.ChatTable.COLUMN_MEM_SELL_COUNT));
            message.msg_read = c.getInt(c.getColumnIndex(DataConstant.ChatTable.COLUMN_READ));

            messages.add(message);
        }
        values.clear();
        String whereClause = "uid = ?";
        String[] whereArgs = new String[] {userid};
//            values.put(DataConstant.ChatUserTable.COLUMN_MEM_ID,message.member.mem_id);
        values.put(DataConstant.ChatTable.COLUMN_READ,1);
        db.update(DataConstant.ChatTable.TABL_NAME, values,whereClause,whereArgs);




        return messages;
    }

//    public Cursor getChatList(String userid) {
//        String[] columns = {DataConstant.ChatTable._ID, DataConstant.ChatTable.COLUMN_MESSAGE, DataConstant.ChatTable.COLUMN_TYPE, DataConstant.ChatTable.COLUMN_DATE,DataConstant.ChatTable.COLUMN_MEM_PICTURE,DataConstant.ChatTable.COLUMN_MEM_SELL_COUNT,DataConstant.ChatTable.COLUMN_READ};
//        String selection = DataConstant.ChatTable.COLUMN_USER_ID + " = ?";
//        String[] selectionArgs = {userid};
//        String orderBy = DataConstant.ChatTable.COLUMN_DATE + " ASC";
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor c = db.query(DataConstant.ChatTable.TABL_NAME, columns, selection, selectionArgs, null, null, orderBy);
//        return c;
//    }





    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public String convertDateString(Date date) {
        return dateFormat.format(date);
    }


}
