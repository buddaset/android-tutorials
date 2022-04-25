package ua.cn.stu.simplemvvm.views

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.core.view.isVisible
import ua.cn.stu.foundation.model.Result
import ua.cn.stu.foundation.views.BaseFragment
import ua.cn.stu.simplemvvm.R
import ua.cn.stu.simplemvvm.databinding.PartResultBinding


fun <T> BaseFragment.renderSimpleResult(root: ViewGroup, result: Result<T>, onSuccess:(T) -> Unit) {
    val binding = PartResultBinding.bind(root)
    renderResult(
        root = root,
        result =result,
        onPending = {
            binding.progressBar.isVisible = true
        },
        onError = {
            binding.errorContainer.isVisible = true
        },
        onSuccess = {successData ->
            root.children
                .filter { it.id != R.id.progress_bar && it.id != R.id.errorContainer }
                .forEach {  it.isVisible = true }
            onSuccess(successData)
        }
    )
}

fun  BaseFragment.onTryAgain(root: View, onTryAgainPressed: ()-> Unit) {
    root.findViewById<Button>(R.id.try_again_button).setOnClickListener { onTryAgainPressed() }
}