package ie.wit.wildr.main

import android.app.Application
import ie.wit.wildr.models.AnimalJSONStore
import ie.wit.wildr.models.AnimalMemStore
import ie.wit.wildr.models.AnimalStore
import timber.log.Timber
import timber.log.Timber.i

class WildrApp : Application() {

    lateinit var animals: AnimalStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        animals = AnimalJSONStore(applicationContext)
        i("Wildr started")
    }
}