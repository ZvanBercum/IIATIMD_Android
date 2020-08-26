package com.example.android.Resources


/**
 * @class Api
 * Class to make api calls to RESTFUL server
 */
class Api{


    private var queue: RequestQueue?
    private val base_url = "http://larsdeklijne.nl"
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
    private fun _getRequest(endpoint : String, callback: (String?) -> Unit){
        val url = "https://postman-echo.com/get?foo1=bar1&foo2=bar2"
        val getRequest =
            JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener<JSONObject?> { response -> Log.d("DEBUG", response.toString()) },
                Response.ErrorListener { error -> Log.d("DEBUG", error.toString()) }
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
    private fun _postRequest(formBody: RequestBody, endpoint: String, callback: (String?)-> Unit) {
        val url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    // response
                    Log.d("Response", response);
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    Log.d("Error.Response", response);
                }
            }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");

                return params;
            }
        };
        queue.add(postRequest);
    }


    /**
     * @func login
     * @var nummer String
     * @var password String
     * @var callback
}
