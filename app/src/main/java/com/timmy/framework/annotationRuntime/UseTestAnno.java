package com.timmy.framework.annotationRuntime;

import com.timmy.framework.annotationRuntime.annotations.TestAnno;

/**
 * Created by admin on 2017/3/30.
 */

public class UseTestAnno {
    @TestAnno(priority = TestAnno.Priority.MEDIUM,author = "timgo",status = TestAnno.Status.STARTED)
    public void useAnno(){

    }
}
