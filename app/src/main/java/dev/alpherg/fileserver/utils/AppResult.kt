package dev.alpherg.fileserver.utils

sealed class AppResult {
    data object Success : AppResult()
    data class Failure(val msg: String) : AppResult()
    data class Error(val msg: String) : AppResult()

    fun onSuccess(action: () -> Unit): AppResult {
        if (this is Success) action()
        return this
    }

    fun onFailure(action: (msg: String) -> Unit): AppResult {
        if (this is Failure) action(this.msg)
        return this
    }

    fun onError(action: (msg: String) -> Unit): AppResult {
        if (this is Error) action(this.msg)
        return this
    }

    fun finally(action: () -> Unit): AppResult {
        action()
        return this
    }
}