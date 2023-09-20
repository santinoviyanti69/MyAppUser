package com.belajar.myappuser.utils.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.belajar.myappuser.R
import com.belajar.myappuser.data.remote.Api
import com.belajar.myappuser.data.remote.Service
import com.belajar.myappuser.data.repository.UserRepository
import com.belajar.myappuser.data.room.DataBase
import com.belajar.myappuser.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class DetailActivity : AppCompatActivity() {

    //Mendeklarasikan variable binding untuk mereferensikan Binding
    //Mendeklarasikan variable binding untuk mereferensikan database
    //Mendeklarasikan variable binding untuk mereferensikan viewmodel
    private lateinit var binding: ActivityDetailBinding
    private val dataBase: DataBase by lazy {
        DataBase.getDatabase(this)
    }

    private val viewModelDetail: DetailViewModel by viewModels {
        DetailViewModelFactory(
            UserRepository(
                Api.createService(Service::class.java), dataBase.Dao()
            )
        )
    }

    //kondisi awal saat DetailActivity baru diciptakan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detail)

        //intent sebagai penghubung interaksi antar Activity di aplikasi Android
        val username = intent.getStringExtra("EXTRA_ID")
        viewModelDetail.getDetailUser(username)

        //untuk membantu mendebug di logcat
        Log.d("detail", username.toString())

        viewModelDetail.detailUser.observe(this) {

            Glide.with(this)
                .load(it.avatar_url)
                .transform(CenterInside(), RoundedCorners(24))
                .into(binding.image)


            binding.nama.text = it.login

            //untuk membantu mendebug di logcat
            Log.d("DetailUser", it.isFavorite.toString())

            if(it.isFavorite){
                binding.btnFavorite.changeIconColor(R.color.red)

                Toast.makeText(this, "Berhasil Menambahkan ke Favorite", Toast.LENGTH_LONG).show()

            }else{
                binding.btnFavorite.changeIconColor(R.color.white)

                Toast.makeText(this, "Berhasil Membatalkan Favorite", Toast.LENGTH_LONG).show()

            }

        }
        binding.btnFavorite.setOnClickListener {
            Log.d("buttonFavorite", "sudah diklik")
            viewModelDetail.setFavorite()
        }


    }

    // untuk menentukan kejadian pada masing-masing opsi dengan mendapatkan id pada setiap menu item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

//untuk mengganti warna ikon pada tombol gambar
fun ImageButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}

