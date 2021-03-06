package com.twt.service.home.common.schedule

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.twt.service.R
import com.twt.service.schedule2.view.schedule.ScheduleActivity

/**
 * Created by retrox on 23/10/2017.
 */

class ScheduleItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val board: LinearLayout = itemView.findViewById(R.id.ll_courses)
    val title: TextView = itemView.findViewById(R.id.tv_schedule_title)
    val inflater = LayoutInflater.from(itemView.context)

    fun bind(owner: LifecycleOwner, viewModel: ScheduleViewModel) {
        board.setOnClickListener {
            val intent = Intent(itemView.context, ScheduleActivity::class.java)
            itemView.context.startActivity(intent)
        }
        viewModel.title.observe(owner, Observer<String?> {
            title.text = it
        })
        viewModel.liveItems.observe(owner, Observer<List<ViewModel>> {
//            board.removeAllViews()
//            board.invalidate()
            it?.apply {
                board.removeAllViews()
                for (vm in it) {
                    val view = inflater.inflate(R.layout.item_common_course, board, false)
                    CourseItemHolder(view).apply {
                        bind(owner, vm)
                    }
                    board.addView(view)
                }
            }

        })
    }

    class CourseItemHolder(itemView: View) {
        val cardview: CardView = itemView.findViewById(R.id.card_home_item_course)
        val textview: TextView = itemView.findViewById(R.id.tv_home_item_course)
        fun bind(owner: LifecycleOwner, viewModel: ViewModel) {
            if (viewModel is CourseBriefViewModel) {
                viewModel.apply {
                    cardColor.observe(owner, Observer<Int> {
                        it?.let(cardview::setCardBackgroundColor)
                    })
                    courseName.observe(owner, Observer<String> {
                        it?.let { textview.text = it }
                    })
                    textColor.observe(owner, Observer<Int> {
                        it?.let(textview::setTextColor)
                    })
                }


            }
        }
    }
}
