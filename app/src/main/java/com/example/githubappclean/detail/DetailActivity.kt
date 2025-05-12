package com.example.githubappclean.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubappclean.MainActivity
import com.example.githubappclean.R
import com.example.module.core.data.Resource
import com.example.githubappclean.databinding.ActivityDetailBinding
import com.example.githubappclean.follows.SectionPageAdapter
import com.example.module.core.domain.model.DetailUser
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue
import kotlin.toString

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var person: String? = null
    private val detailViewModel: DetailViewModel by viewModel()
    private var isFavorite: Boolean = false
    private var inDetailUser: DetailUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        person = intent.extras?.getString(MainActivity.PARCEL_LOGIN) as String
        lifecycleScope.launch {
            launch {
                detailViewModel.getDetails(person.toString())
                    .observe(this@DetailActivity) { detailUser ->
                        Log.d(TAG, "detailUser $detailUser")
                        onDetailsReceived(detailUser)
                    }
            }

            launch {
                detailViewModel.isFavoriteGithub(person ?: "").collect { state ->
                    setStatusFavorite(state)
                    isFavorite = state
                }
            }
        }

        binding.favorite.setOnClickListener {
            if (isFavorite) {
                inDetailUser?.let { detailViewModel.setFavoriteUser(it, false) }
                setStatusFavorite(false)
                Toast.makeText(this, "deleted from favorite", Toast.LENGTH_SHORT).show()
            } else {
                inDetailUser?.let {
                    detailViewModel.setFavoriteUser(it, true)
                }
                setStatusFavorite(true)
                Toast.makeText(this, "added to favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setTabLayoutAdapter() {
        val viewPager: ViewPager2 = binding.detailViewPager
        val tabs: TabLayout = binding.detailTabs
        viewPager.adapter = SectionPageAdapter(this, person!!)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_DETAIL[position])
        }.attach()
    }

    private fun TextView.setAndVisible(text: String?) {
        if (!text.isNullOrBlank()) {
            this.text = text
            this.visibility = View.VISIBLE
        }
    }

    private fun onDetailsReceived(result: Resource<DetailUser>) {
        when (result) {
            is Resource.Loading -> showLoading(true)
            is Resource.Error -> {
                errorOccurred()
                binding.detailNoInternet.visibility = View.VISIBLE
                binding.detailNoInternet.text = getString(R.string.error)
                binding.favorite.visibility = View.INVISIBLE
                showLoading(false)
            }

            is Resource.Success -> {
                parseUserDetail(result.data)
                setTabLayoutAdapter()
                binding.favorite.visibility = View.VISIBLE
                showLoading(false)
                result.data.let { userDetail ->
                    inDetailUser = userDetail
                }
            }
        }
    }

    private fun parseUserDetail(user: DetailUser?) {
        binding.apply {
            detailUsername.setAndVisible(user!!.login)
            detailFollowersValue.setAndVisible(user.followers.toString())
            detailFollowingValue.setAndVisible(user.following.toString())
            detailRepoValue.setAndVisible(user.publicRepos.toString())
            detailName.setAndVisible(user.name)
            detailCompany.setAndVisible(user.company)
            detailLocation.setAndVisible(user.location)
            detailImage.visibility = View.VISIBLE
            detailFollowing.visibility = View.VISIBLE
            detailFollowers.visibility = View.VISIBLE
            detailRepo.visibility = View.VISIBLE
            detailTabs.visibility = View.VISIBLE
            detailViewPager.visibility = View.VISIBLE
            appCompatImageView.visibility = View.VISIBLE
            favorite.visibility = View.VISIBLE
            Glide
                .with(applicationContext)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_baseline_person)
                .into(detailImage)
        }
    }

    private fun errorOccurred() {
        binding.apply {
            detailTabs.visibility = View.INVISIBLE
            detailViewPager.visibility = View.INVISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                detailViewPager.visibility = View.INVISIBLE
                binding.favorite.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                detailViewPager.visibility = View.VISIBLE
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.favorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24))
        } else {
            binding.favorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24))
        }
    }

    companion object {
        @StringRes
        private val TAB_DETAIL = intArrayOf(
            R.string.tab_1
        )

        private val TAG = DetailActivity::class.java.simpleName
    }
}