package com.pokemon.mebius.restful.exception

/**
 * @Description:
 * @Author:         xwang
 * @CreateDate:     2020/8/31
 */

class ApiException(val errorCode: Int,val errorMessage: String = "") : RuntimeException(errorMessage)