package inhouseproduct.androidquiz2.DB;

import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DbOps extends SQLiteOpenHelper {

	DbOps dbOpsInstance;

	static SQLiteDatabase db;
	public static String DB_PATH;
	public static String DB_NAME = "androidquiz.sqlite";
	public static Activity context = null;

	public DbOps(Activity context) {
		super(context, DB_NAME, null, 1);
		this.context = context;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packageInfo = pm.getPackageInfo(
					context.getPackageName(), 0);
			DB_PATH = "/data/data/" + packageInfo.packageName + "/databases/";
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		copyDB();
	}

	public boolean copyDB() {
		this.getReadableDatabase();
		try {
			String dbName = DB_PATH + DB_NAME;
			if (!checkDataBase()) {
				InputStream localDB = context.getAssets().open(DB_NAME);
				if (localDB != null) {
					OutputStream myOutput = new FileOutputStream(dbName);
					byte[] buffer = new byte[2048];
					int length;
					while ((length = localDB.read(buffer)) > 0) {
						myOutput.write(buffer, 0, length);
					}
					myOutput.flush();
					myOutput.close();
					localDB.close();
				} else {
					System.out.println("SQLite DB Not Found..");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static SQLiteDatabase openDB() {
		return SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	// public static boolean update(Object tableName, String where) {
	// try {
	// db = openDB();
	//
	// ContentValues values = new ContentValues();
	// Field[] fields = tableName.getClass().getDeclaredFields();
	// for (int i = 0; i < fields.length; i++) {
	// putTypedValue(values, fields[i], tableName);
	// }
	//
	// int rowsUpdated = db.update(tableName.getClass().getSimpleName(),
	// values, where, null);
	// if (rowsUpdated > 0) {
	// if (db.isOpen())
	// db.close();
	// return true;
	// } else {
	// }
	// } catch (Exception e) {
	// if (db.isOpen())
	// db.close();
	// e.printStackTrace();
	// }
	// if (db.isOpen())
	// db.close();
	// return false;
	// }

	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public static boolean createTable(Object entityClass) {
		try {
			String tableName = entityClass.getClass().getSimpleName();
			db = openDB();
			String createQ = "Create TABLE IF NOT EXISTS " + tableName;
			String columns = "(";
			Field[] fields = entityClass.getClass().getDeclaredFields();

			String delimiter = "";
			for (int i = 0; i < fields.length; i++) {
				columns += delimiter;
				columns += getSqliteColumnDeclaration(fields[i]);
				delimiter = ",";
			}
			columns += ");";
			db.execSQL(createQ + columns);
			// db.execSQL("delete from " + tableName);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static int SelectCheckEntry(String where, Object tableClass) {
		db = openDB();
		List<Object> recordsList = new ArrayList<Object>();
		Cursor cursor = null;

		int batchStatus = 0;

		try {
			String tableName = tableClass.getClass().getSimpleName();
			if (where == null || where == "")
				cursor = db.rawQuery("select * from " + tableName, null);
			else
				cursor = db.rawQuery("select * from " + tableName + " where "
						+ where, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();

				batchStatus = cursor.getInt(0);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// cursor.close();
			// db.close();
			try {
				if (db.isOpen()) {
					db.close();

				}
			} catch (Exception e2) {
				// TODO: handle exception
				if (db.isOpen()) {
					db.close();

				}
			}

		}
		return batchStatus;
	}

	public static List<Object> select(String where, Object tableClass) {
		db = openDB();
		List<Object> recordsList = new ArrayList<Object>();
		Cursor cursor = null;

		try {
			String tableName = tableClass.getClass().getSimpleName();
			if (where == null || where == "")
				cursor = db.rawQuery("select * from " + tableName, null);
			else
				cursor = db.rawQuery("select * from " + tableName + " where "
						+ where, null);

			if (cursor.getCount() > 0) {
				// cursor.moveToFirst();
				while (cursor.moveToNext()) {
					@SuppressWarnings("rawtypes")
                    Class className = Class.forName(tableClass.getClass()
							.getName());
					Object classOb = className.newInstance();
					for (int j = 0; j < cursor.getColumnCount(); j++) {
						Field field = classOb.getClass().getField(
								cursor.getColumnName(j));
						if (field.getType().getSimpleName()
								.equalsIgnoreCase("double"))
							field.setDouble(classOb, cursor.getDouble(j));
						else if (field.getType().getSimpleName()
								.equalsIgnoreCase("long"))
							field.setLong(classOb, cursor.getLong(j));
						else if (field.getType().getSimpleName()
								.equalsIgnoreCase("int"))
							field.setInt(classOb, cursor.getInt(j));
						else if (field.getType().getSimpleName()
								.equalsIgnoreCase("String"))
							field.set(classOb, cursor.getString(j));
						else if (field.getType().getName()
								.equalsIgnoreCase("[B"))
							field.set(classOb, cursor.getBlob(j));
//						else if (field.getType().getName()
//								.equalsIgnoreCase("boolean"))
//							field.set(classOb, cursor.getString(j));
					}
					recordsList.add(classOb);
				}

			} else {
				System.out.println("No Record Found in Table...");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// cursor.close();
			// db.close();
			try {
				if (db.isOpen()) {
					db.close();

				}
			} catch (Exception e2) {
				// TODO: handle exception
				if (db.isOpen()) {
					db.close();

				}
			}

			// if (!(cursor == null)) {
			// cursor = null;
			// cursor.close();
			// }

		}
		return recordsList;
	}

	public static List<Object> SelectDistnict(String where, Object tableClass) {
		db = openDB();
		List<Object> recordsList = new ArrayList<Object>();
		Cursor cursor = null;

		try {
			String tableName = tableClass.getClass().getSimpleName();
			if (where == null || where == "")
				cursor = db.rawQuery("select DISTINCT BatchNo from "
						+ tableName, null);
			else
				cursor = db.rawQuery("select * from " + tableName + " where "
						+ where, null);

			if (cursor.getCount() > 0) {
				// cursor.moveToFirst();
				while (cursor.moveToNext()) {
					@SuppressWarnings("rawtypes")
                    Class className = Class.forName(tableClass.getClass()
							.getName());
					Object classOb = className.newInstance();
					for (int j = 0; j < cursor.getColumnCount(); j++) {
						Field field = classOb.getClass().getField(
								cursor.getColumnName(j));
						if (field.getType().getSimpleName()
								.equalsIgnoreCase("double"))
							field.setDouble(classOb, cursor.getDouble(j));
						else if (field.getType().getSimpleName()
								.equalsIgnoreCase("long"))
							field.setLong(classOb, cursor.getLong(j));
						else if (field.getType().getSimpleName()
								.equalsIgnoreCase("int"))
							field.setInt(classOb, cursor.getInt(j));
						else if (field.getType().getSimpleName()
								.equalsIgnoreCase("String"))
							field.set(classOb, cursor.getString(j));
						else if (field.getType().getName()
								.equalsIgnoreCase("[B"))
							field.set(classOb, cursor.getBlob(j));
					}
					recordsList.add(classOb);
				}

			} else {
				System.out.println("No Record Found in Table...");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// cursor.close();
			// db.close();
			try {
				if (db.isOpen()) {
					db.close();

				}
			} catch (Exception e2) {
				// TODO: handle exception
				if (db.isOpen()) {
					db.close();

				}
			}

			// if (!(cursor == null)) {
			// cursor = null;
			// cursor.close();
			// }

		}
		return recordsList;
	}

	public static int selectCount(String count, String where, Object tableClass) {
		db = openDB();

		int cursorCount = 0;

		List<Object> recordsList = new ArrayList<Object>();
		Cursor cursor = null;

		try {
			String tableName = tableClass.getClass().getSimpleName();

			if (where == null || where == "")
				cursor = db.rawQuery("select " + count + " from " + tableName,
						null);

			else
				cursor = db.rawQuery("select " + count + " from " + tableName
						+ " where " + where, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();

				cursorCount = cursor.getInt(0);

				//
			} else {
				System.out.println("No Record Found in Table...");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (db.isOpen()) {
					db.close();

				}
			} catch (Exception e2) {
				// TODO: handle exception
				if (db.isOpen()) {
					db.close();

				}
			}

			// if (!(cursor == null)) {
			// cursor = null;
			// cursor.close();
			// }

		}
		return cursorCount;
	}

	public static int ColumnSum(String where, Object tableClass) {
		db = openDB();

		int cursorCount = 0;

		List<Object> recordsList = new ArrayList<Object>();
		Cursor cursor = null;

		try {
			String tableName = tableClass.getClass().getSimpleName();

			// Cursor cur = db.rawQuery("SELECT  FROM myTable", null);

			if (where == null || where == "")
				cursor = db.rawQuery("select * from " + tableName, null);

			else
				cursor = db.rawQuery("select * from " + tableName + " where "
						+ where, null);

			if (cursor.getCount() > 0) {
				cursor.moveToFirst();

				cursorCount = cursor.getInt(0);

				//
			} else {
				System.out.println("No Record Found in Table...");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (db.isOpen()) {
					db.close();

				}
			} catch (Exception e2) {
				// TODO: handle exception
				if (db.isOpen()) {
					db.close();

				}
			}

			// if (!(cursor == null)) {
			// cursor = null;
			// cursor.close();
			// }

		}
		return cursorCount;
	}

	public static boolean checkEntry(String where, Object tableClass) {
		db = openDB();
		List<Object> recordsList = new ArrayList<Object>();
		Cursor cursor = null;

		boolean entry = false;

		try {
			String tableName = tableClass.getClass().getSimpleName();
			if (where == null || where == "")
				cursor = db.rawQuery("select * from " + tableName, null);
			else
				cursor = db.rawQuery("select * from " + tableName + " where "
						+ where, null);

			if (cursor.getCount() > 0) {
				entry = true;

			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return entry;
	}

	public static long insert(Object tableClass) {
		long insertedRowId = 0;
		try {
			db = openDB();
			ContentValues content = new ContentValues();

			Field[] fields = tableClass.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				putTypedValue(content, fields[i], tableClass);
			}

			insertedRowId = db.insert(tableClass.getClass().getSimpleName(),
					null, content);
			if (db.isOpen())
				db.close();
		} catch (Exception e) {
			if (db.isOpen())
				db.close();
			e.printStackTrace();
		}
		return insertedRowId;
	}

	public static boolean update(Object tableName, String where,
                                 ContentValues values) {
		try {
			db = openDB();
			int rowsUpdated = db.update(tableName.getClass().getSimpleName(),
					values, where, null);
			if (rowsUpdated > 0) {
				if (db.isOpen())
					db.close();
				return true;
			} else {
			}
		} catch (Exception e) {
			if (db.isOpen())
				db.close();
			e.printStackTrace();
		}
		if (db.isOpen())
			db.close();
		return false;
	}

	// public static boolean insertOrUpdate(Object tableClass,HashMap<String,
	// Object> data) {
	// try {
	// String tableName = tableClass.getClass().getSimpleName();
	// db = openDB();
	// if (!update("ID="+data.get("ID"), data));
	// long id = insert(data);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return false;
	// }

	/**
	 * @param Parameters
	 *            : tableClass> the table to delete from where> the optional
	 *            WHERE clause to apply when deleting. Passing null will delete
	 *            all rows. Returns: the number of rows affected if a
	 *            whereClause is passed in, 0 otherwise. To remove all rows and
	 *            get a count pass "1" as the whereClause.
	 */
	public static int delete(Object tableClass, String where) {
		int rowsEffected = 0;
		try {
			db = openDB();
			rowsEffected = db.delete(tableClass.getClass().getSimpleName(),
					where, null);
			if (db.isOpen())
				db.close();
		} catch (Exception e) {
			if (db.isOpen())
				db.close();
			e.printStackTrace();
		}
		if (db.isOpen())
			db.close();

		return rowsEffected;
	}

	public static boolean Close() {
		try {
			if (db.isOpen())
				db.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	static void putTypedValue(ContentValues content, Field field, Object classOb) {
		String fieldName = field.getName();
		try {
			if (field.getType() == Boolean.class
					|| field.getType() == boolean.class) {
				content.put(field.getName(), (Boolean) field.get(classOb));
			} else if (field.getType() == Byte[].class
					|| field.getType() == byte[].class) {
				content.put(field.getName(), (byte[]) field.get(classOb));
			} else if (field.getType() == Double.class
					|| field.getType() == double.class) {
				content.put(field.getName(), (Double) field.get(classOb));
			} else if (field.getType() == Float.class
					|| field.getType() == float.class) {
				content.put(field.getName(), (Float) field.get(classOb));
			} else if (field.getType() == Integer.class
					|| field.getType() == int.class) {
				if (!fieldName.equalsIgnoreCase("id"))
					content.put(field.getName(), (Integer) field.get(classOb));
			} else if (field.getType() == Long.class
					|| field.getType() == long.class) {
				if (!fieldName.equalsIgnoreCase("id"))
					content.put(field.getName(), (Long) field.get(classOb));
			} else if (field.getType() == String.class) {
				content.put(field.getName(), (String) field.get(classOb));
			} else {
				return;
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	static String getSqliteColumnDeclaration(Field field) {
		Class c = field.getType();
		String decl = field.getName() + " ";

		if (c == int.class || c == Integer.class) {
			decl += "INTEGER";
			if (field.getName().equalsIgnoreCase("id"))
				decl += " PRIMARY KEY AUTOINCREMENT";
		} else if (c == Long.class || c == long.class) {
			decl += "LONG";
			if (field.getName().equalsIgnoreCase("id"))
				decl += " PRIMARY KEY AUTOINCREMENT";
		} else if (c == String.class) {
			decl += "TEXT";
		} else if (c == Character.class || c == char.class) {
			decl += "TEXT";
		} else if (c == Float.class || c == float.class) {
			decl += "REAL";
		} else if (c == Boolean.class || c == boolean.class) {
			decl += "Boolean";
		} else if (c == byte[].class || c == byte[].class) {
			decl += "BLOB";
		} else if (c == Double.class || c == double.class) {
			decl += "DOUBLE";
		} else {
			return null;
		}

		return decl;

	}

	public static boolean update(Object tableName, String where) {
		try {
			db = openDB();

			ContentValues values = new ContentValues();
			Field[] fields = tableName.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				putTypedValue(values, fields[i], tableName);
			}

			int rowsUpdated = db.update(tableName.getClass().getSimpleName(),
					values, where, null);
			if (rowsUpdated > 0) {
				if (db.isOpen())
					db.close();
				return true;
			} else {
			}
		} catch (Exception e) {
			if (db.isOpen())
				db.close();
			e.printStackTrace();
		}
		if (db.isOpen())
			db.close();
		return false;
	}

	@Override
	public synchronized void close() {
		if (db != null) {
			db.close();
			super.close();
		}
	}
}
