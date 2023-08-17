package be.melyuki.roadtripapp.database

class DbContract {

    companion object {
        const val NAME : String = "geek_trotters.sqlite"
        const val VERSION : Int = 1
    }

    class FavTable {

        companion object {
            const val TABLE_NAME = "favorites"

            const val CITY_NAME = "city_name"
            const val LONGITUDE = "longitude"
            const val LATITUDE = "latitude"

            const val SCRIPT_CREATE =
                "CREATE TABLE $TABLE_NAME (" +
                        "$CITY_NAME VARCHAR(50), " +
                        "$LONGITUDE VARCHAR(50), " +
                        "$LATITUDE VARCHAR(50) " +
                ");"

            const val SCRIPT_DROP =
                "DROP TABLE $TABLE_NAME;"
        }
    }
}