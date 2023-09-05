package com.emirhanuyunmaz.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirhanuyunmaz.kotlincountries.adapter.CountryAdapter
import com.emirhanuyunmaz.kotlincountries.databinding.FragmentFeedBinding
import com.emirhanuyunmaz.kotlincountries.viewmodel.FeedViewModel


class FeedFragment : Fragment() {

    private lateinit var binding:FragmentFeedBinding
    private lateinit var viewModel: FeedViewModel
    private var countryAdapter=CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(this).get(FeedViewModel :: class.java)

        //ViewModelProviders
        viewModel.refreshData()
        binding.countryList.layoutManager=LinearLayoutManager(this.context)
        binding.countryList.adapter=countryAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility=View.GONE
            binding.countryErrorText.visibility=View.GONE
            binding.countryLoading.visibility=View.VISIBLE
            viewModel.refreshAPI()
            binding.swipeRefreshLayout.isRefreshing=false
        }
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->

            countries?.let {
                binding.countryList.visibility=View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }

            viewModel.countryError.observe(viewLifecycleOwner, Observer { error->

                error?.let {
                    if (it){
                        binding.countryErrorText.visibility=View.VISIBLE
                    }else{
                        binding.countryErrorText.visibility=View.GONE
                    }
                }

            })

            viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading->

                loading?.let {
                    if (it){
                        binding.countryLoading.visibility=View.VISIBLE
                        binding.countryList.visibility=View.GONE
                        binding.countryErrorText.visibility=View.GONE
                    }else{
                        binding.countryLoading.visibility=View.GONE
                    }
                }

            })


        })
    }


}