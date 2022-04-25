package ua.cn.stu.foundation

import ua.cn.stu.foundation.model.Repository

interface BaseApplication {

    val repositories : List<Repository>
}