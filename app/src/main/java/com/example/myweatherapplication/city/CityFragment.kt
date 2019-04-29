package com.example.myweatherapplication.city

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.example.myweatherapplication.App
import com.example.myweatherapplication.Database
import com.example.myweatherapplication.R
import com.example.myweatherapplication.city.CreateCityDialogFragment.CreateCityDialogListener

class CityFragment : Fragment(), CityAdapter.CityItemListener {

    private val TAG = CityFragment::class.java.simpleName

    interface CityFragmentListener {
        fun onCitySelected(city: City)
        fun onSelectionCleared()
        fun onEmptyCities()
    }

    var listener: CityFragmentListener? = null

    private lateinit var cities: MutableList<City>
    private lateinit var database: Database
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = App.database
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)
        recyclerView = view.findViewById(R.id.cities_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cities = database.getAllCities()

        adapter = CityAdapter(cities, this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_city, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_create_city -> {
                showCreateCityDialog()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCitySelected(city: City) {
        listener?.onCitySelected(city)
    }

    override fun onCityDeleted(city: City) {
        showDeleteCityDialog(city)
    }

    fun selectFirstCity() {
        when (cities.isEmpty()) {
            true -> listener?.onEmptyCities()
            false -> onCitySelected(cities.first())
        }
    }

    private fun showCreateCityDialog() {
        val createCityFragment = CreateCityDialogFragment()
        createCityFragment.listener = object: CreateCityDialogListener {
            override fun onDialogPositiveClick(cityName: String) {
                saveCity(City(cityName))
            }

            override fun onDialogNegativeClick() { }
        }

        createCityFragment.show(fragmentManager, "CreateCityDialogFragment")
    }

    private fun showDeleteCityDialog(city: City) {
        val deleteCityFragment = DeleteCityDialogFragment.newInstance(city.name)
        deleteCityFragment.listener = object: DeleteCityDialogFragment.DeleteCityDialogListener {
            override fun onDialogPositiveClick() {
                deleteCity(city)
            }

            override fun onDialogNegativeClick() { }
        }

        deleteCityFragment.show(fragmentManager, "DeleteCityDialogFragment")
    }

    private fun saveCity(city: City) {
        if (database.saveCity(city)) {
            cities.add(city)
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(context,
                R.string.city_message_error_could_not_create_city,
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteCity(city: City) {
        if (database.deleteCity(city)) {
            cities.remove(city)
            adapter.notifyDataSetChanged()
            selectFirstCity()
            Toast.makeText(context,
                getString(R.string.city_message_info_city_deleted, city.name),
                Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context,
                getString(R.string.city_message_error_could_not_delete_city, city.name),
                Toast.LENGTH_SHORT).show()
        }
    }

}