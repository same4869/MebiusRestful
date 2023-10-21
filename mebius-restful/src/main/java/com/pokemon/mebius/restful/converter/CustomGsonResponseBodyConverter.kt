package com.pokemon.mebius.restful.converter

import com.pokemon.mebius.commlib.utils.CommJsonParser
import com.pokemon.mebius.log.MLog
import com.pokemon.mebius.restful.bean.HttpStatus
import com.pokemon.mebius.restful.bean.PermitHttpStatus
import com.pokemon.mebius.restful.exception.ApiException
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Type

/**
 *  author: xun.wang on 2019/5/31
 **/
//todo 此处业务相关，需改
internal class CustomGsonResponseBodyConverter<T>(private val type: Type) :
    Converter<ResponseBody, T> {

    companion object {
        const val EXCEPTION_GSON_PARSE_ERROR = 10020030 //json序列化相关错误
        const val EXCEPTION_PASS_ERROR = 10020031 //通行证接口报错统一走这个错误码
    }

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val response = value.string()

        val httpStatus: HttpStatus

        try {
            MLog.d("httpResponse -->gson parse : $response")
            httpStatus =
                CommJsonParser.getCommJsonParser().fromJson(response, HttpStatus::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            MLog.d("gson parse error")
            value.close()
            throw ApiException(EXCEPTION_GSON_PARSE_ERROR, "gson parse error")
        }

        MLog.d(
            "httpStatus.code --> " + httpStatus.code + " httpStatus.retcode --> " + httpStatus.retcode
        )

        when {
            httpStatus.retcode == 0 && httpStatus.code == 0 -> {
                //社区接口正常
            }

            httpStatus.retcode == 0 && httpStatus.code == 200 -> {
                val permitHttpStatus: PermitHttpStatus =
                    CommJsonParser.getCommJsonParser()
                        .fromJson(response, PermitHttpStatus::class.java)
                if (permitHttpStatus.data.status == 1) {
                    //通行证接口正常
                } else {
                    //通行证接口异常
                    value.close()
                    val rtnCode = EXCEPTION_PASS_ERROR
                    val errMsg = permitHttpStatus.data.msg
                    throw ApiException(rtnCode, errMsg, permitHttpStatus.data.status)
                }
            }

            else -> {
                value.close()
                val rtnCode = if (httpStatus.retcode != 0) httpStatus.retcode else httpStatus.code
                val errMsg = if (httpStatus.retcode != 0) httpStatus.message else response
                throw ApiException(rtnCode, errMsg)
            }
        }
        return CommJsonParser.getCommJsonParser().fromJson<T>(response, type)
    }
}
