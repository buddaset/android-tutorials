package ua.cn.stu.foundation.model

sealed class Progress

object  EmptyProgress: Progress()

data class PercentageProgress(val percentage: Int) : Progress() {

    companion object {
        val START = PercentageProgress(percentage = 0)
    }
}

// ---extension methods

fun Progress.isInProgress(): Boolean = this !is EmptyProgress

fun Progress.getPercentage(): Int = (this as? PercentageProgress)?.percentage ?: PercentageProgress.START.percentage