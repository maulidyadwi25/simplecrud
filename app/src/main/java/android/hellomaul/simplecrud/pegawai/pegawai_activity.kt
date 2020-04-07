package android.hellomaul.simplecrud.pegawai

import android.app.ProgressDialog
import android.content.Intent
import android.hellomaul.simplecrud.R
import android.hellomaul.simplecrud.pegawai.RVAAdapterPegawai
import android.hellomaul.simplecrud.pegawai.pegawai
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_pegawai_activity.*
import org.json.JSONObject
import java.lang.Exception


class pegawai_activity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = " Data Pegawai "
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mFloatingActionButton.setOnClickListener{
            startActivity(Intent(this,
                manage_pegawai::class.java))
        }}

    var arrayList = ArrayList<pegawai>()

    override fun onResume() {
        super.onResume()
        loadAllStudents()
    }

    private fun loadAllStudents(){
        val loading = ProgressDialog(this)
        loading.setMessage("Data Load ...")
        loading.show()
        AndroidNetworking.get(ApiEndPointPegawai.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{


                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()
                    val jsonArray = response?.optJSONArray("result")
                    Log.e("RESPONSE", jsonArray.toString())
//                    Log.e("Main",response.toString())
                    try {
                        if (jsonArray?.length() == 0) {
                            loading.dismiss()
                            Toast.makeText(
                                applicationContext,
                                "Pegawai data is empty, Add the data first",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        for (i in 0 until jsonArray?.length()!!) {
                            val jsonObject = jsonArray?.optJSONObject(i)
                            if (!jsonObject.isNull("NIP")) {
                                arrayList.add(
                                    pegawai(
                                        jsonObject.getString("NIP"),
                                        jsonObject.getString("Name"),
                                        jsonObject.getString("Address"),
                                        jsonObject.getString("Phone")
                                    )
                                )
                            }
                            if (jsonArray?.length() - 1 == i) {
                                loading.dismiss()
                                val adapter = RVAAdapterPegawai(
                                    applicationContext,
                                    arrayList
                                )
                                adapter.notifyDataSetChanged()
                                mRecyclerView.adapter = adapter
                            }
                        }
                    }catch (e:Exception){

                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure",Toast.LENGTH_SHORT).show()
                }
            })


    }

}

