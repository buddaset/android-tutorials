package ua.cn.stu.foundation.model.tasks.factories

import android.os.Handler
import android.os.Looper
import ua.cn.stu.foundation.model.ErrorResult
import ua.cn.stu.foundation.model.FinalResult
import ua.cn.stu.foundation.model.SuccessResult
import ua.cn.stu.foundation.model.tasks.AbstractTask
import ua.cn.stu.foundation.model.tasks.SynchronizedTask
import ua.cn.stu.foundation.model.tasks.Task
import ua.cn.stu.foundation.model.tasks.TaskListener
import ua.cn.stu.foundation.model.tasks.dispatchers.Dispatcher
import java.lang.Exception


private val handler = Handler(Looper.getMainLooper())

class ThreadTaskFactory : TasksFactory {


    override fun <T> async(body: TaskBody<T>): Task<T> {
        return SynchronizedTask(ThreadTask(body))

    }



   private class ThreadTask<T>(private val body: TaskBody<T>) : AbstractTask<T>() {

        private var thread: Thread? = null


        override fun doEnqueue(listener: TaskListener<T>) {
            thread = Thread {
                executeBody(body, listener)
            }
            thread?.start()
        }

        override fun doCancel() {
            thread?.interrupt()
        }


    }

}