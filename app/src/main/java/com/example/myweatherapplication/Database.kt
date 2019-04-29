package com.example.myweatherapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myweatherapplication.city.City

private const val DATABASE_NAME = "weather.db"
private const val DATABASE_VERSION = 1

private const val CITY_TABLE_NAME = "city"
private const val CITY_KEY_ID = "id"
private const val CITY_KEY_NAME = "name"

private const val CITY_TABLE_CREATE = """
CREATE TABLE $CITY_TABLE_NAME (
    $CITY_KEY_ID INTEGER PRIMARY KEY,
    $CITY_KEY_NAME TEXT)
"""

private const val CITY_QUERY_SELECT_ALL = "SELECT * FROM $CITY_TABLE_NAME"

class Database(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val TAG = Database::class.java.simpleName

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CITY_TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun saveCity(city: City) : Boolean {
        val values = ContentValues()
        values.put(CITY_KEY_NAME, city.name)

        return when {
            city.id < 0 -> {
                city.id = createCity(values)
                city.id > 0
            }
            else -> updateCity(city.id, values)
        }
    }

    fun getAllCities() : MutableList<City> {
        val cities = mutableListOf<City>()

        readableDatabase.rawQuery(CITY_QUERY_SELECT_ALL, null).use { cursor ->
            while (cursor.moveToNext()) {
                val city = City(
                    cursor.getLong(cursor.getColumnIndex(CITY_KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(CITY_KEY_NAME))
                )
                cities.add(city)
            }
        }

        return cities
    }

    fun deleteCity(city: City) : Boolean {
        val deleteCount = writableDatabase.delete(CITY_TABLE_NAME,
            "$CITY_KEY_ID = ?",
            arrayOf("${city.id}"))
        return deleteCount == 1
    }

    private fun createCity(values: ContentValues) : Long {
        return writableDatabase.insert(CITY_TABLE_NAME, null, values)
    }

    private fun updateCity(id: Long, values: ContentValues) : Boolean {
        val updateCount = writableDatabase.update(CITY_TABLE_NAME,
            values,
            "$CITY_KEY_ID = ?",
            arrayOf("$id"))
        return updateCount > 0
    }

}