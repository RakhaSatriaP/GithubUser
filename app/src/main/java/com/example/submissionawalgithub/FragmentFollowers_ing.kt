package com.example.submissionawalgithub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawalgithub.databinding.FragmentFollowersIngBinding

class FragmentFollowers_ing : Fragment() {

    companion object {
        const val ING = 0
        const val ERS = 1
        fun newInstance(type: Int) = FragmentFollowers_ing().apply {
            this.type = type
        }
    }

    var type = 0
    private var binding: FragmentFollowersIngBinding? = null
    private val adapter by lazy {
        Adapter {
        }
    }

    private val viewModel by activityViewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.fol?.apply {
            this.layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FragmentFollowers_ing.adapter
        }

        when (type) {
            ERS -> {
                viewModel.followersUser.observe(viewLifecycleOwner, this::hasilfol)
            }
            ING -> {
                viewModel.followingUser.observe(viewLifecycleOwner, this::hasilfol)
            }
        }
    }

    private fun hasilfol(state: HasilRespon) {
        when (state) {
            is HasilRespon.Sukses<*> -> {
                adapter.setData(state.data as MutableList<Response.Item>)
            }
            is HasilRespon.Loading -> {
                binding?.loading?.isVisible = state.L
            }
            is HasilRespon.Error -> {
                Toast.makeText(requireActivity(), state.E.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersIngBinding.inflate(layoutInflater)
        return binding?.root
    }

}