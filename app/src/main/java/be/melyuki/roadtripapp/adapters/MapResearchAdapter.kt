package be.melyuki.roadtripapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import be.melyuki.roadtripapp.R
import be.melyuki.roadtripapp.models.MapResearchModel

class MapResearchAdapter(val context: Context, val nominatimList: MutableList<MapResearchModel>) :
    BaseAdapter() {

//    lateinit var name : String
//    lateinit var lat : String
//    lateinit var long: String

    lateinit var tv_name : TextView
    lateinit var tv_lat : TextView
    lateinit var tv_lon : TextView

    override fun getCount(): Int {
        return nominatimList.size
    }
    override fun getItem(position: Int): Any {
        return nominatimList[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val convertView = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false)

//        nominatimList.map { c ->
//            name = c.displayName.toString()
//            lat = c.lat.toString()
//            long = c.lon.toString()
//        }

        val element : MapResearchModel = nominatimList[position]

        val lonText = context.getString(R.string.tv_item_city_long)
        val latText = context.getString(R.string.tv_item_city_lat, element.lat)

        tv_name = convertView.findViewById(R.id.tv_item_city_display_name)
        tv_name.text = element.displayName

        tv_lon = convertView.findViewById(R.id.tv_item_city_lon)
        tv_lon.text = lonText.format(element.lon)

        tv_lat = convertView.findViewById(R.id.tv_item_city_lat)
        tv_lat.text = latText

        return convertView
    }
}