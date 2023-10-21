package com.pokemon.mebius.restful

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.gson.Gson
import com.pokemon.mebius.commlib.utils.APPLICATION
import com.pokemon.mebius.commlib.utils.CommJsonParser
import com.pokemon.mebius.commlib.utils.JsonParser
import com.pokemon.mebius.commlib.utils.onClick
import com.pokemon.mebius.commlib.utils.showToast
import com.pokemon.mebius.log.MLog
import com.pokemon.mebius.restful.exception.ApiException
import com.pokemon.mebius.restful.exception.BaseErrorConsumer
import com.pokemon.mebius.restful.utils.applySchedulers
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        APPLICATION = application
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        RetrofitClient.init(
//            isMockerP = true,
//            interceptorBuilder = {
//                it.addInterceptor(LoginStatusInterceptor())
//            }
//        )
        initJsonParser()
        RetrofitClient.init(baseUrlP = "https://www.wanandroid.com/")

        findViewById<Button>(R.id.test1).onClick {
            RetrofitClient.getOrCreateService<ApiService>().requestHotKey().applySchedulers().subscribe ({
                MLog.d("requestHotKey --> ${it.data}")
                showToast("requestHotKey --> ${it.data}")
            },object:BaseErrorConsumer(){
                override fun onApiExceptionCall(apiException: ApiException) {
                }

                override fun logError(e: Throwable) {
                }
            })
        }

    }

    private fun initJsonParser() {
        CommJsonParser.initJsonParser(jsonParserP = object : JsonParser {
            override fun toJson(src: Any): String {
                return Gson().toJson(src)
            }

            override fun <T> fromJson(json: String, clazz: Class<T>): T {
                return Gson().fromJson(json, clazz)
            }

            override fun <T> fromJson(json: String, typeOfT: Type): T {
                return Gson().fromJson(json, typeOfT)
            }

        })
    }
}