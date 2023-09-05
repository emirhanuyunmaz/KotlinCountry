package com.emirhanuyunmaz.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.emirhanuyunmaz.kotlincountries.R
import com.emirhanuyunmaz.kotlincountries.databinding.FragmentCountryBinding
import com.emirhanuyunmaz.kotlincountries.util.downloadFromImage
import com.emirhanuyunmaz.kotlincountries.util.placeHolderProgressBar
import com.emirhanuyunmaz.kotlincountries.viewmodel.CountryViewModel


class CountryFragment : Fragment() {

    private lateinit var binding:FragmentCountryBinding
    private lateinit var viewModel: CountryViewModel
    private var countryUUid=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCountryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.let {
            countryUUid= CountryFragmentArgs.fromBundle(it!!).countryUUid
        }

        viewModel=ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUUid)

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country->
            country?.let {
                binding.countryNameDetailText.text=it.countryName
                binding.countryCapitalDetailText.text=it.countryCapital
                binding.countryCurrencyDetailText.text=it.countryCurrency
                binding.countryLanguageDetailText.text=it.countryLanguage
                binding.countryRegionDetailText.text=it.countryRegion
                context.let {c->
                    binding.countryFlagImageView.downloadFromImage(it.imageURL!!,
                        placeHolderProgressBar(c!!)
                    )
                }
            }
        })

    }

}