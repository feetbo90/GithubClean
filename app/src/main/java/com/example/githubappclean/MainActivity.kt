package com.example.githubappclean

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubappclean.core.data.Resource
import com.example.githubappclean.core.domain.model.UserGithub
import com.example.githubappclean.core.ui.GithubAdapter
import com.example.githubappclean.databinding.ActivityMainBinding
import com.example.githubappclean.databinding.DialogSearchBinding
import com.example.githubappclean.detail.DetailActivity
import com.example.githubappclean.favorite.FavoriteActivity
import com.example.githubappclean.main.MainViewModel
import com.example.githubappclean.search.SearchActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mainViewModel.githubUser.observe(this) { githubUser ->
            showRecycle(githubUser)
        }

        /* binding.search.setOnClickListener {
            showInputDialog()
        } */
    }

    private fun showRecycle(result: Resource<List<UserGithub>>?) {
        when (result) {
            is Resource.Loading -> showLoading(true)
            is Resource.Success -> {
                val githubAdapter = result.data?.let { GithubAdapter(it) }

                binding.myUsers.apply {
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = githubAdapter
                    setHasFixedSize(true)
                }

                githubAdapter?.setOnItemClickCallback(object :
                    GithubAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: UserGithub) {
                        Intent(
                            this@MainActivity,
                            DetailActivity::class.java
                        ).apply {
                            putExtra(PARCEL_LOGIN, data.login)
                            putExtra(PARCEL_ID, data.id)
                        }.also {
                            startActivity(it)
                        }
                    }
                })
                showLoading(false)
            }

            is Resource.Error -> {
                binding.message.visibility = View.VISIBLE
                binding.message.text = getString(R.string.error)
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                showLoading(false)
            }

            null -> {
                binding.message.visibility = View.VISIBLE
                binding.message.text = getString(R.string.error)
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.myLoading.visibility = View.VISIBLE
            binding.myUsers.visibility = View.GONE
        } else {
            binding.myLoading.visibility = View.GONE
            binding.myUsers.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // Aksi ketika menu Settings dipilih
                showInputDialog()
                true
            }
            R.id.action_favorite -> {
                // Aksi ketika menu Favorite dipilih
                Intent(
                    this@MainActivity,
                    FavoriteActivity::class.java
                ).also {
                    startActivity(it)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    fun showInputDialog() {
        // Inflate binding
        val binding = DialogSearchBinding.inflate(layoutInflater)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(binding.root)
            .setTitle(getString(R.string.search_user))
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.search)) { dialog, _ ->
                val inputText = binding.editTextInput.text.toString()
                Intent(
                    this@MainActivity,
                    SearchActivity::class.java
                ).apply {
                    putExtra(PARCEL_SEARCH, inputText)
                }.also {
                    startActivity(it)
                }
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()

        dialog.setOnShowListener {
            binding.editTextInput.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.editTextInput, InputMethodManager.SHOW_IMPLICIT)
        }

        dialog.show()
    }



    companion object {
        const val PARCEL_LOGIN = "parcel_login"
        const val PARCEL_ID = "parcel_id"

        const val PARCEL_SEARCH = "parcel_search"
    }
}