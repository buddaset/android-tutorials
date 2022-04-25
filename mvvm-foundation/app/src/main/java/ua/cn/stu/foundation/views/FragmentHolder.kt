package ua.cn.stu.foundation.views

import ua.cn.stu.foundation.ActivityScopeViewModel

interface FragmentHolder {

    fun notifyScreenUpdates()

    fun getActivityScopeViewModel(): ActivityScopeViewModel
}