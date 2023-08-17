package be.melyuki.roadtripapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.melyuki.roadtripapp.R
import be.melyuki.roadtripapp.models.CityModel

class ListFavAdapter(val context : Context, val cities : List<CityModel>) :
    RecyclerView.Adapter<ListFavAdapter.CityViewHolder>() {
    class CityViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle : TextView
        private val tvLon : TextView
        private val tvLat : TextView

        fun setTitle(name : String) {
            tvTitle.text = name
        }
        fun setLon(name : String) {
            tvLon.text = name
        }
        fun setLat(name : String) {
            tvLat.text = name
        }

        init {
            tvTitle = itemView.findViewById(R.id.rv_item_city_display_name)
            tvLon = itemView.findViewById(R.id.rv_item_city_lon)
            tvLat = itemView.findViewById(R.id.rv_item_city_lat)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_rv_fav, parent, false)
        return CityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val element : CityModel = cities[position]

        holder.setTitle(element.displayName.toString())
        holder.setLon(element.lon.toString())
        holder.setLat(element.lat.toString())
    }
}