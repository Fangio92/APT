package com.dizdarevic.apt.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dizdarevic.apt.*
import com.dizdarevic.apt.repository.RetrofitService.Companion.getInstance
import com.dizdarevic.apt.databinding.FragmentUsersBinding
import com.dizdarevic.apt.ui.viewmodels.MainViewModel
import com.dizdarevic.apt.models.User
import com.dizdarevic.apt.repository.MainRepository
import com.dizdarevic.apt.ui.adapters.OnRecyclerViewItemClicked
import com.dizdarevic.apt.ui.adapters.RVUserAdapter
import com.dizdarevic.apt.ui.viewmodels.MainViewModelFactory


class UsersFragment : Fragment() {

    lateinit var binding: FragmentUsersBinding
    val adapter = RVUserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);

    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding=FragmentUsersBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu)
        (activity as MainActivity?)?.setSupportActionBar(binding.toolbar)

        val appPreferences = AppPreferences(requireContext())

        appPreferences.userNameFlow.asLiveData().observe(requireActivity(), { username ->
            if (username == "") {
                findNavController().navigate(R.id.action_usersFragment_to_loginFragment3)
            }
        })

        val onRecyclerViewItemClicked=object : OnRecyclerViewItemClicked {
            override fun onItemClick(user: User) {
                findNavController().navigate(R.id.action_usersFragment_to_detailsFragment)
            }
        }

        val viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(MainRepository(getInstance()))).get(
            MainViewModel::class.java)

        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager= LinearLayoutManager(requireContext())
        viewModel.users.observe(viewLifecycleOwner, Observer {
            adapter.setUserList(it, onRecyclerViewItemClicked)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem? = menu.findItem(R.id.app_bar_search)

        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}