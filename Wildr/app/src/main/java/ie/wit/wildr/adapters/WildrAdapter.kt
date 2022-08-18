package ie.wit.wildr.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.wildr.databinding.CardAnimalBinding
import ie.wit.wildr.models.AnimalModel

interface WildrListener {
    fun onWildrClick(animal: AnimalModel)
}

class WildrAdapter constructor(private var animals: List<AnimalModel>,
                               private val listener: WildrListener) :
    RecyclerView.Adapter<WildrAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardAnimalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val animal = animals[holder.adapterPosition]
        holder.bind(animal, listener)
    }

    override fun getItemCount(): Int = animals.size

    class MainHolder(private val binding : CardAnimalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animal: AnimalModel, listener: WildrListener) {
            binding.animalType.text = animal.type
            binding.animalName.text = animal.name
            binding.animalSex.text = animal.sex
            Picasso.get().load(animal.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onWildrClick(animal) }
        }
    }
}