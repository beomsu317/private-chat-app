package com.beomsu317.privatechatapp.data.local.data_store

import android.content.Context
import android.util.Log
import androidx.datastore.dataStore
import com.beomsu317.privatechatapp.domain.model.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClientDataStore @Inject constructor(
    private val context: Context,
    private val client: Client
) {

    private val Context.clientDataStore by dataStore(
        fileName = "client.pb",
        serializer = ClientSerializer
    )

    val dataStoreFlow = context.clientDataStore.data

    init {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreFlow.collectLatest {
                Log.d("TAG", "${it}")
                client.token = it.token
                client.user = it.user.copy(
                    photoUrl = if (it.user.photoUrl.isEmpty()) {
                        "android.resource://com.beomsu317.privatechatapp/drawable/user_placeholder"
                    } else {
                        it.user.photoUrl
                    }
                )
            }
        }
    }

    suspend fun updateClient(client: Client) {
        context.clientDataStore.updateData {
            it.copy(token = client.token, user = client.user)
        }
    }
}