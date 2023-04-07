package ua.honchar.test.core.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.observe(
    owner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (value: T) -> Unit
) {
    owner.lifecycleScope.launch {
        // Suspend the coroutine until the lifecycle is DESTROYED.
        // repeatOnLifecycle launches the block in a new coroutine every time the
        // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
        owner.repeatOnLifecycle(lifecycleState) {
            // Safely collect from locations when the lifecycle is STARTED
            // and stop collecting when the lifecycle is STOPPED
            collect { action.invoke(it) }
        }
    }
}