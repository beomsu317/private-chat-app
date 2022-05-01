package com.beomsu317.privatechatapp.di

import com.beomsu317.privatechatapp.data.remote.PrivateChatApi
import com.beomsu317.privatechatapp.data.repository.PrivateChatRepositoryImpl
import com.beomsu317.privatechatapp.domain.model.Client
import com.beomsu317.privatechatapp.domain.model.User
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import com.beomsu317.privatechatapp.domain.use_case.GetProfileUseCase
import com.beomsu317.privatechatapp.domain.use_case.PrivateChatUseCases
import com.beomsu317.privatechatapp.domain.use_case.SignInUseCase
import com.beomsu317.privatechatapp.domain.use_case.SignUpUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkhttpClient(client: Client): OkHttpClient {
        val interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().newBuilder()
                client.token?.let { token ->
                    request.addHeader("Authorization", "Bearer ${token}")
                }
                return chain.proceed(request.build())
            }
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providePrivateChatApi(client: OkHttpClient): PrivateChatApi {
        return Retrofit.Builder()
            .baseUrl(PrivateChatApi.BASE_URL)
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }
                    .asConverterFactory(
                        MediaType.get(
                            "application/json"
                        )
                    )
            )
            .client(client)
            .build()
            .create(PrivateChatApi::class.java)
    }

    @Provides
    @Singleton
    fun providePrivateChatRepository(api: PrivateChatApi): PrivateChatRepository =
        PrivateChatRepositoryImpl(api)

    @Provides
    @Singleton
    fun providePrivateChatUseCase(
        repository: PrivateChatRepository,
        client: Client
    ): PrivateChatUseCases {
        return PrivateChatUseCases(
            signUpUseCase = SignUpUseCase(repository),
            signInUseCase = SignInUseCase(repository, client),
            getProfileUseCase = GetProfileUseCase(repository)
        )
    }
}