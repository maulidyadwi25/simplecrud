package android.hellomaul.simplecrud.student

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
import kotlinx.android.synthetic.main.activity_manage_student.*
import org.json.JSONObject

class ManageStudentActivity : AppCompatActivity() {
    lateinit var i: Intent
    private var gender = "Pria"
    private fun delete(){
        val loading = ProgressDialog(this)
        loading.setMessage("data deleted")
        loading.show()
      AndroidNetworking.get(ApiEndPoint.DELETE+"?number=" + textnumber.text.toString().toInt())
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(object : JSONObjectRequestListener {
                                override fun onResponse(response: JSONObject?) {
                                    loading.dismiss()
                                  Toast.makeText(applicationContext, response?.getString("massage"), Toast.LENGTH_SHORT).show()
                                    if (response?.getString("massage")?.contains("successfully")!!){
                                        this@ManageStudentActivity.finish()
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
      AndroidNetworking.post(ApiEndPoint.UPDATE)
          .addBodyParameter("number", textnumber.text.toString())
          .addBodyParameter("name", textname.text.toString())
          .addBodyParameter("address", textaddress.text.toString())
          .addBodyParameter("gender", gender)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(object : JSONObjectRequestListener{
                                override fun onResponse(response: JSONObject?) {
                                    loading.dismiss()
                                Toast.makeText(applicationContext, response?.getString("massage"),
                                 Toast.LENGTH_SHORT).show()
                                    if (response?.getString("massage")?.contains("successfully")!!){
                                        this@ManageStudentActivity.finish()
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
        setContentView(R.layout.activity_manage_student)
        i = intent
        if (i.hasExtra("editmode")) {
            if (i.getStringExtra("editmode").equals("1")) {
                onEditMode()
            }
        }
        rgGender.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radioBoy -> {
                    gender = "Male"
                }
                R.id.radioGirl -> {
                    gender = "Female"
                }
            }

        }
        btnCreate.setOnClickListener{
            create()
        }
        btnUpdate.setOnClickListener {update()  }
        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Data is Confirmation")
                .setMessage("Are you sure to delete this data ? ")
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
        textnumber.setText(i.getStringExtra("number"))
        textname.setText(i.getStringExtra("name"))
        textaddress.setText(i.getStringExtra("address"))
        textnumber.isEnabled = false

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE

        gender = i.getStringExtra("gender")
        if (gender.equals("Male")) {
            rgGender.check(R.id.radioBoy)
        } else {
            rgGender.check((R.id.radioGirl))
        }
    }

    private fun create(){
        val loading = ProgressDialog(this)
        loading.setMessage("menambahkan data ")
        loading.show()
        AndroidNetworking.post(ApiEndPoint.CREATE)
            .addBodyParameter("number", textnumber.text.toString())
            .addBodyParameter("name", textname.text.toString())
            .addBodyParameter("address", textname.text.toString())
            .addBodyParameter("address", textaddress.text.toString())
            .addBodyParameter("gender", gender)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("successfully")!!){
                        this@ManageStudentActivity.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }



}
