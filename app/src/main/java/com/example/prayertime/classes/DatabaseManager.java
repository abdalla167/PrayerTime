
package com.example.prayertime.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseManager extends SQLiteOpenHelper {

	@SuppressLint("SdCardPath")
	private static String DB_PATH = "/data/data/com.sally/databases/";
	private static String DB_NAME = "sally_db.db";
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	public DatabaseManager(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;

		if(Build.VERSION.SDK_INT >= 17){
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
		}
		else
		{
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		}
	}

	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		} else {

			this.getReadableDatabase();

		}
	}

	private boolean checkDataBase() {
		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}

	private void copyDataBase() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void openDataBase() throws SQLException {
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			db.disableWriteAheadLogging();
		}
	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			File database = new File(db.getPath());
			database.delete();
		} catch (Exception e) {
		}
	}

}
