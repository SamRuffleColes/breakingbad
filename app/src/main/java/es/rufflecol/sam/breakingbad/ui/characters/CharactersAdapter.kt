package es.rufflecol.sam.breakingbad.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.rufflecol.sam.breakingbad.data.repository.entity.CharacterEntity
import es.rufflecol.sam.breakingbad.databinding.ListItemCharacterBinding


class CharactersAdapter :
    ListAdapter<CharacterEntity, RecyclerView.ViewHolder>(BreakingBadCharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterViewHolder(
            ListItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val venue = getItem(position)
        (holder as CharacterViewHolder).bind(venue)
    }

    class CharacterViewHolder(private val binding: ListItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                Toast.makeText(it.context, "${binding.character?.name} clicked", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        fun bind(item: CharacterEntity) {
            binding.apply {
                character = item
                executePendingBindings()
            }
        }

    }

}

private class BreakingBadCharacterDiffCallback : DiffUtil.ItemCallback<CharacterEntity>() {

    override fun areItemsTheSame(
        oldItem: CharacterEntity,
        newItem: CharacterEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharacterEntity,
        newItem: CharacterEntity
    ): Boolean {
        return oldItem == newItem
    }

}