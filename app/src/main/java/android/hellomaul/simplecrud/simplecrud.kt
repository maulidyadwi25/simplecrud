package android.hellomaul.simplecrud

import android.content.Intent
import android.hellomaul.simplecrud.inventaris.Inventaris_Activity
import android.hellomaul.simplecrud.inventaris.inventaris
import android.hellomaul.simplecrud.pegawai.pegawai
import android.hellomaul.simplecrud.pegawai.pegawai_activity
import android.hellomaul.simplecrud.student.MainActivity
import android.hellomaul.simplecrud.student.ManageStudentActivity
import android.hellomaul.simplecrud.student.students
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class simplecrud : AppCompatActivity() {
    private lateinit var btn_student: Button
    private lateinit var btn_pegawai: Button
    private lateinit var btn_inventaris: Button
    private lateinit var btn_guru: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simplecrud)
        btn_student = findViewById(R.id.btn_student)
        btn_student.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
        btn_pegawai = findViewById(R.id.btn_pegawai)
        btn_pegawai.setOnClickListener{
            startActivity(Intent(this, pegawai_activity::class.java))
        }
        btn_inventaris = findViewById(R.id.btn_inventaris)
        btn_inventaris.setOnClickListener{
            startActivity(Intent(this, Inventaris_Activity::class.java))
        }
    }
}
