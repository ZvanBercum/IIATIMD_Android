package com.example.android.Resources

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException


/**
 * @class Api
 * Class to make api calls to RESTFUL server
 */
class Api{

    private var queue: RequestQueue?
    //BaseURL is localhost for testing purposes, using NGROK for tunneling.
    //BEWARE: NGROK URL CHANGES EVERYTIME YOU RESTART THE SERVICE
    private val base_url = "https://db29-2001-981-90d9-1-8158-1698-b5bd-edf2.ngrok.io"
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
    private fun _getRequest(endpoint : String, callback: (String?, VolleyError?) -> Unit){
        val url = this.base_url+endpoint
        val getRequest =
            StringRequest(
                Request.Method.GET, url,
                Response.Listener<String?> { response ->
                try {
                    callback(response, null)
                } catch (e: JSONException) {
                    e.printStackTrace()
                } },
                Response.ErrorListener { error -> callback(null, error) }
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
    private fun _postRequest(endpoint: String, params:MutableMap<String, String>,  callback: (String?, VolleyError?)-> Unit) {
        val url = this.base_url+endpoint
        val postRequest: StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                try {
                   callback(response, null)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> callback(null, error)
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
    fun login(email: String, password: String, callback: (String?, VolleyError?) -> Unit){
        val endpoint = "/api/authenticate"
        val params: MutableMap<String, String> = HashMap()
        params["email"] = email
        params["password"] = password
        this._postRequest(endpoint, params, callback)
    }

    fun getMedicines(token: String, callback:(String?, VolleyError?) -> Unit){
        val endpoint = "/medicine/index?token=$token"
        this._getRequest(endpoint, callback)
    }
}
