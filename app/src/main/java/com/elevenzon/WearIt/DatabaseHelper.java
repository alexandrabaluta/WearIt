package com.elevenzon.WearIt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public int lastTableImageID = 0;

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "test.db";
    // User table name
    private static final String TABLE_USER = "users";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"  + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private static final String TABLE_IMAGE = "storedImages";
    private static final String COLUMN_IMAGE_ID = "imageid";
    private static final String COLUMN_IMAGE_ITEM = "myImage";
    private static final String COLUMN_IMAGE_SEASON="season";
    private static final String COLUMN_IMAGE_COLOR="color";
    private static final String COLUMN_IMAGE_TYPE="type";
    private static final String COLUMN_IMAGE_PATTERN="pattern";


   private String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE +"(" + COLUMN_IMAGE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_IMAGE_ITEM + " BLOB,"+
            COLUMN_IMAGE_SEASON+" TEXT,"+COLUMN_IMAGE_COLOR+" TEXT,"+ COLUMN_IMAGE_TYPE+" TEXT," + COLUMN_IMAGE_PATTERN+" TEXT"+")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_IMAGE_TABLE);
        db.execSQL("INSERT INTO users(email, password) values ('alexandra@yahoo.com','admin1') ");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        // Create tables again
        onCreate(db);
    }

    /**
     * method to create image record
     * @param bmp the bitmap of the image
     */
    public void addImage(Bitmap bmp){
        byte[] data = getBitmapAsByteArray(bmp);


        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL(CREATE_IMAGE_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGE);

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_IMAGE_ITEM, data);
//        cv.put(COLUMN_IMAGE_COLOR, tag_color);
//        cv.put(COLUMN_IMAGE_PATTERN, tag_pattern);
//        cv.put(COLUMN_IMAGE_TYPE, tag_type);
//        cv.put(COLUMN_IMAGE_SEASON, tag_season);
        db.insert(TABLE_IMAGE, null, cv);
        lastTableImageID++;
        db.close();
        Log.d("testingTag", "Added to database!");
    }

    public void addTagsToImage(String tag_color, String tag_pattern, String tag_type, String tag_season){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_IMAGE_SEASON, tag_season);
        cv.put(COLUMN_IMAGE_COLOR, tag_color);
        cv.put(COLUMN_IMAGE_TYPE, tag_type);
        cv.put(COLUMN_IMAGE_PATTERN, tag_pattern);

        db.update(TABLE_IMAGE,cv,COLUMN_IMAGE_ID + " = ?", new String[]{String.valueOf(lastTableImageID)});

        db.close();
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * method to retrieve image from DB by id
     * @param i id of image in table
     * @return bitmap of image
     */
    public Bitmap getImage(int i){

        SQLiteDatabase db = this.getWritableDatabase();
        String qu = "SELECT "+COLUMN_IMAGE_ITEM+" FROM "+ TABLE_IMAGE +" WHERE "+COLUMN_IMAGE_ID +"=" + i ;
        Cursor cur = db.rawQuery(qu, null);

        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(0);
            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null ;
    }

    public Bitmap getMatch(int i, String color){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor curs = db.rawQuery("SELECT myImage FROM  storedImages   where imageid="+i+" AND color=?"  , new String [] {color});

        if (curs.moveToFirst()){
            byte[] imgByte = curs.getBlob(0);
            curs.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (curs != null && !curs.isClosed()) {
            curs.close();
        }

        return null ;
    }

    public Bitmap getMatchPattern(int i, String pattern){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor curs = db.rawQuery("SELECT myImage FROM  storedImages   where imageid="+i+" AND pattern=?"  , new String [] {pattern});

        if (curs.moveToFirst()){
            byte[] imgByte = curs.getBlob(0);
            curs.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (curs != null && !curs.isClosed()) {
            curs.close();
        }

        return null ;
    }

    public Bitmap getMatchSeason(int i, String season){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor curs = db.rawQuery("SELECT myImage FROM  storedImages   where imageid="+i+" AND season=?"  , new String [] {season});

        if (curs.moveToFirst()){
            byte[] imgByte = curs.getBlob(0);
            curs.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (curs != null && !curs.isClosed()) {
            curs.close();
        }

        return null ;
    }

    public Bitmap getMatchType(int i, String type){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor curs = db.rawQuery("SELECT myImage FROM  storedImages WHERE imageid="+i+" and type=? LIMIT 1"  , new String [] {type});

        if (curs.moveToFirst()){
            byte[] imgByte = curs.getBlob(0);
            curs.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (curs != null && !curs.isClosed()) {
            curs.close();
        }

        return null ;
    }


    public StringBuilder getTagsAtID(int i){
        StringBuilder res = new StringBuilder();
        SQLiteDatabase db = this.getWritableDatabase();
        //we use the sql statement SELECT color, pattern, type, season FROM TABLE_IMAGE WHERE COLUMN_IMAGE_ID = i
        String[] columns = {
                COLUMN_IMAGE_COLOR,
                COLUMN_IMAGE_PATTERN,
                COLUMN_IMAGE_TYPE,
                COLUMN_IMAGE_SEASON
        };
        String selection = COLUMN_IMAGE_ID + " = ?";

        String selectionArgs[] = {String.valueOf(i)};

        Cursor cursor = db.query(TABLE_IMAGE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            res.append(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_COLOR)));
            res.append(", ");
            res.append(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATTERN)));
            res.append(", ");
            res.append(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_TYPE)));
            res.append(", ");
            res.append(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_SEASON)));
        }
        cursor.close();
        db.close();

        //return tag list
        return res;
    }
    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_EMAIL + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return userList;
    }
    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }


}