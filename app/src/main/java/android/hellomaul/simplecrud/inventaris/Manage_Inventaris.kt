package android.hellomaul.simplecrud.inventaris

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
import kotlinx.android.synthetic.main.activity_manage_inventaris.*
import org.json.JSONObject

class Manage_Inventaris : AppCompatActivity() {
    lateinit var i: Intent
    private fun delete() {
        val loading = ProgressDialog(this)
        loading.setMessage("Data Deleted")
        loading.show()
        AndroidNetworking.get(ApiEndPointInventaris.DELETE + "?Id=" + textId.text.toString().toInt())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(
                        applicationContext,
                        response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@Manage_Inventaris.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun update() {
        val loading = ProgressDialog(this)
        loading.setMessage("Data Load ...")
        loading.show()
        AndroidNetworking.post(ApiEndPointInventaris.UPDATE)
            .addBodyParameter("Id", textId.text.toString())
            .addBodyParameter("Name", textName.text.toString())
            .addBodyParameter("Count", textCount.text.toString())
            .addBodyParameter("Type", textType.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(
                        applicationContext, response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@Manage_Inventaris.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_inventaris)
        i = intent
        if (i.hasExtra("editmode")) {
            if (i.getStringExtra("editmode").equals("1")) {
                onEditMode()
            }
        }
        btnCreate.setOnClickListener {
            create()
        }
        btnUpdate.setOnClickListener { update() }
        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Data is Confirmation")
                .setMessage("Are you sure to delete data ? ")
                .setPositiveButton("DELETE") { dialogInterface, i ->
                    dialogInterface.dismiss()
                    delete()
                }
                .setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .show()
        }
    }

    private fun onEditMode() {
        textId.setText(i.getStringExtra("Id"))
        textName.setText(i.getStringExtra("Name"))
        textCount.setText(i.getStringExtra("Count"))
        textType.setText(i.getStringExtra("Type"))
        textId.isEnabled = false

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE
    }

    private fun create() {
        val loading = ProgressDialog(this)
        loading.setMessage("Add Data ")
        loading.show()
        AndroidNetworking.post(ApiEndPointInventaris.CREATE)
            .addBodyParameter("Id", textId.text.toString())
            .addBodyParameter("Name", textName.text.toString())
            .addBodyParameter("Count", textCount.text.toString())
            .addBodyParameter("Type", textType.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(
                        applicationContext,
                        response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@Manage_Inventaris.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.message?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}
