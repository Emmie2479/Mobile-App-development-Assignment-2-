package org.wit.wildr.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import ie.wit.wildr.R
import timber.log.Timber

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    Timber.i("Select image")
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_animal_image.toString())
    intentLauncher.launch(chooseFile)
}