package com.example.tarea2equipo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoreBaseSQLite extends SQLiteOpenHelper {

    // TABLA DE USUARIOS
    public static final String TABLA_USUARIOS = "usuarios";
    public static final String COL_ID_USUARIO = "id_usuario";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_CASA = "casa";

    // TABLA DE PEDIDOS
    public static final String TABLA_PEDIDOS = "pedidos";
    public static final String COL_ID_PEDIDO = "id_pedido";
    public static final String COL_ID_USUARIO_PEDIDO = "id_usuario_fk";
    public static final String COL_FECHA = "fecha";
    public static final String COL_TOTAL = "total";

    // TABLA DE INGREDIENTES POR PEDIDO
    public static final String TABLA_DETALLES = "detalles_pedido";
    public static final String COL_DET_ID = "id_detalle";
    public static final String COL_DET_ID_PED = "id_pedido_fk";
    public static final String COL_DET_PROD = "producto";
    public static final String COL_DET_PRECIO = "precio";

    public static final String CREATE_TABLE_USUARIO = "CREATE TABLE " + TABLA_USUARIOS + " (" +
                                                       COL_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                       COL_NOMBRE + " TEXT, " +
                                                       COL_CASA + " TEXT)";
    public static final String CREATE_TABLE_PEDIDO = "CREATE TABLE " + TABLA_PEDIDOS + " (" +
                                                     COL_ID_PEDIDO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                     COL_ID_USUARIO_PEDIDO + " INTEGER, " +
                                                     COL_FECHA + " TEXT, " +
                                                     COL_TOTAL + " REAL, " +
                                                     "FOREIGN KEY(" + COL_ID_USUARIO_PEDIDO + ") REFERENCES " + TABLA_USUARIOS + "(" + COL_ID_USUARIO + "))";

    public static final String CREATE_TABLE_DETALLES = "CREATE TABLE " + TABLA_DETALLES + " (" +
                                                        COL_DET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                        COL_DET_ID_PED + " INTEGER, " +
                                                        COL_DET_PROD + " TEXT, " +
                                                        COL_DET_PRECIO + " REAL, " +
                                                        "FOREIGN KEY(" + COL_DET_ID_PED + ") REFERENCES " + TABLA_PEDIDOS + "(" + COL_ID_PEDIDO + "))";

    public StoreBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_PEDIDO);
        db.execSQL(CREATE_TABLE_DETALLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_PEDIDOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_DETALLES);
        onCreate(db);

    }

}
