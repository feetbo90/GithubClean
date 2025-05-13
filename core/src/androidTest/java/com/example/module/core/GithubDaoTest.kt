package com.example.module.core

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.module.core.data.source.local.entity.SimpleUserEntity
import com.example.module.core.data.source.local.entity.UserGithubEntity
import com.example.module.core.data.source.local.room.GithubDao
import com.example.module.core.data.source.local.room.GithubDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class GithubDaoTest {

    private lateinit var database: GithubDatabase
    private lateinit var githubDao: GithubDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            GithubDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        githubDao = database.githubDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndCheckFavoriteUser() = runBlocking {
        val user = SimpleUserEntity(
            id = 1,
            avatarUrl = "https://avatar.url",
            login = "bale",
            isFavorite = true
        )

        githubDao.updateFavoriteUser(user)

        val result = githubDao.isFavoriteUser("bale").first()
        Assert.assertTrue(result)
    }

    @Test
    fun testGetFavoriteUsers() = runBlocking {
        // Siapkan data dengan beberapa user, beberapa favorite dan beberapa tidak
        val user1 = SimpleUserEntity(id = 1, avatarUrl = "url1", login = "user1", isFavorite = true)
        val user2 = SimpleUserEntity(id = 2, avatarUrl = "url2", login = "user2", isFavorite = false)
        val user3 = SimpleUserEntity(id = 3, avatarUrl = "url3", login = "user3", isFavorite = true)

        githubDao.updateFavoriteUser(user1)
        githubDao.updateFavoriteUser(user2)
        githubDao.updateFavoriteUser(user3)

        // Ambil favorite users
        val favoriteUsers = githubDao.getFavoriteUsers().first()

        Assert.assertEquals(2, favoriteUsers.size)
        Assert.assertTrue(favoriteUsers.any { it.login == "user1" })
        Assert.assertTrue(favoriteUsers.any { it.login == "user3" })
        Assert.assertFalse(favoriteUsers.any { it.login == "user2" })
    }

    @Test
    fun testInsertAndGetGithubUsers() = runBlocking {
        // Siapkan list UserGithubEntity
        val userList = listOf(
            UserGithubEntity(id = 10, login = "alpha", avatarUrl = "urlA"),
            UserGithubEntity(id = 5, login = "beta", avatarUrl = "urlB"),
            UserGithubEntity(id = 20, login = "gamma", avatarUrl = "urlC")
        )

        // Insert list user
        githubDao.insertGithubUser(userList)

        // Ambil semua github users, harus terurut berdasarkan id ASC
        val githubUsers = githubDao.getGithubUsers().first()

        // Pastikan jumlah sama
        Assert.assertEquals(userList.size, githubUsers.size)

        val expectedOrder = listOf(5, 10, 20)
        val actualOrder = githubUsers.map { it.id }
        Assert.assertEquals(expectedOrder, actualOrder)
    }
}

