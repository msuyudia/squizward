package com.suy.squizwardapp.utils

data class Resource<out T>(val status: Status, val data: T?, val message: Int?) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: Int): Resource<T> {
            return Resource(Status.ERROR, null, message)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}
