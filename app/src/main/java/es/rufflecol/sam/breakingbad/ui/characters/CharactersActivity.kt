package es.rufflecol.sam.breakingbad.ui.characters

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.data.repository.entity.CharacterEntity
import es.rufflecol.sam.breakingbad.databinding.ActivityCharactersBinding
import es.rufflecol.sam.breakingbad.ui.characterdetail.CharacterDetailActivity
import es.rufflecol.sam.breakingbad.ui.util.snack


@AndroidEntryPoint
class CharactersActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBindingUI()
        viewModel.updateCharacters()
    }

    private fun setupDataBindingUI() {
        val binding = DataBindingUtil.setContentView<ActivityCharactersBinding>(
            this,
            R.layout.activity_characters
        ).apply {
            viewModel = this@CharactersActivity.viewModel
            lifecycleOwner = this@CharactersActivity
        }

        setupListUI(binding.recyclerView)
        setupFilterUI(binding.seriesChips)
        setupNotificationUI(binding.root)
    }

    private fun setupListUI(recyclerView: RecyclerView) {
        val adapter = CharactersAdapter { character ->
            startDetailActivity(character)
        }
        recyclerView.adapter = adapter
        viewModel.characters.observe(this, {
            adapter.submitList(it)
        })
    }

    private fun setupFilterUI(chipGroup: ChipGroup) {
        viewModel.series.observe(this, { allSeries ->
            chipGroup.removeAllViews()
            allSeries.map { series ->
                Chip(chipGroup.context).apply {
                    text = series
                    setChipDrawable(
                        ChipDrawable.createFromAttributes(
                            chipGroup.context,
                            null,
                            0,
                            R.style.Widget_MaterialComponents_Chip_Choice
                        )
                    )
                    isChecked = false
                }
            }.forEach { chip ->
                chipGroup.addView(chip)
            }

            chipGroup.setOnCheckedChangeListener { _, _ ->
                val checked = chipGroup.children
                    .map { it as? Chip }
                    .filterNotNull()
                    .filter { it.isChecked }
                    .firstOrNull()
                if (checked != null) {
                    viewModel.filter(checked.text.toString())
                } else {
                    viewModel.filter("")
                }
            }
        })
    }

    private fun setupNotificationUI(root: View) {
        viewModel.userNotification.observe(this, {
            it?.let {
                root.snack(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_characters, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as? SearchView
        searchView?.let { setupSearchUI(it) }

        return super.onCreateOptionsMenu(menu)
    }

    private fun setupSearchUI(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText.orEmpty())
                return true
            }
        })
    }

    private fun startDetailActivity(character: CharacterEntity) {
        val intent = Intent(this, CharacterDetailActivity::class.java).apply {
            putExtra(CharacterDetailActivity.EXTRA_CHARACTER, character)
        }
        startActivity(intent)
    }

}