package com.example.urbandictionaryapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.model.ItemDescriptionResponse
import com.example.urbandictionaryapp.viewmodel.UrbanDictionaryVMFactory
import com.example.urbandictionaryapp.viewmodel.UrbanDictionaryViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var  search: EditText
    val viewModel: UrbanDictionaryViewModel by lazy {
        ViewModelProvider(this,
            UrbanDictionaryVMFactory())
            .get(UrbanDictionaryViewModel::class.java)

    }

    val urbanAdapter : UrbanDictionaryAdapter by lazy {
        UrbanDictionaryAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search =  findViewById(R.id.editText_search)

        initViews()


        viewModel.getUrbanDescription().observe(this,
            object : Observer<ItemDescriptionResponse> {
                override fun onChanged(t: ItemDescriptionResponse?) {
                    t?.let { initAdapter((it)) }
                }
            })
        viewModel.getUrbanDescriptionError().observe(this,
            object : Observer<String> {
                override fun onChanged(t: String?) {
                    t?.let { Toast.makeText(this@MainActivity, t, Toast.LENGTH_SHORT).show()}
                }
            })
    }


    fun sortUpButton (view: View){

        Toast.makeText(this@MainActivity, "Sorting up", Toast.LENGTH_SHORT).show()
        sortUp()
    }

    fun sortUpDown (view: View){

        Toast.makeText(this@MainActivity, "Sorting down", Toast.LENGTH_SHORT).show()
        sortDown()
    }

    fun Search (view: View){

        if (!search.text.isEmpty()){

            val define = search.text.toString()
            searchForDefinition(define)
            search.text.clear()

        }
        else(
                Toast.makeText(this@MainActivity, "Define your search first please", Toast.LENGTH_SHORT).show()
                )
    }


    fun initViews(){
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = urbanAdapter
    }

    private fun sortDown() {
        viewModel.sortDataDown()
    }

    private fun sortUp() {
        viewModel.sortDataUp()
    }

    fun initAdapter(dataSet: ItemDescriptionResponse){
        urbanAdapter.dataSet = dataSet
    }

    fun searchForDefinition(userInput: String){

        viewModel.getDescription(userInput)
    }

}
