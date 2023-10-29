# MebiusRestful
Mebius的submodule--MebiusRestful

#### MebiusRestful网络库

##### 功能介绍
- 基于retrofit+rxjava封装的网络库，一般用来处理http的restful类型的接口请求
- 此基础库依赖于MebiusCommlib与MebiusLog
- 涵盖基础的接口请求，错误处理与回调
- 支持默认的缓存处理与本地数据mock（beta）

##### 使用方式
全局初始化时需要注册json解析器，以gson为例
```
    APPLICATION = application

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
```
进行必要的初始化
```
    RetrofitClient.init(baseUrlP = "https://www.wanandroid.com/")
```
此方法参数说明，参数皆为可选
- isMockerP ： 是否需要开启本地mock
- baseUrlP： baseUrl，大部分时候我们会使用一个拦截器去动态更改，所以这里更多时候也是占坑
- customConverterFactoryP：自定义ConverterFactory，如果不设置会有一个内置的，但是啥都没做
- interceptorBuilder： 自定义的interceptor从这里塞进去
- networkInterceptorBuilder： 自定义的networkInterceptor从这里塞进去
- applyOKHttpClientBuilder： 生成builder后如果有一些其他操作用这里

应用层按照retrofit的方式定义接口
```
interface ApiService {
    @GET("hotkey/json")
    fun requestHotKey(): Observable<CommonResponseInfo<List<HotKeyBean>>>
}

data class CommonResponseInfo<T>(
    val data: T
)

data class HotKeyBean(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)
```

使用如下进行使用，其中mBlock，onApiExceptionCall，logError根据实际情况配置，也可以不要

````
  RetrofitClient.getOrCreateService<ApiService>().requestHotKey().applySchedulers()
                .subscribe({
                    MLog.d("restful requestHotKey --> ${it.data}")
                    showToast("requestHotKey --> ${it.data}")
                }, object : BaseErrorConsumer(mBlock = { errCode, errMsg ->
                    MLog.d("restful mBlock errCode --> $errCode errMsg --> $errMsg")
                }) {
                    override fun onApiExceptionCall(apiException: ApiException) {
                        MLog.d("restful onApiExceptionCall errCode --> ${apiException.errorCode} errMsg --> ${apiException.errorMessage}")
                    }

                    override fun logError(e: Throwable) {
                        MLog.d("restful logError  --> ${e.message}")
                    }
                })
````