package com.example.githubappclean.core.data.source.local.room

import androidx.room.*
import com.example.githubappclean.core.data.source.local.entity.SimpleUserEntity
import com.example.githubappclean.core.data.source.local.entity.UserGithubEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDao {
    @Query("SELECT * FROM user where is_favorite = 1")
    fun getFavoriteUsers(): Flow<List<SimpleUserEntity>>

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAllUsers(): Flow<List<SimpleUserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateFavoriteUser(user: SimpleUserEntity)

    @Query("UPDATE user SET is_favorite = :isFavorite WHERE login = :login")
    suspend fun updateFavoriteStatus(login: String, isFavorite: Boolean)


    @Query("SELECT * FROM GithubUser ORDER BY id ASC")
    fun getGithubUsers(): Flow<List<UserGithubEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGithubUser(userList: List<UserGithubEntity>)

    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :login AND is_favorite = 1)")
    fun isFavoriteUser(login: String): Flow<Boolean>
}