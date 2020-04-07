package android.hellomaul.simplecrud.pegawai

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.hellomaul.simplecrud.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_manage_pegawai.*
import kotlinx.android.synthetic.main.activity_manage_pegawai.btnCreate
import kotlinx.android.synthetic.main.activity_manage_pegawai.btnDelete
import kotlinx.android.synthetic.main.activity_manage_pegawai.btnUpdate
import org.json.JSONObject

class manage_pegawai : AppCompatActivity() {
    lateinit var i: Intent
    private fun delete(){
        val loading = ProgressDialog(this)
        loading.setMessage("data deleted")
        loading.show()
        AndroidNetworking.get(ApiEndPointPegawai.DELETE+"?NIP=" + textNIP.text.toString().toInt())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_SHORT).show()
                    if (response?.getString("message")?.contains("successfully")!!){
                        this@manage_pegawai.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun update(){
        val loading = ProgressDialog(this)
        loading.setMessage("data load")
        loading.show()
        AndroidNetworking.post(ApiEndPointPegawai.UPDATE)
            .addBodyParameter("NIP", textNIP.text.toString())
            .addBodyParameter("Name", textName.text.toString())
            .addBodyParameter("Address", textAddress.text.toString())
            .addBodyParameter("Phone", textPhone.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, response?.getString("message"),
                        Toast.LENGTH_SHORT).show()
                    if (response?.getString("message")?.contains("successfully")!!){
                        this@manage_pegawai.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_pegawai)
        i = intent
        if (i.hasExtra("editmode")) {
            if (i.getStringExtra("editmode").equals("1")) {
                onEditMode()
            }
        }
        btnCreate.setOnClickListener{
            create()
        }
        btnUpdate.setOnClickListener {update()  }
        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Data is Confirmation")
                .setMessage("Are you sure to delete data ? ")
                .setPositiveButton("DELETE") { dialogInterface, i -> dialogInterface.dismiss()
                    delete()
                }
                .setNegativeButton("CANCEL", DialogInterface.OnClickListener {
                        dialogInterface, i -> dialogInterface.dismiss()
                })
                .show()
        }
    }

    private fun onEditMode() {
        textNIP.setText(i.getStringExtra("NIP"))
        textName.setText(i.getStringExtra("Name"))
        textAddress.setText(i.getStringExtra("Address"))
        textPhone.setText(i.getStringExtra("Phone"))
        textNIP.isEnabled = false

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE
    }

    private fun create(){
        val loading = ProgressDialog(this)
        loading.setMessage("Add Data ")
        loading.show()
        AndroidNetworking.post(ApiEndPointPegawai.CREATE)
            .addBodyParameter("NIP", textNIP.text.toString())
            .addBodyParameter("Name", textName.text.toString())
            .addBodyParameter("Address", textAddress.text.toString())
            .addBodyParameter("Phone", textPhone.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("successfully")!!){
                        this@manage_pegawai.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.message?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }



}
