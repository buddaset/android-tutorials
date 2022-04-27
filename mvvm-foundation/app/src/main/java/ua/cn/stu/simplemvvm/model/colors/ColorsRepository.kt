package ua.cn.stu.simplemvvm.model.colors

import kotlinx.coroutines.flow.Flow
import ua.cn.stu.foundation.model.Repository


typealias ColorListener = (NamedColor) -> Unit

/**
 * Repository interface example.
 *
 * Provides access to the available colors and current selected color.
 */
interface ColorsRepository : Repository {

    /**
     * Get the list of all available colors that may be chosen by the user.
     */
    suspend fun getAvailableColors(): List<NamedColor>

    /**
     * Get the color content by its ID
     */
    suspend fun getById(id: Long): NamedColor

    /**
     * Get the current selected color.
     */
    suspend fun getCurrentColor(): NamedColor

    /**
     * Set the specified color as current.
     */
    fun  setCurrentColor(color: NamedColor) : Flow<Int>

   fun listenCurrentColor(): Flow<NamedColor>

}