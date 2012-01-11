package com.korsakopf.caloriecounter.database;

import java.sql.Date;
import java.util.Calendar;
import java.util.ArrayList; 

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.DatePicker;

public class CalorieCounterDbAdapter {
	int id = 0;
	private static final String TAG = "DBAdapter";

	public static final String DB_NAME = "CalorieCounterdatabase.db";
	public static final int DB_VERSION = 10;

	public static final String TABLE = "nutvalues";
	public static final String C_ID = "_id"; // special for ID
	public static final String C_DATE = "date";
	public static final String C_NAME = "name";
	public static final String C_GRAM = "gram";
	public static final String C_CAL = "calories";
	public static final String C_PROT = "protien";

	// SQL statement voor het aanmaken van de database
	private static final String DATABASE_CREATE = "create table " + TABLE + "("
			+ C_ID + " integer primary key autoincrement, " + C_DATE + " integer,"
			+ C_NAME + " text," + C_GRAM + " integer," + C_CAL + " integer,"
			+ C_PROT + " integer);";

	//
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public CalorieCounterDbAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS nutvalues");
			onCreate(db);
		}
	}

	// open database
	public CalorieCounterDbAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// database dicht doen
	public void close() {
		DBHelper.close();
	}

	// Meerdere items in een array gooien ofzo
	private ContentValues createContentValues(int date, String name,
			double gram, double calories, double protien) {
		ContentValues values = new ContentValues();
		values.put(C_DATE, date);
		values.put(C_NAME, name);
		values.put(C_GRAM, gram);
		values.put(C_CAL, calories);
		values.put(C_PROT, protien);
		return values;
	}

	// Waardes toevoegen aan de nutvalues tabel
	public long insertMultiple(int date, String name, double gram,
			double calories, double protien) {
		ContentValues values = createContentValues(date, name, gram, calories*gram/100,
				protien*gram/100);
		return db.insert(TABLE, null, values);
	}

   // Telt het aantal toegevoegde rijen om dit in een toast weer te geven
	public int getAllEntries() {
		Cursor cursor = db.rawQuery("SELECT COUNT(_id) FROM nutvalues", null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(0);
		}
		return cursor.getInt(0);
	}

	
	// Telt het aantal calorieen van vandaag voor in MainActivity
	public int getCalories(String stringDate) {
		Cursor cursor = db.rawQuery(
				"SELECT SUM(calories) FROM nutvalues WHERE date = '"
						+ stringDate + "'", null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(0);
		}
		return cursor.getInt(0);
	}

	// Telt het aantal eiwitten van vandaag voor in MainActivity
	public int getProtien(String stringDate) {
		Cursor cursor = db.rawQuery(
				"SELECT SUM(protien) FROM nutvalues WHERE date = '"
						+ stringDate + "'", null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(0);
		}
		return cursor.getInt(0);
	}


	
	// test 2 voor listview
	public Cursor getOverviewDate(){
		String test = "SELECT _id, date, sum(calories) as calories, sum(protien) as protien FROM nutvalues GROUP BY date";
		Cursor cursor = db.rawQuery(test, null);
		return cursor;
	}
	
	
	// Test voor grafiek -- calorieen
 	public Number[] getCalForGraph() {
		int idx = 0;		
		Cursor cursor = db.rawQuery(
				"SELECT calories FROM nutvalues", null);
		Number[] myList = new Number[cursor.getCount()];

		 if (!cursor.moveToFirst()) return myList;		 
		 myList[idx++] = cursor.getInt( 0 );
		 while (!cursor.isLast()) {
		    int val = cursor.getInt( 0 );
		    myList[idx++] = val;
		 cursor.moveToNext(); 
		 
 		 }   
		  
 	  return myList;
	}
 	
	// Test voor grafiek -- eiwitten
 	public Number[] getProtForGraph() {
		int idx = 0;		
		Cursor cursor = db.rawQuery(
				"SELECT date, sum(protien) as protien FROM nutvalues GROUP BY date", null);
		Number[] myList = new Number[cursor.getCount()];

		 if (!cursor.moveToFirst()) return myList;		 
		 myList[idx++] = cursor.getInt( 0 );
		 while (!cursor.isLast()) {
		    int val = cursor.getInt( 0 );
		    myList[idx++] = val;
		 cursor.moveToNext(); 
		 
 		 }     
 	  return myList;
	}




 	
 	
	/* TESTCODE
	public Cursor getOverviewDate(){
		String[] columns = new String[]{ C_ID, C_DATE, C_GRAM };
		Cursor cursor = db.query(TABLE, columns,
		  null, null, null, null, null);
	
		return cursor;
	}
	
	
	while (!cursor.isLast()) {
  myList[idx++] = cursor.getInt( 0 );
  myList[idx++] = cursor.getInt( 1 );
  cursor.moveToNext(); 
 }   

	
		*/
}
