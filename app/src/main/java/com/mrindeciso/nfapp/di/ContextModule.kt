package com.mrindeciso.nfapp.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object ContextModule {

    @Provides
    fun getContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

}