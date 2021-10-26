package com.coldkitchen.severalminuteworkout

//Firstly, we create ViewHolder, which stores our items that we want to operate with.
//Then, we need to override onCreateViewHolder, to inflate (set) our single item to show.
//Then, we need to override onBindViewHolder, where we actually do the operations with
/// items from ViewHolder, within given current position (firstly getting position from items list that in class
/// arguments)
//Then, we need to override getItemCount, that only returns .size of list of items, that we want to
/// work with.

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ViewUtils.dpToPx
import kotlinx.android.synthetic.main.item_exercise_status.view.*

// Extending Rec.View.Adapter, that some sort of a list of items that we want to display:
class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>, val context: Context) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    //Gave it a view holder, which inherits from Rec.View.ViewHolder, but inflated with my tv:
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        //Status circles
        val tvItem = view.tvItem
    }

    //With the next overridden functions:

    //Displaying it on the screen
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_exercise_status,
            parent,
            false))
    }

    // Bind my data (that is in the ViewHolder) to the RecyclerView with my corrections.
    // It taking position from attached to the class arrayList
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val factor: Float = holder.itemView.getContext()
            .getResources().getDisplayMetrics().density

        //which item is it now (passed position)
        val model: ExerciseModel = items[position]
        //setting the Id as a text to a circle (tvItem from ViewHolder)
        ////holder.tvItem.text = model.getId().toString()

        //Changing color and size if this item is completed or selected:
        if(model.getIsSelected()){
            holder.tvItem.background = ContextCompat.getDrawable(context,
                R.drawable.circular_item_for_countdown)
            holder.tvItem.layoutParams.width = (17 * factor).toInt()
            holder.tvItem.layoutParams.height = (17 * factor).toInt()
            ////holder.tvItem.setTextColor(Color.parseColor("#212121"))
        } else
            if(model.getIsComplited()){
                holder.tvItem.background = ContextCompat.getDrawable(context,
                    R.drawable.item_comlited)
                    //holder.tvItem.text = "X"
                    holder.tvItem.layoutParams.width = (17 * factor).toInt()
                    holder.tvItem.layoutParams.height = (17 * factor).toInt()

        } else{
                holder.tvItem.background = ContextCompat.getDrawable(context,
                    R.drawable.item_not_activated)

                holder.tvItem.layoutParams.width = (17 * factor).toInt()
                holder.tvItem.layoutParams.height = (17 * factor).toInt()
            }
    }

    //Gets number of items
    override fun getItemCount(): Int {
        return items.size
    }
}