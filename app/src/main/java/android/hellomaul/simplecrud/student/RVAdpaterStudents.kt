package android.hellomaul.simplecrud.student

import android.content.Context
import android.content.Intent
import android.hellomaul.simplecrud.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_student_list.view.*

class RVAdpaterStudents(private val context: Context , private val arrayList: ArrayList<students>):
RecyclerView.Adapter<RVAdpaterStudents.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    { return Holder(
        LayoutInflater.from(
            parent.context
        ).inflate(R.layout.activity_student_list, parent, false)
    )
    }

    override fun getItemCount(): Int =
        arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int)
    { holder.view.NumberList.text =
            arrayList?.get(position)?.number
        holder.view.NameList.text = " Name : " + arrayList?.get(position).name
        holder.view.AddressList.text = "Address" + arrayList?.get(position).address
        holder.view.GenderList.text= "Gender" + arrayList?.get(position).gender
        holder.view.cvList.setOnClickListener {
            val i = Intent(context, ManageStudentActivity::class.java)
            i.putExtra("editmode" , "1")
            i.putExtra("number" , arrayList?.get(position)?.number)
            i.putExtra("name" , arrayList?.get(position)?.name)
            i.putExtra("address" , arrayList?.get(position)?.address)
            i.putExtra("gender" , arrayList?.get(position)?.gender)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)
}