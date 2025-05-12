package com.example.module.core.data

import android.util.Log
import com.example.module.core.data.source.local.LocalDataSource
import com.example.module.core.data.source.remote.RemoteDataSource
import com.example.module.core.data.source.remote.network.ApiResponse
import com.example.module.core.data.source.remote.response.UserGithubResponse
import com.example.module.core.domain.model.DetailUser
import com.example.module.core.domain.model.SimpleUsers
import com.example.module.core.domain.model.UserGithub
import com.example.module.core.domain.repository.IGithubRepository
import com.example.module.core.utils.AppExecutors
import com.example.module.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GithubRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IGithubRepository {
    override fun searchUser(query: String): Flow<Resource<List<SimpleUsers>>> = flow {
        emit(Resource.Loading()) // emit loading state

        remoteDataSource.searchUser(query).collect { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val users = DataMapper.mapResponseSimpleToDomain(response.data)
                    emit(Resource.Success(users))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("Tidak ada data followers"))
                }
                is ApiResponse.Error -> {
                    Log.e(TAG, "getUserFollowers error: ${response.errorMessage}")
                    emit(Resource.Error(response.errorMessage))
                }
            }
        }
    }.catch { e ->
        Log.e(TAG, "getUserFollowers: ${e.message}")
        emit(Resource.Error(e.message ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)


    override fun getGithubUser(): Flow<Resource<List<UserGithub>>> =
        object : NetworkBoundResource<List<UserGithub>, List<UserGithubResponse>>() {
            override fun loadFromDB(): Flow<List<UserGithub>> {
                return localDataSource.getGithubUsers().map { DataMapper.mapEntitiesToDomainNewGithub(it) }
            }

            override fun shouldFetch(data: List<UserGithub>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<UserGithubResponse>>> =
                remoteDataSource.getGithubUsers()

            override suspend fun saveCallResult(data: List<UserGithubResponse>) {
                val githubList = DataMapper.mapResponsesGithubUserToEntities(data)
                localDataSource.insertGithubUser(githubList)
            }

        }.asFlow()

    override fun getDetailUser(username: String): Flow<Resource<DetailUser>> = flow {
        emit(Resource.Loading())
        remoteDataSource.getDetailUsers(username).collect { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val user = DataMapper.mapResponseToDomainUser(response.data)

                    emit(Resource.Success(user))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("Data kosong"))
                }
                is ApiResponse.Error -> {
                    Log.e("UserViewModel", "Error: ${response.errorMessage}")
                    emit(Resource.Error(response.errorMessage))
                }
            }
        }
    }.catch { e ->
        Log.d(TAG, "getDetailUser: ${e.message}")
        emit(Resource.Error(e.message ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)

    override fun isFavoriteUser(id: String): Flow<Boolean> {
        return localDataSource.isFavoriteUser(id)
            .flowOn(Dispatchers.IO)
    }

    override fun getUserFollowers(id: String): Flow<Resource<List<SimpleUsers>>> = flow {
        emit(Resource.Loading()) // emit loading state

        remoteDataSource.getUserFollowers(id).collect { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val followers = DataMapper.mapResponseSimpleToDomain(response.data)
                    emit(Resource.Success(followers))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("Tidak ada data followers"))
                }
                is ApiResponse.Error -> {
                    Log.e(TAG, "getUserFollowers error: ${response.errorMessage}")
                    emit(Resource.Error(response.errorMessage))
                }
            }
        }
    }.catch { e ->
        Log.e(TAG, "getUserFollowers: ${e.message}")
        emit(Resource.Error(e.message ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)

    override fun getFavoriteUsers(): Flow<List<SimpleUsers>> {
        return localDataSource.getFavoriteUsers().map { DataMapper.mapEntitiesToDomainUser(it) }
    }

    override fun setFavoriteUser(user: DetailUser, state: Boolean) {
        val userEntity = DataMapper.mapDomainToEntity(user, state)
        appExecutors.diskIO().execute { localDataSource.setFavoriteUser(userEntity, state) }
    }

    companion object {
        private val TAG = GithubRepository::class.java.simpleName
    }
}