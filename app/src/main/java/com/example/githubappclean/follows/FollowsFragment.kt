package com.example.githubappclean.follows

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubappclean.MainActivity
import com.example.githubappclean.core.data.Resource
import com.example.githubappclean.core.data.source.remote.response.SimpleUser
import com.example.githubappclean.core.ui.FollowsAdapter
import com.example.githubappclean.databinding.FragmentFollowsBinding
import com.example.githubappclean.detail.DetailActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

/**
 * A simple [Fragment] subclass.
 * Use the [FollowsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowsFragment : Fragment() {

    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!
    private val followsViewModel: FollowsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(ARG_USERNAME, "")

        if (index == 1) {
            username?.let {
                val mIndex = 1
                setViewModel(it, mIndex)
            }
        }
    }

    private fun goToDetail(user: SimpleUser) {
        Intent(activity, DetailActivity::class.java).apply {
            putExtra(MainActivity.PARCEL_LOGIN, user.login)
        }.also {
            startActivity(it)
        }
    }

    private fun setViewModel(username: String, index: Int) {
        if (index == 1) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    followsViewModel.getUserFollowers(username).observe(viewLifecycleOwner) { followers ->
                        Log.d(TAG, "followers $followers")
                        onFollowsResultReceived(followers)
                    }
                }
            }
        }
    }

    private fun onFollowsResultReceived(result: Resource<List<SimpleUser>>) {
        when (result) {
            is Resource.Loading -> showLoading(true)
            is Resource.Error -> {
                binding.status.visibility = View.VISIBLE
                binding.status.text = "An Error is Occurred"
                showLoading(false)
            }
            is Resource.Success -> {
                showFollows(result.data)
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.loading.visibility = View.VISIBLE
        else binding.loading.visibility = View.GONE
    }

    private fun showFollows(users: List<SimpleUser>?) {
        if (users?.isNotEmpty()!!) {
            val linearLayoutManager = LinearLayoutManager(activity)
            val listAdapter = FollowsAdapter(users)

            binding.users.apply {
                layoutManager = linearLayoutManager
                adapter = listAdapter
                setHasFixedSize(true)
            }

            listAdapter.setOnItemClickCallback(object :
                FollowsAdapter.OnItemClickCallback {
                override fun onItemClicked(data: SimpleUser) {
                    goToDetail(data)
                }

            })
        } else binding.status.visibility = View.VISIBLE
    }


    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_USERNAME = "username"
        private val TAG = FollowsFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(index: Int, username: String) =
            FollowsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putString(ARG_USERNAME, username)
                }
            }
    }
}