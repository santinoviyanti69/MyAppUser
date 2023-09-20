package com.belajar.myappuser.utils.wishlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.belajar.myappuser.R
import com.belajar.myappuser.data.model.User
import com.belajar.myappuser.data.remote.Api
import com.belajar.myappuser.data.remote.Service
import com.belajar.myappuser.data.repository.UserRepository
import com.belajar.myappuser.data.room.DataBase
import com.belajar.myappuser.databinding.ActivityWishlistBinding
import com.belajar.myappuser.utils.detail.DetailActivity
import com.belajar.myappuser.utils.list.UserAdapter
import com.belajar.myappuser.utils.list.UserListAdapterListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishlistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWishlistBinding
    private lateinit var viewModel: WishlistViewModel
    private val database : DataBase by lazy {
        DataBase.getDatabase(this)
    }

    //kondisi awal saat ActivityWishlist baru diciptakan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.wishlist)

        ViewModelProvider(
            this,
            WishlistViewModelFactory(
                UserRepository(
                    Api.createService(Service::class.java),database.Dao()),
            )
        )[WishlistViewModel::class.java].also { viewModel = it}

        // untuk mengambil listuser item
        val items = viewModel.itemsFavorite
        binding.bindAdapter(items)
    }

    //untuk menghubungkan PagingDataAdapter ke view
    private fun ActivityWishlistBinding.bindAdapter(items: Flow<PagingData<User>>) {
        val adapter = UserAdapter(object : UserListAdapterListener {
            override fun onClickUser(user: User) {
                startActivity(
                    Intent(this@WishlistActivity, DetailActivity::class.java)
                        .putExtra("EXTRA_ID", user.login)
                )
            }
        })
        binding.Favorite.adapter = adapter
        binding.Favorite.addItemDecoration(
            DividerItemDecoration(this@WishlistActivity, DividerItemDecoration.VERTICAL)
        )
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collect {
                    adapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                adapter.refresh()
                }
            }
        }



    // untuk menentukan kejadian pada masing-masing opsi dengan mendapatkan id pada setiap menu item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        //mengembalikan nilai yang diterima dari metode onOptionsItemSelected di superclass.
        return super.onOptionsItemSelected(item)
    }
}