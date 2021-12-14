package org.wit.wildr.models
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
//data class WildrModel(var title: String = "")
//data class WildrModel(var title: String = "", var description: String = "")
var image: Uri = Uri.EMPTY
@Parcelize
data class WildrModel(var id: Long = 0,
                          var title: String = "",
                          var description: String = "",
                          var image: Uri = Uri.EMPTY) : Parcelable