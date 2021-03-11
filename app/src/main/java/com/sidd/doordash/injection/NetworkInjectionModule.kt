package com.sidd.doordash.injection

import com.sidd.doordash.api.DoorDashService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkInjectionModule {

    @Singleton
    @Provides
    fun getDoorDashService(): DoorDashService {
        return DoorDashService.create()
    }

}