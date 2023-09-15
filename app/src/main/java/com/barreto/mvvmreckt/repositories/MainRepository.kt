package com.barreto.mvvmreckt.repositories

import com.barreto.mvvmreckt.rest.RetrofitService

class MainRepository constructor(
    private val retrofitService: RetrofitService
) {

    fun getAllLives() = retrofitService.getAllLives()

}