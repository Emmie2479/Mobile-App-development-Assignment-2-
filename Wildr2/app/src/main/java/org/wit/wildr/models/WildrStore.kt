package org.wit.wildr.models

public interface WildrStore {
    fun findAll(): List<WildrModel>
    fun create(animal: WildrModel)
    fun update(animal: WildrModel)
    fun delete(animal: WildrModel)
}


