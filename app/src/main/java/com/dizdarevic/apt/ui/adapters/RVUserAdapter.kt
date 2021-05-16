package com.dizdarevic.apt.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dizdarevic.apt.R
import com.dizdarevic.apt.databinding.AdapterUserBinding
import com.dizdarevic.apt.models.User
import com.dizdarevic.apt.models.RandomUser



class RVUserAdapter:RecyclerView.Adapter<MainViewHolder>(), Filterable {
    private var userList: List<User>?=null
    private var filteredUserList: List<User>?=null
    private var onRecyclerViewItemClicked: OnRecyclerViewItemClicked?=null

    fun setUserList(randomUser: RandomUser,onRecyclerViewItemClicked: OnRecyclerViewItemClicked? ) {
        this.userList = randomUser.userList
        this.filteredUserList=this.userList
        this.onRecyclerViewItemClicked=onRecyclerViewItemClicked
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterUserBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val user = filteredUserList!![position]
        holder.binding.name.text = user.getName()
        holder.binding.country.text=user.nat
        Glide.with(holder.itemView.context).load(user.picture.medium).into(holder.binding.profileImage)
        holder.binding.view.setOnClickListener {
            onRecyclerViewItemClicked?.onItemClick(user)
        }
        holder.binding.view.setAnimation(AnimationUtils.loadAnimation(holder.binding.view.context,
            R.anim.fade_scale
        ));
    }

    override fun getItemCount(): Int {
        if(filteredUserList!=null)
            return filteredUserList?.size!!
        return 0
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filteredUserList = userList
                } else {
                    val filteredList: MutableList<User> = ArrayList()
                    userList?.forEachIndexed { index, user ->
                        val str=user.name.first+" "+user.name.last
                        if (str.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(user)
                        }
                    }
                    filteredUserList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredUserList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredUserList = filterResults.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }


}
class MainViewHolder(val binding: AdapterUserBinding) : RecyclerView.ViewHolder(binding.root) {

}