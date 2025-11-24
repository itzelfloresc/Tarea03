package com.example.tarea2equipo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class GestorBD {
    private static final int VERSION = 1;
    private static final String NOM_BDD = "howgarts_store";
    private StoreBaseSQLite dbHelper;
    private SQLiteDatabase db;

    public GestorBD(Context context) {
        dbHelper = new StoreBaseSQLite(context, NOM_BDD, null, VERSION);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (dbHelper != null) dbHelper.close();
    }

    // --- GESTIÓN DE USUARIOS ---
    public int registrarUsuario(Usuario usuario) {
        // (Mismo código que en la respuesta anterior para buscar o crear usuario)
        int id = -1;
        String query = "SELECT " + StoreBaseSQLite.COL_ID_USUARIO +
                " FROM " + StoreBaseSQLite.TABLA_USUARIOS +
                " WHERE " + StoreBaseSQLite.COL_NOMBRE + "=? AND " +
                StoreBaseSQLite.COL_CASA + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{usuario.getNombre(), usuario.getCasa()});

        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        } else {
            ContentValues values = new ContentValues();
            values.put(StoreBaseSQLite.COL_NOMBRE, usuario.getNombre());
            values.put(StoreBaseSQLite.COL_CASA, usuario.getCasa());
            id = (int) db.insert(StoreBaseSQLite.TABLA_USUARIOS, null, values);
        }
        cursor.close();
        return id;
    }

    public void registrarCompraCompleta(Pedido pedidoCabecera, List<Ingrediente> carrito) {
        // 1. Insertar la Cabecera del Pedido
        ContentValues valuesPedido = new ContentValues();
        valuesPedido.put(StoreBaseSQLite.COL_ID_USUARIO_PEDIDO, pedidoCabecera.getIdUsuario());
        valuesPedido.put(StoreBaseSQLite.COL_FECHA, pedidoCabecera.getFecha());
        valuesPedido.put(StoreBaseSQLite.COL_TOTAL, pedidoCabecera.getTotal());

        // insert devuelve el ID de la fila recién creada
        long idPedidoGenerado = db.insert(StoreBaseSQLite.TABLA_PEDIDOS, null, valuesPedido);

        // 2. Insertar cada producto del carrito vinculado a ese ID de Pedido
        if (idPedidoGenerado != -1) {
            for (Ingrediente ing : carrito) {
                ContentValues valuesDetalle = new ContentValues();
                valuesDetalle.put(StoreBaseSQLite.COL_DET_ID_PED, idPedidoGenerado); // <-- LA CLAVE
                valuesDetalle.put(StoreBaseSQLite.COL_DET_PROD, ing.getNombre());
                valuesDetalle.put(StoreBaseSQLite.COL_DET_PRECIO, ing.getPrecio());

                db.insert(StoreBaseSQLite.TABLA_DETALLES, null, valuesDetalle);
            }
        }
    }

    public void registrarPedidoCompleto(Pedido pedido) {
        try {
            // 1. Insertamos la CABECERA (Lo general) en la tabla 'pedidos'
            ContentValues valuesCabecera = new ContentValues();
            valuesCabecera.put(StoreBaseSQLite.COL_ID_USUARIO_PEDIDO, pedido.getIdUsuario());
            valuesCabecera.put(StoreBaseSQLite.COL_FECHA, pedido.getFecha());
            valuesCabecera.put(StoreBaseSQLite.COL_TOTAL, pedido.getTotal());

            // Esto nos devuelve el ID único que la BD le asignó a este pedido (ej: 50)
            long idPedidoGenerado = db.insert(StoreBaseSQLite.TABLA_PEDIDOS, null, valuesCabecera);

            // 2. Insertamos la LISTA (Los detalles) usando ese ID
            if (idPedidoGenerado != -1) {
                // El gestor extrae la lista que está DENTRO del objeto pedido
                for (Ingrediente ing : pedido.getIngredientes()) {
                    ContentValues valuesDetalle = new ContentValues();
                    valuesDetalle.put(StoreBaseSQLite.COL_DET_ID_PED, idPedidoGenerado); // Usamos el ID del paso 1
                    valuesDetalle.put(StoreBaseSQLite.COL_DET_PROD, ing.getNombre());
                    valuesDetalle.put(StoreBaseSQLite.COL_DET_PRECIO, ing.getPrecio());

                    db.insert(StoreBaseSQLite.TABLA_DETALLES, null, valuesDetalle);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
