package com.bhavneshsharma.moviedetailstmdbcompose.network.service

import com.bhavneshsharma.moviedetailstmdbcompose.BuildConfig
import com.bhavneshsharma.moviedetailstmdbcompose.network.interceptor.AuthInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

interface BaseService {
    companion object {
        fun <T : BaseService> createService(
            clazz: Class<T>,
            readTimeout: Long? = null,
            authInterceptor: AuthInterceptor?,
            baseUrl: String = BuildConfig.BASE_URL,
        ): T {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .let { if (authInterceptor == null) it else it.addInterceptor(authInterceptor) }
                .let {
                    if (readTimeout != null) it.readTimeout(readTimeout, TimeUnit.SECONDS)
                    else it
                }.build()

            val gson: Gson = GsonBuilder()
                .registerTypeAdapter(
                    Date::class.java,
                    object : TypeAdapter<Date>() {
                        override fun write(writer: JsonWriter?, value: Date?) {
                            when (value) {
                                null -> writer?.nullValue()
                                else -> writer?.value(value.time)
                            }
                        }

                        override fun read(reader: JsonReader?): Date {
                            return when (reader) {
                                null -> Date()
                                else -> Date(reader.nextLong())
                            }
                        }
                    }
                )
                .create()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(clazz)
        }
    }
}