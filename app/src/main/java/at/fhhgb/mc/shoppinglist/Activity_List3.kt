package at.fhhgb.mc.shoppinglist

import android.content.Context
import android.os.Bundle
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity


class Activity_List3 : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var editNumber: EditText
    lateinit var buttonAdd: Button
    lateinit var buttonRemove: Button
    companion object {
        lateinit var listView: ListView
        var list: ArrayList<String> = ArrayList()
        private lateinit var arrayAdapter: ListViewAdapter3

        //remove Item with check-button
        @JvmStatic
        fun removeItem(i: Int) {
            list.removeAt(i)
            listView.setAdapter(arrayAdapter)
        }
    }

    var listsize: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list1)

        //set listname as title
        val intent = getIntent()
        val name = intent.getStringExtra("Name")
        title = name
        
        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
        editNumber = findViewById(R.id.editNumber)
        buttonAdd = findViewById(R.id.btnAdd)
        buttonRemove = findViewById(R.id.btnRemove)
        arrayAdapter = ListViewAdapter3(this, list)

        loadProducts()

        //add an products to list
        buttonAdd.setOnClickListener {
            if (editText.text.toString().length != 0) {
                list.add(editText.text.toString() + "           -           " + editNumber.text.toString())
                listsize++
                editText.setText("")
                editNumber.setText("")
                arrayAdapter.notifyDataSetChanged()
                listView.adapter = arrayAdapter
                Toast.makeText(this, "Product added!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No Product added!", Toast.LENGTH_SHORT).show()
            }
            saveProducts()
        }

        //remove all products from list
        buttonRemove.setOnClickListener {
            while (list.isNotEmpty()) {
                list.removeAt(0)
            }
            Toast.makeText(this, "All Products deleted!", Toast.LENGTH_SHORT).show()
            saveProducts()
            arrayAdapter.notifyDataSetChanged()
            listView.adapter = arrayAdapter
        }

        //remove item by clicking
        /*
        listView.adapter = arrayAdapter
        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                list.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
            }*/
    }

    //save data when app is closed
    private fun saveProducts() {
        val sharedPreferences = getSharedPreferences("sharedPreferencesList3", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY_LIST3", list.toString())
        }.apply()
    }

    //load data when app is opened again
    private fun loadProducts() {
        list.removeAll(list)
        val sharedPreferences = getSharedPreferences("sharedPreferencesList3", Context.MODE_PRIVATE)
        var savedstring = sharedPreferences.getString("STRING_KEY_LIST3", null)
        if (savedstring != null) {
            savedstring = savedstring.replace("[", "")
            savedstring = savedstring.replace("]", "")


        val array: List<String> = savedstring!!.split(", ")
        for (i in array.indices) {
            list.add(array[i])
        }
        arrayAdapter.notifyDataSetChanged()
        listView.adapter = arrayAdapter
        }
    }

    override fun onPause() {
        super.onPause()
        saveProducts()
    }

    override fun onResume() {
        super.onResume()
        loadProducts()
    }

    override fun onStop() {
        super.onStop()
        saveProducts()
    }

    override fun onStart() {
        super.onStart()
        loadProducts()
    }
}

