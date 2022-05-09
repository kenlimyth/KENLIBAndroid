package com.kenlib.sqlite;

import com.kenlib.util.KENConfig;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SqlLite工具类
 */
public class SqlLiteTool extends SQLiteOpenHelper {

    private static String name = "mydb.db";
    private static int version = KENConfig.DB_VERSION;

    public SqlLiteTool(Context context) {
        super(context, name, null, version);
    }

    /**
     * 初始化DB
     *
     * @param db
     */
    void initDB(SQLiteDatabase db) {
    	// sql
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("create table [user](id integer ,isdenglu integer,[userid] varchar(64),[isjizhu] varchar(64))");
        db.execSQL("insert into [user](id,userid) values(1,'1')");

        db.execSQL("DROP TABLE IF EXISTS hxuser");
        db.execSQL("create table [hxuser](tel varchar(64) ,[imgurl] varchar(64),[nicheng] varchar(64))");

        db.execSQL("DROP TABLE IF EXISTS liaotian");
        db.execSQL("create table [liaotian](id integer ,tel varchar(64) ,[tel1] varchar(64))");
        db.execSQL("insert into [liaotian](id,tel,tel1) values(1,'1','1')");


        db.execSQL("DROP TABLE IF EXISTS [Broker_Invoice]");
        db.execSQL("create table [Broker_Invoice](id integer primary key autoincrement,[StoreId]"
                + " varchar(64),[ToStoreId] varchar(64),[CreateUser] varchar(64),[ShipperTel] varchar(64),[Shipper] varchar(64),[ConsigneeAddress] varchar(64),[ConsigneeTel] varchar(64),[Consignee] varchar(64),[DeliveryType] varchar(64),[TotalCarriage] varchar(64),[AgencyReceiveCharge] varchar(64),[Notes] varchar(164),[CargoName] varchar(64),[Qty] varchar(64),[Weight] varchar(64),[Cubage] varchar(64)"
                + ",[ctime] datetime,[isup] varchar(64))");


        db.execSQL("DROP TABLE IF EXISTS KCData");
        db.execSQL("create table KCData(id integer primary key autoincrement,"
                + "[ydh] varchar(64),[hw] varchar(64),[sl] varchar(64),[zl] varchar(64),[tj] varchar(64),"
                + "[toCity] varchar(64),[bz] varchar(64),[wd] varchar(64),[type] varchar(64),"
                + "[isCheck] varchar(64)"
                + ",[ctime] datetime,[fy] varchar(50),[wdname] varchar(50),[fwdname] varchar(50),"
                + "[sjkc] varchar(50),[kcc] varchar(50))");
    }

    /**
     * 当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行. 重写onCreate方法，调用execSQL方法创建表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        initDB(db);

    }

    /**
     * 当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
     */
    //
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        initDB(db);
    }
}
