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

abstract class BaseErrorConsumer(private val mBlock: (errCode: Int, errMsg: String) -> Unit = { i: Int, s: String -> }) :
    Consumer<Throwable> {

    companion object {
        const val ERROR_NET = -999
    }

    abstract fun onApiExceptionCall(apiException: ApiException)

    abstract fun logError(e: Throwable)

    override fun accept(e: Throwable) {
        if (!isNetworkAvailable(APPLICATION)) {
            MLog.d("no net")
            mBlock(ERROR_NET, "")
            showToast(getResString(R.string.net_error_message_toast))
            return
        }
        when (e) {
            is HttpException -> {// We had non-2XX http error
                MLog.d("HttpException")
                showToast(getResString(R.string.net_error_message_toast))
                mBlock(ERROR_NET, "")
                logError(e)
            }

            is SocketTimeoutException -> {
                MLog.d("SocketTimeoutException")
                mBlock(ERROR_NET, "")
                logError(e)
                showToast(getResString(R.string.net_error_message_toast))
            }

            is IOException -> {// A network or conversion error happened
                MLog.d("IOException")
                mBlock(ERROR_NET, "")
                logError(e)
                showToast(getResString(R.string.net_error_message_toast))
            }

            is ApiException -> {
                MLog.d("ApiException : " + e.errorCode)
                onApiExceptionCall(e)
            }

            else -> {
                logError(e)
                MLog.d(getResString(R.string.net_unknown_error_message_toast))
                showToast(getResString(R.string.net_unknown_error_message_toast))
            }
        }
    }

}