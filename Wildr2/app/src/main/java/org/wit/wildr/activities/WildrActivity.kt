package org.wit.wildr.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.wildr.databinding.ActivityWildrBinding
import org.wit.wildr.main.MainApp
import org.wit.wildr.models.WildrModel
import timber.log.Timber
import timber.log.Timber.i

class WildrActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWildrBinding
    var animal = WildrModel()
   // val animals = ArrayList<WildrModel>()
   lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWildrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Timber.plant(Timber.DebugTree())
        app = application as MainApp
        i("Wildr Activity started..")
        binding.btnAdd.setOnClickListener() {
            animal.title = binding.wildrTitle.text.toString()
            animal.description = binding.description.text.toString()
            if (animal.title.isNotEmpty()) {
                app.animals.add(animal.copy())
                i("add Button Pressed: ${animal}")
                for (i in app.animals.indices) {
                    i("Animal[$i]:${this.app.animals[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }

}