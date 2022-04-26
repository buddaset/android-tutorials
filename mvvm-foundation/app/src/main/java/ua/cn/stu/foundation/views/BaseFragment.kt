package ua.cn.stu.foundation.views

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import ua.cn.stu.foundation.model.ErrorResult
import ua.cn.stu.foundation.model.PendingResult
import ua.cn.stu.foundation.model.Result
import ua.cn.stu.foundation.model.SuccessResult
import java.lang.Exception


/**
 * Base class for all fragments
 */
abstract class BaseFragment : Fragment() {

    /**
     * View-model that manages this fragment
     */
    abstract val viewModel: BaseViewModel

    /**
     * Call this method when activity controls (e.g. toolbar) should be re-rendered
     */
    fun notifyScreenUpdates() {
        // if you have more than 1 activity -> you should use a separate interface instead of direct
        // cast to MainActivity
        (requireActivity() as FragmentHolder).notifyScreenUpdates()
    }



    fun <T> renderResult(
        root: View, result: Result<T>,
        onPending: () -> Unit,
        onError: (Exception) -> Unit,
        onSuccess: (T) -> Unit
    ) {
        (root as ViewGroup).children.forEach { it.visibility = View.GONE }
        when (result) {
            is SuccessResult -> onSuccess(result.data)
            is ErrorResult -> onError(result.exception)
            is PendingResult -> onPending()
        }
    }


}