package com.derra.myplantsapp.di

import android.app.Application
import androidx.room.Room
import com.derra.myplantsapp.data.PlantDao
import com.derra.myplantsapp.data.PlantDatabase
import com.derra.myplantsapp.data.PlantRepository
import com.derra.myplantsapp.data.PlantRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        ).build()
    }

    @Provides
    @Singleton
    fun providePlantRepository(db: PlantDatabase) : PlantRepository {
        return PlantRepositoryImpl(db.dao)
    }
}