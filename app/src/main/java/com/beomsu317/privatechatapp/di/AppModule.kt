package com.beomsu317.privatechatapp.di

import android.content.Context
import androidx.room.Room
import com.beomsu317.privatechatapp.data.local.data_store.ClientDataStore
import com.beomsu317.privatechatapp.data.local.room.PrivateChatDatabase
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
                Json.asConverterFactory("application/json".toMediaType())
            )
            .client(client)
            .build()
            .create(PrivateChatApi::class.java)
    }

    @Singleton
    @Provides
    fun providePrivateChatDatabase(@ApplicationContext context: Context): PrivateChatDatabase {
        return Room.databaseBuilder(
            context,
            PrivateChatDatabase::class.java,
            "private_chat_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePrivateChatRepository(
        api: PrivateChatApi,
        database: PrivateChatDatabase,
        clientDataStore: ClientDataStore,
        @ApplicationContext context: Context,
        client: Client
    ): PrivateChatRepository =
        PrivateChatRepositoryImpl(api, database, clientDataStore, context, client)

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
            signOutUseCase = SignOutUseCase(repository),
            uploadProfileImageUseCase = UploadProfileImageUseCase(repository),
            getFriendsUseCase = GetFriendsUseCase(repository),
            getAllFriendsUseCase = GetAllFriendsUseCase(repository),
            addFriendUseCase = AddFriendUseCase(repository),
            deleteFriendUseCase = DeleteFriendUseCase(repository),
            searchFriends = SearchFriendsUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideClient(): Client {
        return Client()
    }

    @Provides
    @Singleton
    fun provideClientDataStore(@ApplicationContext context: Context, client: Client): ClientDataStore {
        return ClientDataStore(context, client)
    }
}