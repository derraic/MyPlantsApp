package com.derra.myplantsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.derra.myplantsapp.add_edit_plant.PermissionHandler
import com.derra.myplantsapp.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePlantDatabase(app: Application): PlantDatabase {
        return Room.databaseBuilder(
            app,
            PlantDatabase::class.java,
            "plant.db"
        ) .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePlantRepository(db: PlantDatabase) : PlantRepository {
        return PlantRepositoryImpl(db.dao)
    }
    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun providePermissionHandler(context: Context): PermissionHandler {
        return PermissionHandler(context)
    }


    @Provides
    @Singleton
    fun provideNotificationDatabase(app: Application): NotificationDatabase {
        return Room.databaseBuilder(
            app,
            NotificationDatabase::class.java,
            "notification.db"
        ) .fallbackToDestructiveMigration().build()
    }
    @Provides
    @Singleton
    fun provideNotificationRepository(db: NotificationDatabase) : NotificationRepository {
        return NotificationRepositoryImpl(db.notificationDao)
    }

}