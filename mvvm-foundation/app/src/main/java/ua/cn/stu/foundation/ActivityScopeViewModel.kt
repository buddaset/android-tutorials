package ua.cn.stu.foundation


import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import ua.cn.stu.foundation.navigator.IntermediateNavigator
import ua.cn.stu.foundation.navigator.Navigator
import ua.cn.stu.foundation.uiactions.UiActions


const val ARG_SCREEN = "ARG_SCREEN"

/**
 * Implementation of [Navigator] and [UiActions].
 * It is based on activity view-model because instances of [Navigator] and [UiActions]
 * should be available from fragments' view-models (usually they are passed to the view-model constructor).
 *
 * This view-model extends [AndroidViewModel] which means that it is not "usual" view-model and
 * it may contain android dependencies (context, bundles, etc.).
 */
class ActivityScopeViewModel(
    val uiActions: UiActions,
    val navigator: IntermediateNavigator
) : ViewModel() , Navigator by navigator, UiActions by uiActions{




    override fun onCleared() {
        super.onCleared()
        navigator.clear()
    }
}