package com.barreto.mvvmreckt.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.barreto.mvvmreckt.R
import com.barreto.mvvmreckt.adapters.MainAdapter
import com.barreto.mvvmreckt.databinding.ActivityMainBinding
import com.barreto.mvvmreckt.repositories.MainRepository
import com.barreto.mvvmreckt.rest.RetrofitService
import com.barreto.mvvmreckt.viewmodel.main.MainViewModel
import com.barreto.mvvmreckt.viewmodel.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var viewModel : MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val adapter = MainAdapter{
        openLink(it.link)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,MainViewModelFactory(MainRepository(retrofitService))).get(
            MainViewModel::class.java
        )
        binding.recyclerview.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.liveList.observe(this, Observer {
            Log.i("Tiago","onStart")
            adapter.setLiveList(it)
        })

        viewModel.errorMessage.observe(this, Observer {mensagem ->
            Toast.makeText(this,mensagem,Toast.LENGTH_SHORT).show()
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllLives()
    }

    private fun openLink(link:String){
        val browserIntent = Intent(Intent.ACTION_VIEW,Uri.parse(link))
        startActivity(browserIntent)
    }

}