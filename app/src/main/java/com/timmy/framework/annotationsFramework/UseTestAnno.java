package com.timmy.framework.annotationsFramework;

import com.timmy.framework.annotationsFramework.annotations.TestAnno;

/**
 * Created by admin on 2017/3/30.
 */

public class UseTestAnno {
    @TestAnno(priority = TestAnno.Priority.MEDIUM,author = "timgo",status = TestAnno.Status.STARTED)
    public void useAnno(){

    }
}
