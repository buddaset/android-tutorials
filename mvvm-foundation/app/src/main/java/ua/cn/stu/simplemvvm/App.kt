package ua.cn.stu.simplemvvm

import android.app.Application
import kotlinx.coroutines.Dispatchers
import ua.cn.stu.foundation.BaseApplication
import ua.cn.stu.foundation.model.coroutines.IoDispatcher
import ua.cn.stu.foundation.model.coroutines.WorkerDispatcher

import ua.cn.stu.simplemvvm.model.colors.InMemoryColorsRepository

/**
 * Here we store instances of model layer classes.
 */
class App : Application(), BaseApplication {

    private val ioDispatcher = IoDispatcher(Dispatchers.IO)
    private val workerDispatcher = WorkerDispatcher(Dispatchers.Default)


    /**
     * Place your singleton scope dependencies here
     */
    override val singletonScopeDependencies: List<Any> = listOf(
        InMemoryColorsRepository(ioDispatcher)
    )

}