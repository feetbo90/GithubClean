package com.example.githubappclean.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubappclean.R
import com.example.module.core.domain.model.UserGithub
import com.example.module.core.ui.GithubAdapter
import com.example.githubappclean.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.module.core.data.Resource
import com.example.githubappclean.detail.DetailActivity
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import com.example.githubappclean.databinding.DialogSearchBinding
import com.example.githubappclean.favorite.FavoriteActivity


class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModel()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as? androidx.appcompat.app.AppCompatActivity)?.apply {
            setSupportActionBar(binding.myToolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner)

        mainViewModel.githubUser.observe(viewLifecycleOwner) { githubUser ->
            showRecycle(githubUser)
        }
    }

    private fun showRecycle(result: Resource<List<UserGithub>>?) {
        when (result) {
            is Resource.Loading -> showLoading(true)
            is Resource.Success -> {
                val githubAdapter = result.data?.let { GithubAdapter(it) }
                binding.myUsers.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = githubAdapter
                    setHasFixedSize(true)
                }
                githubAdapter?.setOnItemClickCallback(object :
                    GithubAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: UserGithub) {
                        Intent(
                            requireContext(),
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.main_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.action_search -> {
                    showInputDialog()
                    true
                }
                R.id.action_favorite -> {
                    startActivity(Intent(requireContext(), FavoriteActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    fun showInputDialog() {
        // Inflate binding
        val binding = DialogSearchBinding.inflate(layoutInflater)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(getString(R.string.search_user))
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.search)) { dialog, _ ->
                val inputText = binding.editTextInput.text.toString()
                val mBundle = Bundle()
                mBundle.putString(PARCEL_SEARCH, inputText)
                findNavController().navigate(R.id.action_mainFragment_to_searchFragment, mBundle)
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()

        dialog.setOnShowListener {
            binding.editTextInput.requestFocus()
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
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
