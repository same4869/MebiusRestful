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

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val response = value.string()
        MLog.d("restful response->$response")

        return CommJsonParser.getCommJsonParser().fromJson<T>(response, type)
    }
}
