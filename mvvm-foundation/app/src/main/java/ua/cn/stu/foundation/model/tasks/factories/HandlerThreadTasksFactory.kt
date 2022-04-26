package ua.cn.stu.foundation.model.tasks.factories

import android.os.Handler
import android.os.HandlerThread
import ua.cn.stu.foundation.model.tasks.AbstractTask
import ua.cn.stu.foundation.model.tasks.SynchronizedTask
import ua.cn.stu.foundation.model.tasks.Task
import ua.cn.stu.foundation.model.tasks.TaskListener

class HandlerThreadTasksFactory : TasksFactory {


    private val thread = HandlerThread(javaClass.simpleName)

    init {
        thread.start()
    }

    private val handler = Handler(thread.looper)


    override fun <T> async(body: TaskBody<T>): Task<T> {
        return  SynchronizedTask(HandlerThreadTask(body))
    }

    private inner class HandlerThreadTask<T>(private val body: TaskBody<T>) : AbstractTask<T>() {

        private var thread: Thread? = null

        override fun doEnqueue(listener: TaskListener<T>) {
            val runnable = Runnable {
                thread = Thread {
                    executeBody(body, listener)
                }
                thread?.start()
            }
        }

        override fun doCancel() {
            thread?.interrupt()
        }

    }
}