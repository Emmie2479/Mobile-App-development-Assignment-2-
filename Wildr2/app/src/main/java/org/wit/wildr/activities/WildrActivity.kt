package org.wit.wildr.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.wildr.R
import ie.wit.wildr.databinding.ActivityWildrBinding
import org.wit.wildr.helpers.showImagePicker
import org.wit.wildr.main.MainApp
import org.wit.wildr.models.WildrModel
import timber.log.Timber
import timber.log.Timber.i

class WildrActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWildrBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var animal = WildrModel()

   // val animals = ArrayList<WildrModel>()
   lateinit var app: MainApp

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       var edit = false
        binding = ActivityWildrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Timber.plant(Timber.DebugTree())
        app = application as MainApp
        i("Wildr Activity started..")
       binding = ActivityWildrBinding.inflate(layoutInflater)
       setContentView(binding.root)
       binding.toolbarAdd.title = title
       setSupportActionBar(binding.toolbarAdd)

       if (intent.hasExtra("animal_edit")) {
           edit = true
           animal = intent.extras?.getParcelable("animal_edit")!!
           binding.wildrTitle.setText(animal.title)
           binding.description.setText(animal.description)
           binding.btnAdd.setText(R.string.save_animal)
           Picasso.get()
               .load(animal.image)
               .into(binding.animalImage)
           if (animal.image != Uri.EMPTY) {
               binding.chooseImage.setText(R.string.change_animal_image)
           }
       }

       binding.btnAdd.setOnClickListener() {
            animal.title = binding.wildrTitle.text.toString()
            animal.description = binding.description.text.toString()
           if (animal.title.isEmpty()) {
               Snackbar.make(it,R.string.enter_animal_title, Snackbar.LENGTH_LONG)
                   .show()
           } else {
               if (edit) {
                   app.animals.update(animal.copy())
               } else {
                   app.animals.create(animal.copy())
               }
           }
           i("add Button Pressed: $animal")
           setResult(RESULT_OK)
           finish()
       }
       binding.chooseImage.setOnClickListener {
           showImagePicker(imageIntentLauncher)

       }
       binding.animalLocation.setOnClickListener {
           i ("Set Location Pressed")
       }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            animal.image = result.data!!.data!!
                            Picasso.get()
                                .load(animal.image)
                                .into(binding.animalImage)
                            binding.chooseImage.setText(R.string.change_animal_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}
