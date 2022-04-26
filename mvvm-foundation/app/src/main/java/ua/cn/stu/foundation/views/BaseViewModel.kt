package ua.cn.stu.foundation.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.cn.stu.foundation.model.PendingResult
import ua.cn.stu.foundation.model.Result
import ua.cn.stu.foundation.model.tasks.Task
import ua.cn.stu.foundation.model.tasks.TaskListener
import ua.cn.stu.foundation.model.tasks.dispatchers.Dispatcher
import ua.cn.stu.foundation.utils.Event


typealias LiveEvent<T> = LiveData<Event<T>>
typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>

typealias LiveResult<T> = LiveData<Result<T>>
typealias MutableLiveResult<T> = MutableLiveData<Result<T>>
typealias MediatorLiveResult<T> = MediatorLiveData<Result<T>>

/**
 * Base class for all view-models.
 */
open class BaseViewModel(private val dispatcher: Dispatcher) : ViewModel() {

    private val tasks = mutableSetOf<Task<*>>()

    /**
     * Override this method in child classes if you want to listen for results
     * from other screens
     */
    open fun onResult(result: Any) {
    }

    fun onBackPressed() {
        clearTasks()

    }

    override fun onCleared() {
        super.onCleared()
        clearTasks()
    }

    fun <T> Task<T>.safeEnqueue(listener: TaskListener<T>? = null) {
        tasks.add(this)
        this.enqueue(dispatcher) {
            tasks.remove(this)
            listener?.invoke(it)
        }
    }

    fun <T> Task<T>.into(liveResult: MutableLiveResult<T>) {
        liveResult.value = PendingResult()
        this.safeEnqueue {
            liveResult.value = it
        }
    }

    private fun clearTasks() {
        tasks.forEach { it.cancel() }
        tasks.clear()

    }

}