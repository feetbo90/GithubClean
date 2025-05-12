package com.example.module.core.data.source.remote

import android.util.Log
import com.example.module.core.data.source.remote.network.ApiResponse
import com.example.module.core.data.source.remote.network.ApiService
import com.example.module.core.data.source.remote.response.SimpleUser
import com.example.module.core.data.source.remote.response.User
import com.example.module.core.data.source.remote.response.UserGithubResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    fun searchUser(query: String): Flow<ApiResponse<List<SimpleUser>>> = flow {
        val users = apiService.getUserList(query).items
        if (users.isNotEmpty()) {
            emit(ApiResponse.Success(users))
        } else {
            emit(ApiResponse.Empty)
        }
    }.catch { e ->
        Log.d(TAG, "searchUserByUsername: ${e.message.toString()}")
        emit(ApiResponse.Error(e.message.toString()))
    }

    fun getGithubUsers(): Flow<ApiResponse<ArrayList<UserGithubResponse>>> = flow {
        val users = apiService.getGithubUsers().items
        Log.d(TAG, "getGithubUsers: $users")

        if (users.isNotEmpty()) {
            emit(ApiResponse.Success(users))
        } else {
            emit(ApiResponse.Empty)
        }
    }.catch { e ->
        Log.d(TAG, "getGithubUsers: ${e.message.toString()}")
        emit(ApiResponse.Error(e.toString()))
    }.flowOn(Dispatchers.IO)


    fun getDetailUsers(username: String): Flow<ApiResponse<User>> = flow {
            val user = apiService.getDetailUser(username)

            if (user.login.isNotEmpty()) {
                emit(ApiResponse.Success(user))
            } else {
                emit(ApiResponse.Empty)
            }
    }.catch { e ->
        Log.d(TAG, "getDetailUser: ${e.message.toString()}")
        emit(ApiResponse.Error(e.toString()))
    }.flowOn(Dispatchers.IO)

    fun getUserFollowers(id: String): Flow<ApiResponse<List<SimpleUser>>> = flow {
            val users = apiService.getFollowersList(id)
            if (users.isNotEmpty()) {
                emit(ApiResponse.Success(users))
            } else {
                emit(ApiResponse.Empty)
            }
    }.catch { e ->
        Log.d(TAG, "getUserFollowers: ${e.message.toString()}")
        emit(ApiResponse.Error(e.toString()))
    }

    companion object {
        private val TAG = RemoteDataSource::class.java.simpleName
    }
}