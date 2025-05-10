package com.example.githubappclean.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubappclean.MainActivity
import com.example.githubappclean.MainActivity.Companion.PARCEL_LOGIN
import com.example.githubappclean.R
import com.example.githubappclean.core.data.Resource
import com.example.githubappclean.core.data.source.remote.response.SimpleUser
import com.example.githubappclean.core.ui.SearchAdapter
import com.example.githubappclean.databinding.ActivitySearchBinding
import com.example.githubappclean.detail.DetailActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class SearchActivity : AppCompatActivity() {
    private var search: String? = null
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        search = intent.extras?.getString(MainActivity.PARCEL_SEARCH) as String
        lifecycleScope.launch {
            launch {
                searchViewModel.searchUser(search.toString()).observe(this@SearchActivity) { searchUser ->
                    Log.d(TAG, "detailUser $searchUser")
                    showRecycle(searchUser)
                }
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

    private fun showRecycle(result: Resource<List<SimpleUser>>?) {
        when (result) {
            is Resource.Loading -> showLoading(true)
            is Resource.Success -> {
                val githubAdapter = result.data?.let { SearchAdapter(it) }

                binding.myUsers.apply {
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = githubAdapter
                    setHasFixedSize(true)
                }

                githubAdapter?.setOnItemClickCallback(object :
                    SearchAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: SimpleUser) {
                        Intent(
                            this@SearchActivity,
                            DetailActivity::class.java
                        ).apply {
                            putExtra(PARCEL_LOGIN, data.login)
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

    companion object {
        private val TAG = SearchActivity::class.java.simpleName
    }
}
