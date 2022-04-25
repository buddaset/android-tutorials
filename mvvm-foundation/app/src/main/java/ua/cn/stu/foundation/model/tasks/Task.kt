package ua.cn.stu.foundation.model.tasks

import ua.cn.stu.foundation.model.FinalResult
import ua.cn.stu.foundation.model.Result

typealias TaskListener<T> = (FinalResult<T>) -> Unit

interface Task<T> {

    fun await() : T

    /**
     *  listener are callen in main thread
     */
    fun enqueue(listener: TaskListener<T>)

    fun cancel()


}