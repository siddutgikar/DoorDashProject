package com.sidd.doordash.injection

import android.content.Context
import com.sidd.doordash.data.AppDatabase
import com.sidd.doordash.data.RestaurantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DBInjectionModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideRestaurantDao(appDatabase: AppDatabase): RestaurantDao {
        return appDatabase.restaurantDao()
    }

}