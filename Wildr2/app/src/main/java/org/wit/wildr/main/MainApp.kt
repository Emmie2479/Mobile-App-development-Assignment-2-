package org.wit.wildr.main

import android.app.Application
import org.wit.wildr.models.WildrModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    val animals = ArrayList<WildrModel>()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Wildr started")
    }
}