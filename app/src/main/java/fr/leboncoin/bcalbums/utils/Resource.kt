package fr.leboncoin.bcalbums.utils

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

enum class Error {
    NETWORK,
    SYSTEM,
    DATA,
    NONE
}

class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val error: Error = Error.NONE
) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data)
        }

        fun <T> error(error: Error, data: T? = null): Resource<T> {
            return Resource(status = Status.ERROR, data = data, error = error)
        }

        fun <T> loading(): Resource<T> {
            return Resource(status = Status.LOADING)
        }
    }
}