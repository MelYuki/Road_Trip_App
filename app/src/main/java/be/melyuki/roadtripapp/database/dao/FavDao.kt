package be.melyuki.roadtripapp.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import be.melyuki.roadtripapp.database.DbContract
import be.melyuki.roadtripapp.database.DbHelper
import be.melyuki.roadtripapp.models.MapResearchModel

class FavDao(val context : Context) {

    var dbHelper : DbHelper? = null
    var db : SQLiteDatabase? = null

    // region Connect's Method to DB
    fun openWritable() {
        dbHelper = DbHelper(context)
        db = dbHelper?.writableDatabase
    }

    fun openReadable() {
        dbHelper = DbHelper(context)
        db = dbHelper?.readableDatabase
    }

    fun close() {
        db?.close()
        dbHelper?.close()
    }
    // endregion

    // region Setting ContentValues
    private fun getContentValues(city : MapResearchModel) : ContentValues {
        val contentValues = ContentValues().apply {
            put(DbContract.FavTable.CITY_NAME, city.displayName)
            put(DbContract.FavTable.LONGITUDE, city.lon)
            put(DbContract.FavTable.LATITUDE, city.lat)
        }
        return contentValues
    }
    // endregion

    // region Setting Cursor
    private fun cursorToCity(cursor : Cursor) : MapResearchModel {
        val city = MapResearchModel(
            cursor.getString(cursor.getColumnIndexOrThrow(DbContract.FavTable.CITY_NAME)),
            cursor.getString(cursor.getColumnIndexOrThrow(DbContract.FavTable.LONGITUDE)),
            cursor.getString(cursor.getColumnIndexOrThrow(DbContract.FavTable.LATITUDE))
        )
        return city
    }
    // endregion

    // region CRuD
    fun getAll() : List<MapResearchModel> {

        // Execution de la requete SQL
        val cursor = db!!.query(
            DbContract.FavTable.TABLE_NAME,
            null,
            null,  null,
            null,
            null,
            null
        )

        // Gestion d'un resultat "vide"
        if(cursor.count == 0) {
            return listOf()
        }

        // Gestion d'un resultat "non vide"
        val result = mutableListOf<MapResearchModel>()

        // - Positionner le cursor sur un element
        cursor.moveToFirst()

        while(!cursor.isAfterLast) {
            // - Récuperation des données
            val city: MapResearchModel = cursorToCity(cursor)

            // - Ajout du resultat dans la liste
            result.add(city)

            // - On passe a la ligne suivante
            cursor.moveToNext()
        }

        // - On ferme le curseur
        cursor.close()

        return result.toList()
    }

    fun create(city: MapResearchModel) : Long {
        val index = db!!.insert(
            DbContract.FavTable.TABLE_NAME,
            null,
            getContentValues(city)
        )
        return index
    }

    fun delete(city: MapResearchModel) : Boolean {
        val numRow = db!!.delete(
            DbContract.FavTable.TABLE_NAME,
            DbContract.FavTable.CITY_NAME + " = ?" ,
            arrayOf(city.displayName.toString())
        )
        return numRow == 1
    }
    // endregion
}