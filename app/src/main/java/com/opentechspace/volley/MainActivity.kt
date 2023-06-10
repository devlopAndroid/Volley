package com.opentechspace.volley

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.opentechspace.volley.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var list: ArrayList<Model>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val queue = Volley.newRequestQueue(this)
        val url = "https://quotable.io/quotes"
        list = ArrayList<Model>()
    // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try{
                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONArray("results")
                    Log.e("length",jsonArray.length().toString())
                    for(i in 0 until (jsonArray.length()))
                  {
                        val jsonObject1 = jsonArray.getJSONObject(i)
                        val model = Model(jsonObject1.getString("author"),
                            jsonObject1.getString("content"))
                        list.add(model)
                    }
                    Log.e("response",list.toString())
                }catch (e : Exception)
                {
                    Log.e("failed",e.message.toString())
                }

            },
            {error ->
                Log.e("failed",error.message.toString())
                Toast.makeText(this, "Failed to get data", Toast.LENGTH_SHORT).show()
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}