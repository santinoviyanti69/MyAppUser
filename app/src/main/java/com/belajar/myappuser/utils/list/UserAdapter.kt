package com.belajar.myappuser.utils.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.belajar.myappuser.data.model.User
import com.belajar.myappuser.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class UserAdapter(private val listener: UserListAdapterListener): PagingDataAdapter<User, RecyclerView.ViewHolder>(
    DIFF_CALLBACK
) {
    //untuk membuat objek ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ViewHolder.onCreate(parent)
    }

    //untuk menghubungkan data yang ada dengan objek ViewHolder.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position)
        (holder as ViewHolder).onBind(user, listener)
    }

    class ViewHolder private constructor(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User?, listener: UserListAdapterListener) {

            user?.let {
                Glide.with(itemView.context).load(it.avatar_url)
                    .transform(CenterInside(), RoundedCorners(24))
                    .into(binding.image)
                binding.username.text = it.login
                binding.root.setOnClickListener {
                    listener.onClickUser(user)
                }

            }
        }

        // object dalam class
        companion object{
            fun onCreate(parent: ViewGroup): ViewHolder {
                val itemView =
                    ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(itemView)
            }
        }

    }
    // object luar class
    companion object {

        // compare data lama vs data baru
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {

            // apakah itemnya sama
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            // apakah konten itemnya sama secara keseluruhan
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}

// sebagai penghubung anatar sistem dengan user
interface UserListAdapterListener {
    fun onClickUser(user: User)
}


