package com.deyber.beersommelier.utils.resource

sealed class Resource<out T> {
    class Loading<out T>: Resource<T>()
    data class Success<out T>(val data:T) : Resource<T>()
    data class Failure<out T>(val message:String?, val throwable:Throwable?, val typeError:TYPEERROR): Resource<T>()
}

enum class TYPEERROR{
    NO_DATA,
    NO_NETWORK,
    NO_DATA_NETWORK,
    ERROR_REQUEST
}

inline fun <reified T> Resource<T>.doLoading(callback: () -> Unit){
    if(this is Resource.Loading<*>){
        callback()
    }
}

inline fun <reified T> Resource<T>.doSuccess(callback:(data:T)->Unit){
    if(this is Resource.Success){
        callback(data)
    }
}

inline fun <reified T> Resource<T>.doFailure(callback: (error:String?, throwable:Throwable?,typeError:TYPEERROR?) -> Unit){
    if(this is Resource.Failure){
        callback(message,throwable,typeError)
    }
}