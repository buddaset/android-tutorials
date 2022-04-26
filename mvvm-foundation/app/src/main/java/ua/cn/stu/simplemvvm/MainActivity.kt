package ua.cn.stu.simplemvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import ua.cn.stu.foundation.ActivityScopeViewModel
import ua.cn.stu.foundation.navigator.IntermediateNavigator
import ua.cn.stu.foundation.navigator.StackFragmentNavigator
import ua.cn.stu.foundation.uiactions.AndroidUiActions
import ua.cn.stu.foundation.utils.viewModelCreator
import ua.cn.stu.foundation.views.FragmentHolder

import ua.cn.stu.simplemvvm.views.currentcolor.CurrentColorFragment

/**
 * This application is a single-activity app. MainActivity is a container
 * for all screens.
 */
class MainActivity : AppCompatActivity(), FragmentHolder {

    private lateinit var navigator: StackFragmentNavigator

    private val activityViewModel by viewModelCreator<ActivityScopeViewModel> {
        ActivityScopeViewModel(
            uiActions = AndroidUiActions(applicationContext),
            navigator = IntermediateNavigator()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator = StackFragmentNavigator(
            activity = this,
            containerId = R.id.fragmentContainer,
            defaultTitle = getString(R.string.app_name),
            animations = StackFragmentNavigator.Animations(
                R.anim.enter,
                R.anim.exit,
                R.anim.pop_enter,
                R.anim.pop_exit),
            initialScreenCreator = { CurrentColorFragment.Screen() }
        )
        navigator.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        navigator.onDestroy()
        super.onDestroy()

    }

    override fun onBackPressed() {
        navigator.onBackPressed()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        // execute navigation actions only when activity is active
        activityViewModel.navigator.setTarget(navigator)
    }

    override fun onPause() {
        super.onPause()
        // postpone navigation actions if activity is not active
        activityViewModel.navigator.setTarget(null)
    }

    override fun notifyScreenUpdates() {
        navigator.notifyScreenUpdates()
    }

    override fun getActivityScopeViewModel(): ActivityScopeViewModel {
        return activityViewModel
    }


}