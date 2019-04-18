package com.dalancon.rc.bean

/**
 * Created by dalancon on 2019/4/15.
 */
data class Student(var name: String, var age: Int, var sex: Int, var group: String) : SectionBean(group)