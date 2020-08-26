package com.example.android.Resources

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


/**
 * @class Api
 * Class to make api calls to RESTFUL server
 */
class Api{


    private var queue: RequestQueue?
    //BaseURL is localhost for testing purposes, using NGROK for tunneling.
    //BEWARE: NGROK URL CHANGES EVERYTIME YOU RESTART THE SERVICE
    private val base_url = "https://c1018c9953c1.ngrok.io"
    //Init vars

    constructor(context: Context) {
        this.queue = Volley.newRequestQueue(context)
    }

    /**
     * @func _getRequest
     * @var endpoint String
     * @var callback
     * This function performs a get request to the basurl+endpoint
     * passes result to the callback
     */
    private fun _getRequest(endpoint : String, callback: (JSONObject?) -> Unit){
        val url = this.base_url+endpoint
        val getRequest =
            JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener<JSONObject?> { response ->
                try {
                    callback(response)
                } catch (e: JSONException) {
                    e.printStackTrace()
                } },
                //TODO: ERROR NEEDS TO BE HANDLED CORRECTLY
                Response.ErrorListener { error -> Log.d("ERROR", error.toString()) }
            )
        this.queue!!.add(getRequest)
    }


    /**
     * @func _postRequest
     * @var formBody RequestBody
     * @var endpoint String
     * @var callback
     * This function performs a post request to the basurl+endpoint
     * data is passed in the formbody
     * passes result to the callback
     */
    private fun _postRequest(endpoint: String, params:MutableMap<String, String>,  callback: (String?)-> Unit) {
        val url = this.base_url+endpoint
        val postRequest: StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                try {
                   callback(response)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Log.d("ERROR", error.toString())
            }) {
            override fun getParams(): Map<String, String> {
                return params
            }
        }
        this.queue!!.add(postRequest)

    }


    /**
     * @func login
     * @var nummer String
     * @var password String
     * @var callback
     * This function sends the postrequest of the login to the server.
     * Passes the results in the callback.
     */
    fun login(email: String, password: String, callback: (String?) -> Unit){
        val endpoint = "/api/authenticate"
        val params: MutableMap<String, String> = HashMap()
        params["email"] = email
        params["password"] = password
        this._postRequest(endpoint, params, callback)
    }
}
