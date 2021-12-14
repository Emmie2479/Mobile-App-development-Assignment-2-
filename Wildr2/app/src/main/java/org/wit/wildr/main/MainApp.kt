package org.wit.wildr.main

import android.app.Application
import org.wit.wildr.models.WildrJSONStore
import org.wit.wildr.models.WildrMemStore
import org.wit.wildr.models.WildrModel
import org.wit.wildr.models.WildrStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var animals: WildrStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        animals = WildrJSONStore(applicationContext)
        i("Wildr started")
    }
}