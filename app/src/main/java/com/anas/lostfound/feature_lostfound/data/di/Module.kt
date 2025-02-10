package com.anas.lostfound.feature_lostfound.data.di

import android.content.Context
import androidx.room.Room
import com.anas.lostfound.feature_lostfound.data.local.ItemsDao
import com.anas.lostfound.feature_lostfound.data.local.ItemsDatabase
import com.anas.lostfound.feature_lostfound.data.remote.RemoteApi
import com.anas.lostfound.feature_lostfound.data.repo.ListRepoImpl
import com.anas.lostfound.feature_lostfound.domain.repo.ListRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

// dependencies
// injection file, tells us how to provide the instances
//
//
// here we need to provide instances of the database class
// we need function that tells how to get instances of dao and database

object Module {


    @Provides
    fun providesRoomDao(database: ItemsDatabase): ItemsDao {
        return database.dao
    }

    // api of retrofit

    @Provides
    fun providesRetrofitApi(retrofit: Retrofit): RemoteApi {
        return retrofit.create(RemoteApi:: class.java)
    }



    // retro fit instance
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()

            )
            .baseUrl("https://lostfound-58400-default-rtdb.europe-west1.firebasedatabase.app/")
            .build()
    }



    // provides room database so we want a singleton here
    @Provides
    @Singleton
    fun providesRoomDb(
        @ApplicationContext appContext: Context
    ): ItemsDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            ItemsDatabase:: class.java,
            "lost_found_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesRepo(db: ItemsDatabase, api: RemoteApi, @IoDispatcher dispatcher: CoroutineDispatcher): ListRepo
    {
        return ListRepoImpl(db.dao, api, dispatcher)
    }

}


