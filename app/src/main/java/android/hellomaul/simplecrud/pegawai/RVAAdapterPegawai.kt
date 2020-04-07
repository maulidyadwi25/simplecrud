package android.hellomaul.simplecrud.pegawai

import android.hellomaul.simplecrud.student.ManageStudentActivity
import android.hellomaul.simplecrud.student.students
import android.content.Context
import android.content.Intent
import android.hellomaul.simplecrud.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_manage_pegawai.view.*
import kotlinx.android.synthetic.main.activity_pegawai_list.view.*



class RVAAdapterPegawai(private val context: Context , private val arrayList: ArrayList<pegawai>?):
    RecyclerView.Adapter<RVAAdapterPegawai.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    { return Holder(
        LayoutInflater.from(
            parent.context
        ).inflate(R.layout.activity_pegawai_list, parent, false)
    )
    }

    override fun getItemCount(): Int =
        arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int)
    {
        holder.view.NIPList.text = arrayList?.get(position)?.NIP.toString()
        holder.view.NameList.text = " Name : " + arrayList?.get(position)?.Name.toString()
        holder.view.AddressList.text = "Address : " + arrayList?.get(position)?.Address.toString()
        holder.view.PhoneList.text= "Phone : " + arrayList?.get(position)?.Phone.toString()
        holder.view.cvList.setOnClickListener {
            val i = Intent(context,manage_pegawai::class.java)
            i.putExtra("editmode" , "1")
            i.putExtra("NIP" , arrayList?.get(position)?.NIP)
            i.putExtra("Name" , arrayList?.get(position)?.Name)
            i.putExtra("Address" , arrayList?.get(position)?.Address)
            i.putExtra("Phone" , arrayList?.get(position)?.Phone)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)
}