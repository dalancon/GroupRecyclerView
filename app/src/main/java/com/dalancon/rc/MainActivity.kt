package com.dalancon.rc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dalancon.rc.bean.Student
import com.dalancon.rc.widget.GroupItemDecoration
import com.dalancon.rc.widget.MyComparator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_layout.view.*

class MainActivity : AppCompatActivity() {

    var lists = ArrayList<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (index in 1..5) {
            var student = Student("name$index", index + 10, 0, "Group1")
            lists.add(student)
        }

        for (index in 1..3) {
            var student = Student("name$index", index + 10, 0, "组2")
            lists.add(student)
        }

        for (index in 1..20) {
            var student = Student("name$index", index + 10, 0, "Group3")
            lists.add(student)
        }

        var layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        mRecyclerView.layoutManager = layoutManager

        var groupItemDecoration = GroupItemDecoration.Builder(this, lists, MyComparator())
                .setDefaultGroupHeight(60)
                .setLeftMargin(40)
                .setRadius(15)
                .setTextSize(25)
                .builder()

        mRecyclerView.addItemDecoration(groupItemDecoration)

        mRecyclerView.adapter = MyAdapter()


    }


    inner class MyAdapter : RecyclerView.Adapter<MyAdapter.MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
        }

        override fun getItemCount(): Int {
            return lists.size
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            // 将组名设置为tag
            holder.itemView.tag = lists[position].groupName
            holder.itemView.tv_name.text = lists[position].name
        }


        inner class MyHolder(var view: View) : RecyclerView.ViewHolder(view)
    }
}
