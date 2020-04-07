package android.hellomaul.simplecrud.inventaris

import android.content.Context
import android.content.Intent
import android.hellomaul.simplecrud.R
import android.hellomaul.simplecrud.inventaris.Manage_Inventaris
import android.hellomaul.simplecrud.inventaris.inventaris
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_inventaris_list.view.*

class RVAAdapterInventaris(private val context: Context, private val arrayList: ArrayList<inventaris>?):
    RecyclerView.Adapter<RVAAdapterInventaris.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    { return Holder(
        LayoutInflater.from(
            parent.context
        ).inflate(R.layout.activity_inventaris_list, parent, false)
    )
    }

    override fun getItemCount(): Int =
        arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int)
    {
        holder.view.IdList.text = arrayList?.get(position)?.Id.toString()
        holder.view.NameList.text = " Name : " + arrayList?.get(position)?.Name.toString()
        holder.view.CountList.text = "Count : " + arrayList?.get(position)?.Count.toString()
        holder.view.TypeList.text= "Type : " + arrayList?.get(position)?.Type.toString()
        holder.view.cvList.setOnClickListener {
            val i = Intent(context, Manage_Inventaris::class.java)
            i.putExtra("editmode" , "1")
            i.putExtra("Id" , arrayList?.get(position)?.Id)
            i.putExtra("Name" , arrayList?.get(position)?.Name)
            i.putExtra("Count" , arrayList?.get(position)?.Count)
            i.putExtra("Type" , arrayList?.get(position)?.Type)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)
}