package org.wit.wildr.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.wildr.R
//import ie.wit.wildr.R
import ie.wit.wildr.databinding.ActivityWildrListBinding
import ie.wit.wildr.databinding.CardWildrBinding
import org.wit.wildr.adapters.WildrAdapter
import org.wit.wildr.adapters.WildrListener
import org.wit.wildr.main.MainApp
import org.wit.wildr.models.WildrModel

class WildrListActivity : AppCompatActivity(), WildrListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityWildrListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWildrListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = WildrAdapter(app.animals.findAll(),this)
        loadAnimals()
        registerRefreshCallback()


        }
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadAnimals() }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, WildrActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
                //startActivityForResult(launcherIntent,0)

            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onAnimalClick(animal: WildrModel) {
        val launcherIntent = Intent(this, WildrActivity::class.java)
        launcherIntent.putExtra("animal_edit", animal)
        refreshIntentLauncher.launch(launcherIntent)
        //startActivityForResult(launcherIntent,0)
    }
    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }*/
    private fun loadAnimals() {
        showAnimals(app.animals.findAll())
    }

    fun showAnimals (animals: List<WildrModel>) {
        binding.recyclerView.adapter = WildrAdapter(animals, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

}
