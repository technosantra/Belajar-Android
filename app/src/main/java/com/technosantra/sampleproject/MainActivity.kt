package com.technosantra.sampleproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GitHubService::class.java)
        val repos = service.profile("luffynas")
        repos.enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("onFailure", t.message)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()
                txtUsername.text = user?.name
                txtLocation.text = user?.location
                Glide.with(this@MainActivity).load("${user?.avatar_url}").into(imgAvatar)
            }

        })
    }
}

interface GitHubService {
    @GET("users/{user}")
    fun profile(@Path("user") user: String): Call<User>
}

class User  {
    val id: Int = 0
    val avatar_url: String = ""
    val name: String = ""
    val location: String = ""
}