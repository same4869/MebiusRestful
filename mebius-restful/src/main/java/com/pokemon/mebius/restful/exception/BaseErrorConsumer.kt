package com.pokemon.mebius.restful.exception

import com.pokemon.mebius.commlib.utils.APPLICATION
import com.pokemon.mebius.commlib.utils.getResString
import com.pokemon.mebius.commlib.utils.isNetworkAvailable
import com.pokemon.mebius.commlib.utils.showToast
import com.pokemon.mebius.log.MLog
import com.pokemon.mebius.restful.R
import io.reactivex.functions.Consumer
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * @Description:
 * @Author:         xwang
 * @CreateDate:     2021/1/13
 */

open class BaseErrorConsumer(private val mBlock: (errCode: Int, errMsg: String) -> Unit = { i: Int, s: String -> }) :
    Consumer<Throwable> {

    companion object {
        const val ERROR_NET = -999
    }

    open fun onApiExceptionCall(apiException: ApiException) {}

    open fun logError(e: Throwable) {}

    override fun accept(e: Throwable) {
        if (!isNetworkAvailable(APPLICATION)) {
            MLog.d("restful no net")
            mBlock(ERROR_NET, "no net")
//            showToast(getResString(R.string.net_error_message_toast))
            return
        }
        when (e) {
            is HttpException -> {// We had non-2XX http error
                MLog.d("restful HttpException")
//                showToast(getResString(R.string.net_error_message_toast))
                mBlock(ERROR_NET, "HttpException")
                logError(e)
            }

            is SocketTimeoutException -> {
                MLog.d("restful SocketTimeoutException")
                mBlock(ERROR_NET, "SocketTimeoutException")
                logError(e)
//                showToast(getResString(R.string.net_error_message_toast))
            }

            is IOException -> {// A network or conversion error happened
                MLog.d("restful IOException")
                mBlock(ERROR_NET, "IOException")
                logError(e)
//                showToast(getResString(R.string.net_error_message_toast))
            }

            is ApiException -> {
                MLog.d("restful ApiException : " + e.errorCode)
                mBlock(ERROR_NET, "ApiException")
                onApiExceptionCall(e)
            }

            else -> {
                logError(e)
                MLog.d(getResString(R.string.net_unknown_error_message_toast))
                mBlock(ERROR_NET, "unknown")
//                showToast(getResString(R.string.net_unknown_error_message_toast))
            }
        }
    }

}