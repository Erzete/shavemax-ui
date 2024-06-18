package com.dicoding.hairstyler.di

import android.content.Context
import android.util.Log
import com.dicoding.hairstyler.data.local.preference.SessionPreference
import com.dicoding.hairstyler.data.local.preference.dataStore
import com.dicoding.hairstyler.data.remote.retrofit.ApiConfig
import com.dicoding.hairstyler.data.repository.HairRepository
import com.dicoding.hairstyler.data.repository.HairRepositoryImpl
import com.dicoding.hairstyler.data.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context) : UserRepositoryImpl{
        val sessionPreference = SessionPreference.getPreferenceInstance(context.dataStore)
        val user = runBlocking { sessionPreference.getToken().first() }
        val apiServiceOne = ApiConfig.getApiServiceOne(user.token)
        val apiServiceTwo = ApiConfig.getApiServiceTwo(user.token)
        return UserRepositoryImpl.getRepositoryInstance(sessionPreference, apiServiceOne, apiServiceTwo)
    }

    fun provideHairRepository(context: Context) : HairRepositoryImpl{
        val sessionPreference = SessionPreference.getPreferenceInstance(context.dataStore)
        val user = runBlocking { sessionPreference.getToken().first() }
        Log.d("HairReposInject", user.token)
        val apiServiceOne = ApiConfig.getApiServiceOne(user.token)
        val apiServiceTwo = ApiConfig.getApiServiceTwo(user.token)
        return HairRepositoryImpl.getRepositoryInstance(apiServiceOne, apiServiceTwo)
    }
}