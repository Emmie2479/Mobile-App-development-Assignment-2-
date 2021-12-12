package org.wit.wildr.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.wildr.R
//import ie.wit.wildr.R
import ie.wit.wildr.databinding.ActivityWildrListBinding
import ie.wit.wildr.databinding.CardWildrBinding
import org.wit.wildr.main.MainApp
import org.wit.wildr.models.WildrModel

class WildrListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityWildrListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWildrListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = WildrAdapter(app.animals)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, WildrActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
class WildrAdapter constructor(private var wildrList: List<WildrModel>) :
    RecyclerView.Adapter<WildrAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWildrBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val wildrListHolder = wildrList[holder.adapterPosition]
        holder.bind(wildrListHolder)
    }

    override fun getItemCount(): Int = wildrList.size

    class MainHolder(private val binding : CardWildrBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animalModel: WildrModel) {
            binding.wildrTitle.text = animalModel.title
            binding.description.text = animalModel.description
        }
    }
}