package com.deyber.beersommelier.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.deyber.beersommelier.utils.constants.DataStoreConstants.DATASTORENAME

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATASTORENAME
)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule{
    @Provides
    @Singleton
    fun provideOnBoardPreference(@ApplicationContext context: Context): DataStore<Preferences> {
     return context.userDataStore
    }
}