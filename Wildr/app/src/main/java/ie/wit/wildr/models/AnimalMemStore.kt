package ie.wit.wildr.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class AnimalMemStore : AnimalStore {

    val animals = ArrayList<AnimalModel>()

    override fun findAll(): List<AnimalModel> {
        return animals
    }

    override fun create(animal: AnimalModel) {
        animal.id = getId()
        animals.add(animal)
        logAll()
    }

    override fun update(animal: AnimalModel) {
        var foundAnimal: AnimalModel? = animals.find { p -> p.id == animal.id }
        if (foundAnimal != null) {
            foundAnimal.type = animal.type
            foundAnimal.name = animal.name
            foundAnimal.sex = animal.sex
            foundAnimal.image = animal.image
            logAll()
        }
    }

    override fun delete(animal: AnimalModel) {
        animals.remove(animal)
    }

    private fun logAll() {
        animals.forEach { i("$it") }
    }
}