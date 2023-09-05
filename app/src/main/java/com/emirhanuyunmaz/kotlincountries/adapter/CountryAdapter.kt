package com.emirhanuyunmaz.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.emirhanuyunmaz.kotlincountries.databinding.ItemCountryBinding
import com.emirhanuyunmaz.kotlincountries.model.Country
import com.emirhanuyunmaz.kotlincountries.util.downloadFromImage
import com.emirhanuyunmaz.kotlincountries.util.placeHolderProgressBar
import com.emirhanuyunmaz.kotlincountries.view.FeedFragmentDirections


class CountryAdapter(var countryList:ArrayList<Country>) :RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){


    class CountryViewHolder(var binding:ItemCountryBinding) :RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding=ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.countryNameText.text=countryList[position].countryName
        holder.binding.countryRegionText.text=countryList[position].countryRegion
        holder.itemView.setOnClickListener{
            val aciton=FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            aciton.countryUUid=countryList[position].uuid
            Navigation.findNavController(it).navigate(aciton)
        }
        holder.binding.imageViewFeed.downloadFromImage(countryList[position].imageURL!!,
            placeHolderProgressBar(holder.itemView.context)
        )
    }

    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}