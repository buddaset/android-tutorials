package ua.cn.stu.foundation.model.tasks.dispatchers

interface Dispatcher {

    fun dispatch(block: () -> Unit)
}