package es.rufflecol.sam.breakingbad.ui.characters

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import es.rufflecol.sam.breakingbad.R
import es.rufflecol.sam.breakingbad.databinding.ActivityCharactersBinding
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
        setupNotificationUI(binding.root)
    }

    private fun setupListUI(recyclerView: RecyclerView) {
        val adapter = CharactersAdapter()
        recyclerView.adapter = adapter
        viewModel.characters.observe(this, {
            adapter.submitList(it)
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
        val searchView = searchItem.actionView as SearchView
        setupSearchUI(searchView)

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
}