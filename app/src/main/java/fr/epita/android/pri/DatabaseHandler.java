package fr.epita.android.pri;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by Rohit on 11/3/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="users.db";
    private static final String TABLE_NAME="users";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_EMAIL="email";
    private static final String COLUMN_LOGIN="login";
    private static final String COLUMN_MOBILE="mobile";
    private static final String COLUMN_PASS="pass";
    private static final String COLUMN_CONFPASS="confpass";

    SQLiteDatabase db;

    private static final String TABLE_CREATE="create table "+ TABLE_NAME +" (id integer primary key " +
            "not null, name text not null, email text not null, login text not null," +
            " pass password not null, confpass password not null,"+
            " mobile numeric not null);";

public DatabaseHandler(Context context)
{
    super(context,DATABASE_NAME,null,DATABASE_VERSION);

}

    @Override
    public void onCreate(SQLiteDatabase db) {
       // db=openOrCreateDatabase("StudentDB", null);
       db.execSQL(TABLE_CREATE);
       this.db=db;
    }

    public void insertdata(Relation r)
    {
        db= this.getWritableDatabase();
        String qry="select * from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(qry,null);
        int count=cursor.getCount();


        ContentValues val=new ContentValues();
        val.put(COLUMN_ID,count);
        val.put(COLUMN_NAME,r.getName());
        val.put(COLUMN_EMAIL,r.getEmail());
        val.put(COLUMN_LOGIN,r.getLogin());
        val.put(COLUMN_MOBILE,r.getMob());
        val.put(COLUMN_PASS,r.getPass());
        val.put(COLUMN_CONFPASS,r.getConfpass());

    db.insert(TABLE_NAME,null,val);
    cursor.close();
    db.close();
    }

    public String searchPass(String loginuser)
    {
        db= this.getReadableDatabase();
        String query= "select login,pass from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        String a,b;
        b="Not Found";
        if(cursor.moveToFirst()) {
            do {
            a= cursor.getString(0);
            if(a.equals(loginuser))
            {
                b=cursor.getString(1);
                break;
            }
            }
            while (cursor.moveToNext());
        }
        db.close();
return b;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
String query= "DROP TABLE IF EXISTS"+ TABLE_NAME;
db.execSQL(query);
this.onCreate(db);
    }
}
