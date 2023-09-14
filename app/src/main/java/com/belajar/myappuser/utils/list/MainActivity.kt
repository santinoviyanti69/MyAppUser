package com.belajar.myappuser.utils.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
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
import com.belajar.myappuser.data.room.Dao
import com.belajar.myappuser.databinding.ActivityMainBinding
import com.belajar.myappuser.utils.detail.DetailActivity
import com.belajar.myappuser.utils.wishlist.WishlistActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    //Mendeklarasikan variable binding untuk mereferensikan Binding
    //Mendeklarasikan variable binding untuk mereferensikan viewmodel
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    //kondisi awal saat MainActivity baru diciptakan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewModelProvider(
            this,
            ProductListViewModelFactory(
                UserRepository(Api.createService(Service::class.java), (Api.createService(Dao::class.java)))
            )

        )[MainViewModel::class.java].also { viewModel = it }

        //untuk mengambil user item
        val items = viewModel.items
        binding.bindAdapter(items)

    }

    //untuk menghubungkan PagingDataAdapter ke view
    private fun ActivityMainBinding.bindAdapter(items: Flow<PagingData<User>>) {
        val adapter = UserAdapter(object : UserListAdapterListener {
            override fun onClickUser(user: User) {
                startActivity(
                    Intent(this@MainActivity, DetailActivity::class.java)
                        .putExtra("EXTRA_ID", user.login)

                )
            }
    })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        )
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                items.collect{
                    adapter.submitData(it)
                }
            }
        }
    }

    // untuk menampilkan layout menu di ecatalog activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_catalog, menu)
            return true
        }

    // untuk menentukan kejadian pada masing-masing opsi dengan mendapatkan id pada setiap menu item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_favorites -> {
                    val intent = Intent(this, WishlistActivity::class.java).apply {
                    }
                    startActivity(intent)
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        }
}
