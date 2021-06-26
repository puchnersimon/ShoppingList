package at.fhhgb.mc.shoppinglist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*

class MainActivity : Activity() {
    lateinit var editText: EditText
    lateinit var buttonAdd: Button
    companion object {
        lateinit var listView: ListView
        var list: ArrayList<String> = ArrayList()
        private lateinit var arrayAdapter: ListViewAdapterMain

        //remove Item with delete-button
        @JvmStatic
        fun removeList(i: Int) {
            list.removeAt(i)
            listView.setAdapter(arrayAdapter)
        }
    }

    /*
    <Button
        android:id="@+id/btnAdd"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/editText"
        android:text="Add List" />
     */

    var listsize: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
        buttonAdd = findViewById(R.id.btnAdd)
        arrayAdapter = ListViewAdapterMain(this, list)

        //add an products to list
        buttonAdd.setOnClickListener {
            if (editText.text.toString().length != 0) {
                list.add(editText.text.toString())
                listsize++
                editText.setText("")
                arrayAdapter.notifyDataSetChanged()
                listView.adapter = arrayAdapter
                Toast.makeText(this, "List added!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No List added!", Toast.LENGTH_SHORT).show()
            }
            saveProducts()
        }

        //step into list by clicking
        listView.adapter = arrayAdapter
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                if (position == 0) {
                    var name1 = listView.getItemAtPosition(position).toString()
                    val intent1 = Intent(this, Activity_List1::class.java)
                    intent1.putExtra("Name", name1)
                    if (name1 != null) {
                        startActivity(intent1)
                    }
                }

                if (position == 1) {
                    var name2 = listView.getItemAtPosition(position).toString()
                    val intent2 = Intent(this, Activity_List2::class.java)
                    intent2.putExtra("Name", name2)
                    if (name2 != null) {
                        startActivity(intent2)
                    }
                }

                if (position == 2) {
                    var name3 = listView.getItemAtPosition(position).toString()
                    val intent3 = Intent(this, Activity_List3::class.java)
                    intent3.putExtra("Name", name3)
                    if (name3 != null) {
                        startActivity(intent3)
                    }
                }

                if (position == 3) {
                    var name4 = listView.getItemAtPosition(position).toString()
                    val intent4 = Intent(this, Activity_List4::class.java)
                    intent4.putExtra("Name", name4)
                    if (name4 != null) {
                        startActivity(intent4)
                    }
                }
            }
    }

    //save data when app is closed
    private fun saveProducts() {
        if (list.isNotEmpty()) {
        val sharedPreferences = this.getSharedPreferences("sharedPrefListsFile", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
            editor.apply {
                putString("STRING_KEY_LISTS", list.toString())
            }.apply()
        }
    }

    //load data when app is opened again
    private fun loadProducts() {
        list.removeAll(list)
        val sharedPreferences = this.getSharedPreferences("sharedPrefListsFile", Context.MODE_PRIVATE)
        var savedLists = sharedPreferences.getString("STRING_KEY_LISTS", null)
        if (savedLists != null) {
            savedLists = savedLists.replace("[", "")
            savedLists = savedLists.replace("]", "")

            val array: List<String> = savedLists!!.split(", ")
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




    //_________________________Tried something other on main activity!______________________________

    /*
    lateinit var binding: ActivityMainBinding
    var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val buttonadd = findViewById<Button>(R.id.activity_main_button_create)
        val buttonremove = findViewById<Button>(R.id.activity_main_button_remove)

        loadLists()
        var count: Int = 0
        buttonadd.setOnClickListener {


            if (count < 4) {
                count = count + 1
                binding.activityMainTextRemove.visibility = View.INVISIBLE
            } else {
                count = 4
                binding.activityMainTextAddtomany.visibility = View.VISIBLE
            }
            when (count) {
                0 -> binding.activityMainTextRemove.visibility = View.VISIBLE
                1 -> {
                    binding.activityMainTextList1.visibility = View.VISIBLE
                    binding.activityMainButtonList1.visibility = View.VISIBLE
                }
                2 -> binding.activityMainButtonList2.visibility = View.VISIBLE
                3 -> binding.activityMainButtonList3.visibility = View.VISIBLE
                4 -> binding.activityMainButtonList4.visibility = View.VISIBLE

            }
        }

        buttonremove.setOnClickListener {
            if (count > 0) {
                count = count - 1
                binding.activityMainTextRemove.visibility = View.INVISIBLE
            } else {
                count = 0
                binding.activityMainTextRemove.visibility = View.VISIBLE
            }
            when (count) {
                0 -> {
                    count = 0
                    binding.activityMainButtonList1.visibility = View.INVISIBLE
                    binding.activityMainTextList1.visibility = View.INVISIBLE
                }
                1 -> {
                    binding.activityMainButtonList2.visibility = View.INVISIBLE
                }
                2 -> {
                    binding.activityMainButtonList3.visibility = View.INVISIBLE
                }
                3 -> {
                    binding.activityMainButtonList4.visibility = View.INVISIBLE
                    binding.activityMainTextAddtomany.visibility = View.INVISIBLE
                }

            }
        }

        binding.activityMainButtonList2.setOnClickListener {
            val i: Intent = Intent(this, Activity_List1::class.java).apply {
                //flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(i)
        }
    }

    fun onSaveButton(view: View) {
        name = findViewById<TextView>(R.id.activity_main_text_list1).text.toString()
        val intent = Intent(this, Activity_List1::class.java)
        intent.putExtra("Name", name)
        if (name != null) {
            startActivity(intent)
        }
    }

    //save listnames
    private fun saveLists() {
        val sharedPreferences = getSharedPreferences("sharedPreferencesLists", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY_LISTS", name)
        }.apply()
    }

    //loadlistnames
    private fun loadLists() {
        val sharedPreferences = getSharedPreferences("sharedPreferencesLists", Context.MODE_PRIVATE)
        var savedstring = sharedPreferences.getString("STRING_KEY_LISTS", null)
        findViewById<TextView>(R.id.activity_main_text_list1).text = savedstring
    }*/
}