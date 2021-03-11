package com.sidd.doordash.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RestaurantDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var restaurantDao: RestaurantDao
    private val restaurant1 = Restaurant(
        1,
        "Restaurant1",
        "Restaurant Desc",
        3.4f,
        100,
        100.21,
        "Some URL",
        "Some other url",
        2,
        Status(arrayOf(36, 36))
    )
    private val restaurant2 = Restaurant(
        2,
        "Restaurant1",
        "Restaurant Desc",
        3.4f,
        100,
        100.21,
        "Some URL",
        "Some other url",
        2,
        Status(arrayOf(36, 36))
    )
    private val restaurant3 = Restaurant(
        3,
        "Restaurant1",
        "Restaurant Desc",
        3.4f,
        100,
        100.21,
        "Some URL",
        "Some other url",
        2,
        Status(arrayOf(36, 36))
    )


    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        restaurantDao = database.restaurantDao()
        restaurantDao.insertAll(listOf(restaurant1, restaurant2, restaurant3))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetRestaurants() = runBlocking {
        val restaurantList = restaurantDao.getRestaurants().first()
        Assert.assertEquals(restaurantList.size, 3)
        Assert.assertEquals(restaurantList[0], restaurant1)
        Assert.assertEquals(restaurantList[1], restaurant2)
        Assert.assertEquals(restaurantList[2], restaurant3)
    }

    @Test
    fun testGetRestaurant() = runBlocking {
        Assert.assertEquals(restaurantDao.getRestaurant(restaurant1.id).first(), restaurant1)
    }

}