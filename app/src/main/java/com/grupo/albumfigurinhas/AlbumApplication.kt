package com.grupo.albumfigurinhas

import android.app.Application
import com.grupo.albumfigurinhas.data.remote.FirebaseRepositoryFactory

class AlbumApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(FirebaseRepositoryFactory.create(this))
    }
}
