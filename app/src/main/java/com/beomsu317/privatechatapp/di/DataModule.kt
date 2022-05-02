package com.beomsu317.privatechatapp.di

import android.content.Context
import com.beomsu317.privatechatapp.data.local.data_store.ClientDataStore
import com.beomsu317.privatechatapp.data.remote.PrivateChatApi
import com.beomsu317.privatechatapp.data.repository.PrivateChatRepositoryImpl
import com.beomsu317.privatechatapp.domain.model.Client
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import com.beomsu317.privatechatapp.domain.use_case.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

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
                Json.asConverterFactory("application/json".toMediaType())
            )
            .client(client)
            .build()
            .create(PrivateChatApi::class.java)
    }

    @Provides
    @Singleton
    fun providePrivateChatRepository(
        api: PrivateChatApi,
        clientDataStore: ClientDataStore
    ): PrivateChatRepository =
        PrivateChatRepositoryImpl(api, clientDataStore)

    @Provides
    @Singleton
    fun providePrivateChatUseCase(
        repository: PrivateChatRepository
    ): PrivateChatUseCases {
        return PrivateChatUseCases(
            signUpUseCase = SignUpUseCase(repository),
            signInUseCase = SignInUseCase(repository),
            getProfileUseCase = GetProfileUseCase(repository),
            isSignedInUseCase = IsSignedInUseCase(repository),
            signOutUseCase = SignOutUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideClientDataStore(@ApplicationContext context: Context, client: Client): ClientDataStore {
        return ClientDataStore(context, client)
    }
}