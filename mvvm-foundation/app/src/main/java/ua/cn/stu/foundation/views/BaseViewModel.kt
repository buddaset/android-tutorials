package ua.cn.stu.foundation.views

import androidx.lifecycle.*
import kotlinx.coroutines.*
import ua.cn.stu.foundation.model.ErrorResult
import ua.cn.stu.foundation.model.Result
import ua.cn.stu.foundation.model.SuccessResult
import java.lang.Exception


typealias LiveResult<T> = LiveData<Result<T>>
typealias MutableLiveResult<T> = MutableLiveData<Result<T>>
typealias MediatorLiveResult<T> = MediatorLiveData<Result<T>>

/**
 * Base class for all view-models.
 */
open class BaseViewModel() : ViewModel() {

    private  val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate
    protected val viewModelScope = CoroutineScope(coroutineContext)
    override fun onCleared() {
        super.onCleared()
        clearViewModelScope()
    }

    /**
     * Override this method in child classes if you want to listen for results
     * from other screens
     */
    open fun onResult(result: Any) {

    }

    /**
     * Override this method in child classes if you want to control go-back behaviour.
     * Return `true` if you want to abort closing this screen
     */
    open fun onBackPressed(): Boolean {
        clearViewModelScope()
        return false
    }


    /**
     * Launch task asynchronously and map its result to the specified
     * [liveResult].
     * Task is cancelled automatically if view-model is going to be destroyed.
     */
    fun <T> into(liveResult: MutableLiveResult<T>, block: suspend () -> T) {
        viewModelScope.launch {
            try {
                liveResult.postValue(SuccessResult(block()))

            } catch (e: Exception) {
                liveResult.postValue(ErrorResult(e))
            }
        }
    }

    private fun clearViewModelScope() {
        viewModelScope.cancel()


    }

}