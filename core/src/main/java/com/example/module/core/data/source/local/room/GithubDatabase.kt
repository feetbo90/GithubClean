package com.example.module.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.module.core.data.source.local.entity.SimpleUserEntity
import com.example.module.core.data.source.local.entity.UserGithubEntity

@Database(entities = [SimpleUserEntity::class, UserGithubEntity::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao
}