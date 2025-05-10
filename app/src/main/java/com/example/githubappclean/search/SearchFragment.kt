package com.example.githubappclean.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubappclean.MainActivity
import com.example.githubappclean.MainActivity.Companion.PARCEL_LOGIN
import com.example.githubappclean.R
import com.example.module.core.data.Resource
import com.example.module.core.data.source.remote.response.SimpleUser
import com.example.module.core.ui.SearchAdapter
import com.example.githubappclean.databinding.FragmentSearchBinding
import com.example.githubappclean.detail.DetailActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    private var search: String? = null
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            search = it.getString(PARCEL_SEARCH)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            searchViewModel.searchUser(search.toString()).observe(viewLifecycleOwner) { searchUser ->
                showRecycle(searchUser)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showRecycle(result: Resource<List<SimpleUser>>?) {
        when (result) {
            is Resource.Loading -> showLoading(true)
            is Resource.Success -> {
                val githubAdapter = result.data?.let { SearchAdapter(it) }

                binding.myUsers.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = githubAdapter
                    setHasFixedSize(true)
                }

                githubAdapter?.setOnItemClickCallback(object :
                    SearchAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: SimpleUser) {
                        Intent(
                            activity,
                            DetailActivity::class.java
                        ).apply {
                            putExtra(PARCEL_LOGIN, data.login)
                            putExtra(MainActivity.PARCEL_ID, data.id)
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
                Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
                showLoading(false)
            }

            null -> {
                binding.message.visibility = View.VISIBLE
                binding.message.text = getString(R.string.error)
                Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
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

    companion object {
        const val PARCEL_SEARCH = "parcel_search"
    }
}