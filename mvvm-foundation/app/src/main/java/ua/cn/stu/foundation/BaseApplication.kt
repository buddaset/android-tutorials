package ua.cn.stu.foundation

import ua.cn.stu.foundation.model.Repository
import ua.cn.stu.foundation.model.tasks.TasksFactory

interface BaseApplication {

    val repositories : List<Repository>
    val tasksFactory : TasksFactory
}