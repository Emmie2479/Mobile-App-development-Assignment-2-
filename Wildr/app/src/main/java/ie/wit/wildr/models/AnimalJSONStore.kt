package ie.wit.wildr.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.wildr.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "animals.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<AnimalModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class AnimalJSONStore(private val context: Context) : AnimalStore {

    var animals = mutableListOf<AnimalModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<AnimalModel> {
        logAll()
        return animals
    }

    override fun create(animal: AnimalModel) {
        animal.id = generateRandomId()
        animals.add(animal)
        serialize()
    }


    override fun update(animal: AnimalModel) {
        val animalsList = findAll() as ArrayList<AnimalModel>
        var foundAnimal: AnimalModel? = animalsList.find { p -> p.id == animal.id }
        if (foundAnimal != null) {
            foundAnimal.type  = animal.type
            foundAnimal.name = animal.name
            foundAnimal.sex = animal.sex
            foundAnimal.image = animal.image
        }
        serialize()
    }

    override fun delete(animal: AnimalModel) {
        animals.remove(animal)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(animals, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        animals = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        animals.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}