package com.example.fluentifyapp.di

import com.example.fluentifyapp.data.api.UserService
import com.example.fluentifyapp.data.repository.AuthRepository
import com.example.fluentifyapp.data.repository.AuthRepositoryImpl
import com.example.fluentifyapp.data.repository.UserRepository
import com.example.fluentifyapp.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)  // Makes this module available throughout the app
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userService: UserService
    ): UserRepository {
        return UserRepositoryImpl(userService)
    }


}
