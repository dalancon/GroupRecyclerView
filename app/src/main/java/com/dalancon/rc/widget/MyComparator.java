package com.dalancon.rc.widget;

import com.dalancon.rc.bean.Student;

import java.util.Comparator;

/**
 * Created by dalancon on 2019/4/15.
 */

public class MyComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        return o1.getGroup().compareTo(o2.getGroup());
    }
}