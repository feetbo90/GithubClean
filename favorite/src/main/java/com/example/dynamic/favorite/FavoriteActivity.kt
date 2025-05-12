package com.example.dynamic.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dynamic.favorite.databinding.ActivityFavoriteBinding
import com.example.dynamic.favorite.di.favoritesModule
import com.example.module.core.ui.SearchAdapter
import com.example.githubappclean.detail.DetailActivity
import com.example.module.core.domain.model.SimpleUsers
import com.example.module.core.utils.UserImageLoader
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import kotlin.getValue

class FavoriteActivity : AppCompatActivity() {
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: ActivityFavoriteBinding
    private val imageLoader: UserImageLoader by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(favoritesModule)

        favoriteViewModel.getFavoriteUsers().observe(this) { githubUser ->
            showRecycle(githubUser)
        }
    }

    private fun showRecycle(result: List<SimpleUsers>?) {

                val githubAdapter = result?.let { SearchAdapter(it, imageLoader) }

                binding.myUsers.apply {
                    layoutManager = LinearLayoutManager(applicationContext)
                    adapter = githubAdapter
                    setHasFixedSize(true)
                }

                githubAdapter?.setOnItemClickCallback(object :
                    SearchAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: SimpleUsers) {
                        Intent(
                            this@FavoriteActivity,
                            DetailActivity::class.java
                        ).apply {
                            putExtra(PARCEL_LOGIN, data.login)
                            putExtra(PARCEL_ID, data.id)
                        }.also {
                            startActivity(it)
                        }
                    }
                })
    }

    companion object {
        const val PARCEL_LOGIN = "parcel_login"
        const val PARCEL_ID = "parcel_id"
    }
}