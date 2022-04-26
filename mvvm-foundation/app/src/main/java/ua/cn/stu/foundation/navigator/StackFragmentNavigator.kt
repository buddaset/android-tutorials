package ua.cn.stu.foundation.navigator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ua.cn.stu.foundation.ARG_SCREEN
import ua.cn.stu.foundation.utils.Event
import ua.cn.stu.foundation.views.BaseFragment
import ua.cn.stu.foundation.views.BaseScreen
import ua.cn.stu.foundation.views.HasScreenTitle


class StackFragmentNavigator(
    private val activity: AppCompatActivity,
    private val containerId: Int,
    private val defaultTitle: String,
    private val animations: Animations,
    private val initialScreenCreator: () -> BaseScreen,

) : Navigator {

    private var result: Event<Any>? = null


    override fun launch(screen: BaseScreen) {
        launchFragment(screen)
    }

    override fun goBack(result: Any?)  {
        if (result != null) {
            this.result= Event(result)
        }
        activity.onBackPressed()
    }

    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            // define the initial screen that should be launched when app starts.
          launchFragment(
                screen = initialScreenCreator(),
                addToBackStack = false
            )
        }
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    fun onBackPressed() {
        val f = getCurrentFragment()
        if (f is BaseFragment)
            f.viewModel.onBackPressed()

    }

    private fun getCurrentFragment(): Fragment? =
     activity.supportFragmentManager.findFragmentById(containerId)


    fun onDestroy() {
        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)

    }

    private fun launchFragment(screen: BaseScreen, addToBackStack: Boolean = true) {
        // as screen classes are inside fragments -> we can create fragment directly from screen
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        // set screen object as fragment's argument
        fragment.arguments = bundleOf(ARG_SCREEN to screen)

        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            .setCustomAnimations(
                animations.enterAnim,
                animations.exitAnim,
                animations.popEnterAnim,
                animations.popExitAnim

            )
            .replace(containerId, fragment)
            .commit()
    }

    fun publishResults(fragment: Fragment) {
        val result = result?.getValue() ?: return
        if (fragment is BaseFragment) {
            // has result that can be delivered to the screen's view-model
            fragment.viewModel.onResult(result)
        }
    }


     private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View,
            savedInstanceState: Bundle?) {
            notifyScreenUpdates()
            publishResults(f)
            }
        }



    fun notifyScreenUpdates() {
        val f = getCurrentFragment()

        if (activity.supportFragmentManager.backStackEntryCount > 0) {
            // more than 1 screen -> show back button in the toolbar
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        if (f is HasScreenTitle && f.getScreenTitle() != null) {
            // fragment has custom screen title -> display it
            activity.supportActionBar?.title = f.getScreenTitle()
        } else {
            activity.supportActionBar?.title = defaultTitle
        }
    }

    class Animations(
        val enterAnim: Int,
        val exitAnim: Int,
        val popEnterAnim: Int,
        val popExitAnim: Int
    )
}

