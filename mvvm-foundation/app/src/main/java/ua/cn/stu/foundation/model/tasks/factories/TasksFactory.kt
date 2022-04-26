package ua.cn.stu.foundation.model.tasks.factories


import ua.cn.stu.foundation.model.tasks.Task

typealias TaskBody<T> = () -> T

interface TasksFactory  {

    fun <T> async(body: TaskBody<T>): Task<T>
}