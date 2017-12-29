package fr.epita.android.pri;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import fr.epita.android.pri.Tools.PasswordFunctions;

/**
 * Created by Rohit on 11/3/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="pri-v1.db";
    private static final String TABLE_NAME="customer";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_EMAIL="email";
    private static final String COLUMN_LOGIN="login";
    private static final String COLUMN_MOBILE="mobile";
    private static final String COLUMN_PASS="pass";
    private static final String COLUMN_SEXE = "sexe";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_PICTURE="picture";

    private static final String TABLE_CREATE="create table "+ TABLE_NAME +" (id integer primary key autoincrement," +
            " name text, email text not null, login text not null," +
            " pass text not null,"+
            " mobile text not null," +
            " sexe int not null," +
            " dob date text null," +
            " picture text);";

    SQLiteDatabase db;

public DatabaseHandler(Context context)
{
    super(context,DATABASE_NAME,null,DATABASE_VERSION);
//    SQLiteDatabase db=this.getWritableDatabase();
    Log.d("Database Handler Cons","Get Writable DB");
}

    @Override
    public void onCreate(SQLiteDatabase db) {
    Log.d("Create DB","Pahunch gaya yahan");
        db.execSQL(TABLE_CREATE);
        Log.d("Database Operation","Database is created successfully");
    }

    public void insertdata(Relation r)
    {
        db= this.getWritableDatabase();
        String qry="select * from "+TABLE_NAME;
        //Cursor cursor=db.rawQuery(qry,null);
        //int count=cursor.getCount();


        ContentValues val = new ContentValues();
        //val.put(COLUMN_ID,count);
        val.put(COLUMN_NAME,r.getName());
        val.put(COLUMN_EMAIL,r.getEmail());
        val.put(COLUMN_LOGIN,r.getLogin());
        val.put(COLUMN_MOBILE,r.getMob());
        val.put(COLUMN_PASS,r.getPass());
        val.put(COLUMN_SEXE,r.getSexe());
        val.put(COLUMN_DOB,r.getDob());
        val.put(COLUMN_PICTURE, (r.getUri() == null) ? "" : r.getUri().toString());

    db.insert(TABLE_NAME,null,val);
        Log.d("Database Operation","Values inserted successfully");
    //cursor.close();
    db.close();
    }

    /*
     * Return new relation associated to the user
     */

    public Relation getRelation(String login)
    {
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE login = ?";
        Cursor cursor = db.rawQuery(query, new String[]{login});
        if (cursor.moveToFirst())
        {
            Relation rl = new Relation();
            rl.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            rl.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            rl.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            rl.setLogin(cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN)));
            rl.setMob(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE)));
            rl.setSexe(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SEXE))));
            rl.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_DOB)));
            rl.setUri(Uri.parse(cursor.getString(cursor.getColumnIndex(COLUMN_PICTURE))));
            cursor.close();
            db.close();
            return rl;
        }
        else
        {
            cursor.close();
            db.close();
            return null;
        }
    }

    /*
     * Return the password associated to the login loginuser
     */
    public String searchPass(String loginuser)
    {
        db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_PASS + " FROM " + TABLE_NAME + " WHERE " + COLUMN_LOGIN + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{loginuser});
        if(cursor.moveToFirst())
        {
                query = cursor.getString(cursor.getColumnIndex(COLUMN_PASS));
                cursor.close();
                db.close();
                return query;
        }
        cursor.close();
        db.close();
        return null;
    }

    /*
      * Return the login associated to the address mail
     */
    public String searchLoginByMail(String mail_addr)
    {
        db = this.getReadableDatabase();
        String find_login = "SELECT " + COLUMN_LOGIN + " FROM " + TABLE_NAME + " WHERE email = ?";
        Cursor cursor  = db.rawQuery(find_login, new String[]{mail_addr});
        if (cursor.moveToFirst()) {
            find_login = cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN));
            cursor.close();
            db.close();
            return find_login;
        }
        cursor.close();
        db.close();
        return null;
    }

     /*
      * Return the mobile associated to the address mail
     */
    public String searchMobileByMail(String mail_addr)
    {
        db = this.getReadableDatabase();
        String find_mobile = "SELECT " + COLUMN_MOBILE + " FROM " + TABLE_NAME + " WHERE email = ?";
        Cursor cursor = db.rawQuery(find_mobile, new String[]{mail_addr});
        if (cursor.moveToFirst())
        {
                find_mobile = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE));
                cursor.close();
                db.close();
                return find_mobile;
        }
        cursor.close();
        db.close();
        return null;
    }

    /*
     * Change the password associated to the login
    */
    public void changePass(String pass, String login)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASS, PasswordFunctions.hashPass(pass, login));
        db.update(TABLE_NAME, values, COLUMN_LOGIN + " = ?", new String[]{login});
        db.close();
    }

    /*
     * Change the picture associated to the login
    */
    public void changePicture(String uri, String login)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PICTURE, uri);
        db.update(TABLE_NAME, values, COLUMN_LOGIN + " = ?", new String[]{login});
        db.close();
    }

    /*
     * Change the mail associated to the login
     */
    public void changeMail(String mail, String login)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, mail);
        db.update(TABLE_NAME, values, COLUMN_LOGIN + " = ?", new String[]{login});
        db.close();
    }

    /*
     * Change the mobile number associated to the mail
     */
    public void changeMobile(String mobile, String login)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOBILE, mobile);
        db.update(TABLE_NAME, values, COLUMN_LOGIN + " = ?", new String[]{login});
        db.close();
    }


    public Cursor getAllData()
    {
        Log.d("Its in the ALl data","ALl data me aa gaya");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

  @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String query= "DROP TABLE IF EXISTS "+ TABLE_NAME;
    db.execSQL(query);
    onCreate(db);
    }


}
