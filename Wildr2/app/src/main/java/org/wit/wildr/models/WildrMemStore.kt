package org.wit.wildr.models

import timber.log.Timber.i
var lastId = 0L
internal fun getId(): Long {
    return lastId++
}
class WildrMemStore : WildrStore {

    val animals = ArrayList<WildrModel>()

    override fun findAll(): List<WildrModel> {
        return animals
    }

    override fun create(animal: WildrModel) {
        animal.id = getId()
        animals.add(animal)
        logAll()
    }
    override fun update(animal: WildrModel) {
        var foundAnimal: WildrModel? = animals.find { p -> p.id == animal.id }
        if (foundAnimal != null) {
            foundAnimal.title = animal.title
            foundAnimal.description = animal.description
            foundAnimal.image = animal.image
            logAll()
        }
    }

    override fun delete(animal: WildrModel) {
        animals.remove(animal)
    }
    fun logAll() {
        animals.forEach{ i("${it}") }

    }
}