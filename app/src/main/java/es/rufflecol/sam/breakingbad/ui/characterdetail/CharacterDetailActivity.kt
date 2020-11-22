package es.rufflecol.sam.breakingbad.ui.characterdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.data.repository.entity.CharacterEntity
import es.rufflecol.sam.breakingbad.databinding.ActivityCharacterDetailBinding

@AndroidEntryPoint
class CharacterDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val character = intent?.extras?.get(EXTRA_CHARACTER) as CharacterEntity
        setupDataBindingUI(character)
    }

    private fun setupDataBindingUI(character: CharacterEntity) {
        DataBindingUtil.setContentView<ActivityCharacterDetailBinding>(
            this,
            R.layout.activity_character_detail
        ).apply {
            this.character = character
            lifecycleOwner = this@CharacterDetailActivity
        }
    }

    companion object {
        const val EXTRA_CHARACTER = "CharacterDetailActivity.extraCharacter"
    }

}