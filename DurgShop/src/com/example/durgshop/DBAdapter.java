package com.example.durgshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * ���ݿ����
 * */
public class DBAdapter {

	private static final String DB_NAME = "durgsShop.db";
	private static final int DB_VERSION = 1;

	private SQLiteDatabase db;
	private final Context context;
	private DBOpenHelper dbOpenHelper;

	public DBAdapter(Context _context) {
		context = _context;
	}

	/** Close the database */
	public void close() {
		if (db != null) {
			db.close();
			db = null;
		}
	}

	/** Open the database */
	public void open() throws SQLiteException {
		dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
		try {
			db = dbOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbOpenHelper.getReadableDatabase();
		}
	}

	/** ע�� */
	public long insert(Users user) {
		ContentValues newValues = new ContentValues();

		newValues.put("username", user.name);
		newValues.put("password", user.password);

		return db.insert("usertable", null, newValues);
	}

	/** ���ҩƷ */
	public long adddurg(Durgs durg) {
		ContentValues newValues = new ContentValues();

		newValues.put("dname", durg.name);
		newValues.put("price", durg.price);

		return db.insert("durgs", null, newValues);
	}

	/** ����ҩƷ */
	public long addpaid(Paiddurg buy) {
		ContentValues newValues = new ContentValues();

		newValues.put("did", buy.did);
		newValues.put("uid", buy.uid);

		return db.insert("buy", null, newValues);
	}

	/** ���ҩƷ���� */
	public long adddmess(Dmess dm) {
		ContentValues newValues = new ContentValues();

		newValues.put("uid", dm.uid);
		newValues.put("did", dm.did);
		newValues.put("cont", dm.cont);

		return db.insert("dmess", null, newValues);
	}

	/** ��ӹ���Ա���� */
	public long addappmess(Appmess am) {
		ContentValues newValues = new ContentValues();

		newValues.put("uid", am.uid);
		newValues.put("cont", am.cont);

		return db.insert("appmess", null, newValues);
	}

	/** ��ʾ����ҩƷ */
	public Durgs[] queryAllDurgs() {
		Cursor results = db.query("durgs", new String[] { "did", "dname",
				"price" }, null, null, null, null, null);
		return ConvertToDurgs(results);
	}

	/** ��ѯҩƷ */
	public Durgs[] querygDurgbyname(String name) {
		String sql = "select * from durgs where dname like '%" + name + "%'";
		Cursor results = db.rawQuery(sql, null);
		return ConvertToDurgs(results);
	}

	/** ��ѯһ��ҩƷ */
	public Durgs[] querygDurgbyid(String id) {
		Cursor results = db.query("durgs", new String[] { "did", "dname",
				"price" }, "did = " + String.valueOf(id), null, null, null,
				null);
		return ConvertToDurgs(results);
	}

	/** ��ʾ������Ʒ���� */
	public Dmess[] queryAllDmessge() {
		Cursor results = db.query("dmess", new String[] { "id", "uid", "did",
				"cont" }, null, null, null, null, null);
		return ConvertToDmess(results);
	}

	/** ��ʾĳ����Ʒ���� */
	public Dmess[] queryDmessge(String did) {
		Cursor results = db.query("dmess", new String[] { "id", "uid", "did",
				"cont" }, "did = " + String.valueOf(did), null, null, null,
				null);
		return ConvertToDmess(results);
	}

	/** ��ʾ���й���Ա���� */
	public Appmess[] queryAllAppmessge() {
		Cursor results = db.query("appmess",
				new String[] { "id", "uid", "cont" }, null, null, null, null,
				null);
		return ConvertToAppmess(results);
	}

	/** ��ʾ�����û� */
	public Users[] queryAllData() {
		Cursor results = db.query("usertable", new String[] { "id", "username",
				"password" }, null, null, null, null, null);
		return ConvertToUser(results);
	}

	/** ��ʾ���ж��� */
	public Paiddurg[] queryAllPaid(String uid) {
		Cursor results = db.query("buy", new String[] { "id", "uid", "did" },
				"uid = " + Integer.parseInt(uid), null, null, null, null);
		return ConvertToPaid(results);
	}

	/** ����һλ�û� */
	public Users[] queryOneData(String username, String password) {
		Cursor results = db.query("usertable", new String[] { "id", "username",
				"password" }, "username ='" + username + "' and password ='"
				+ password + "'", null, null, null, null);
		return ConvertToUser(results);
	}

	/** ����һλ�û� */
	public Users[] queryUserbyid(String id) {
		Cursor results = db.query("usertable", new String[] { "id", "username",
				"password" }, "id = '" + Integer.parseInt(id) + "'", null,
				null, null, null);
		return ConvertToUser(results);
	}

	/** ��֤ע���û��� */
	public Users[] queryRegisterData(String username) {
		Cursor results = db.query("usertable", new String[] { "id", "username",
				"password" }, "username ='" + username + "'", null, null, null,
				null);
		return ConvertToUser(results);
	}

	/** ���α�תΪUsers���� */
	private Users[] ConvertToUser(Cursor cursor) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		Users[] users = new Users[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			users[i] = new Users();
			users[i].id = cursor.getInt(0);
			users[i].name = cursor.getString(cursor.getColumnIndex("username"));
			users[i].password = cursor.getString(cursor
					.getColumnIndex("password"));
			cursor.moveToNext();
		}
		return users;
	}

	/** ���α�תΪDurgs���� */
	private Durgs[] ConvertToDurgs(Cursor cursor) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		Durgs[] durgs = new Durgs[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			durgs[i] = new Durgs();
			durgs[i].did = cursor.getString(0);
			durgs[i].name = cursor.getString(cursor.getColumnIndex("dname"));
			durgs[i].price = cursor.getString(cursor.getColumnIndex("price"));
			cursor.moveToNext();
		}
		return durgs;
	}

	/** ���α�תΪDmess���� */
	private Dmess[] ConvertToDmess(Cursor cursor) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		Dmess[] dm = new Dmess[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			dm[i] = new Dmess();
			dm[i].id = cursor.getString(0);
			dm[i].did = cursor.getString(cursor.getColumnIndex("did"));
			dm[i].uid = cursor.getString(cursor.getColumnIndex("uid"));
			dm[i].cont = cursor.getString(cursor.getColumnIndex("cont"));
			cursor.moveToNext();
		}
		return dm;
	}

	/** ���α�תΪAppmess���� */
	private Appmess[] ConvertToAppmess(Cursor cursor) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		Appmess[] am = new Appmess[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			am[i] = new Appmess();
			am[i].id = cursor.getString(0);
			am[i].uid = cursor.getString(cursor.getColumnIndex("uid"));
			am[i].cont = cursor.getString(cursor.getColumnIndex("cont"));
			cursor.moveToNext();
		}
		return am;
	}

	/** ���α�תΪPaiddurg���� */
	private Paiddurg[] ConvertToPaid(Cursor cursor) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		Paiddurg[] p = new Paiddurg[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			p[i] = new Paiddurg();
			p[i].id = cursor.getString(0);
			p[i].uid = cursor.getString(cursor.getColumnIndex("uid"));
			p[i].did = cursor.getString(cursor.getColumnIndex("did"));
			cursor.moveToNext();
		}
		return p;
	}

	/** ɾ��usertable���������� */
	public long deleteAllData() {
		db.delete("dmess", null, null);
		db.delete("appmess", null, null);
		db.delete("buy", null, null);
		return db.delete("usertable", null, null);
	}

	/** ɾ��usertable��ָ������ */
	public long deleteOneData(String id) {
		db.delete("dmess", "uid" + "=" + Integer.parseInt(id), null);
		db.delete("appmess", "uid" + "=" + Integer.parseInt(id), null);
		db.delete("buy", "uid" + "=" + Integer.parseInt(id), null);
		return db.delete("usertable", "id" + "=" + Integer.parseInt(id), null);
	}

	/** ɾ��durg��ָ������ */
	public long deleteOneDurg(String id) {
		db.delete("dmess", "did" + "=" + Integer.parseInt(id), null);
		db.delete("buy", "did" + "=" + Integer.parseInt(id), null);
		return db.delete("durgs", "did" + "=" + Integer.parseInt(id), null);
	}

	/** ����usertable��ָ������ */
	public long updateOneData(String id, Users users) {
		ContentValues updateValues = new ContentValues();
		updateValues.put("username", users.name);
		updateValues.put("password", users.password);

		return db.update("usertable", updateValues,
				"id" + "=" + Integer.parseInt(id), null);
	}

	/** ��̬Helper�࣬���ڽ��������ºʹ����ݿ� */
	private static class DBOpenHelper extends SQLiteOpenHelper {

		public DBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		private static final String DB_CREATE_USER = "CREATE TABLE usertable"
				+ "(id integer primary key autoincrement, username text,password text);";

		private static final String DB_CREATE_DURG = "CREATE TABLE durgs"
				+ "(did integer primary key autoincrement, dname text,price text);";

		private static final String DB_CREATE_DMESS = "CREATE TABLE dmess("
				+ "[id] integer PRIMARY KEY AUTOINCREMENT, "
				+ "[uid] integer REFERENCES [usertable]([id]), "
				+ "[did] integer REFERENCES [durgs]([did])," + "[cont] text);";

		private static final String DB_CREATE_APPMESS = "CREATE TABLE appmess("
				+ "[id] integer PRIMARY KEY AUTOINCREMENT, "
				+ "[uid] integer REFERENCES [usertable]([id]), "
				+ "[cont] text);";

		private static final String DB_CREATE_BUY = "CREATE TABLE buy("
				+ "[id] integer PRIMARY KEY AUTOINCREMENT,"
				+ "[uid] integer REFERENCES [usertable]([id]),"
				+ "[did] integer REFERENCES [durgs]([did]));";

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DB_CREATE_USER);
			_db.execSQL(DB_CREATE_DURG);
			_db.execSQL(DB_CREATE_DMESS);
			_db.execSQL(DB_CREATE_APPMESS);
			_db.execSQL(DB_CREATE_BUY);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
				int _newVersion) {
			_db.execSQL("DROP TABLE IF EXISTS " + DB_CREATE_APPMESS);
			_db.execSQL("DROP TABLE IF EXISTS " + DB_CREATE_DMESS);
			_db.execSQL("DROP TABLE IF EXISTS " + DB_CREATE_DURG);
			_db.execSQL("DROP TABLE IF EXISTS " + DB_CREATE_USER);
			_db.execSQL("DROP TABLE IF EXISTS " + DB_CREATE_BUY);
			onCreate(_db);
		}
	}
}
