package com.example.favorite.ui.fav

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.repo.FavoriteRepository
import com.example.core.domain.usecase.FavoriteInteractor
import com.example.core.domain.usecase.FavoriteUseCase
import com.example.favorite.databinding.ActivityFavoriteBinding
import com.example.core.ui.main.UserAdapter
import com.example.project_one.R
import com.example.projectone.ui.userdetail.DetailUserActivity
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var broadcastReceiver: BroadcastReceiver
    private lateinit var tvPowerStatus: TextView

    private val adapter by lazy {
        UserAdapter{
            Intent(this@FavoriteActivity, DetailUserActivity::class.java).apply {
                putExtra("userItems", it)
                startActivity(this)
            }
        }
    }

    private val favoriteUseCase: FavoriteUseCase by lazy {
        FavoriteInteractor(FavoriteRepository(application))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.FavoriteListRv.layoutManager = LinearLayoutManager(this)
        binding.FavoriteListRv.adapter = adapter

        // Inisialisasi ViewModel
        val viewModel = ViewModelProvider(this, FavoriteViewModelFactory(favoriteUseCase)).get(
            FavoriteViewModel::class.java
        )

        // Mengamati LiveData dari ViewModel
        lifecycleScope.launch {
            viewModel.getAllFavUser().collect { favUsers ->
                // Update data dalam adapter
                adapter.setData(favUsers)
            }
        }

        // leak canary
        tvPowerStatus = binding.tvPowerStatus

    }

    // leak canary
    private fun registerBroadCastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    Intent.ACTION_POWER_CONNECTED -> {
                        tvPowerStatus.text = getString(R.string.power_connected)
                    }
                    Intent.ACTION_POWER_DISCONNECTED -> {
                        tvPowerStatus.text = getString(R.string.power_disconnected)
                    }
                }
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}

