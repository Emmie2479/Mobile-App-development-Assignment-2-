package ie.wit.wildr.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.wildr.R
import ie.wit.wildr.adapters.WildrAdapter
import ie.wit.wildr.adapters.WildrListener
import ie.wit.wildr.databinding.ActivityWildrListBinding
import ie.wit.wildr.main.WildrApp
import ie.wit.wildr.models.AnimalModel

class WildrListActivity : AppCompatActivity(), WildrListener {

    lateinit var app: WildrApp
    private lateinit var binding: ActivityWildrListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWildrListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as WildrApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadAnimals()

        registerRefreshCallback()
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
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWildrClick(animal: AnimalModel) {
        val launcherIntent = Intent(this, WildrActivity::class.java)
        launcherIntent.putExtra("animal_edit", animal)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadAnimals() }
    }

    private fun loadAnimals() {
        showAnimals(app.animals.findAll())
    }

    fun showAnimals (animals: List<AnimalModel>) {
        binding.recyclerView.adapter = WildrAdapter(animals, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}