package com.grupo.albumfigurinhas.data.remote

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.MemoryCacheSettings
import com.google.firebase.firestore.PersistentCacheSettings
import com.grupo.albumfigurinhas.BuildConfig
import com.grupo.albumfigurinhas.data.repository.CompetitionRepository
import com.grupo.albumfigurinhas.data.repository.FakeCompetitionRepository

object FirebaseRepositoryFactory {
    private const val EMULATOR_HOST = "10.0.2.2"
    private const val EMULATOR_PORT = 8080
    private const val EMULATOR_APP_NAME = "firestore-emulator"
    private const val CACHE_SIZE_BYTES = 100L * 1024L * 1024L

    fun create(context: Context): CompetitionRepository {
        val useEmulator = BuildConfig.DEBUG && BuildConfig.USE_FIREBASE_EMULATOR
        if (!useEmulator && !BuildConfig.FIREBASE_CONFIGURED) {
            return FakeCompetitionRepository()
        }

        val firebaseApp = if (useEmulator) {
            emulatorApp(context)
        } else {
            productionApp(context)
        }
        val firestore = FirebaseFirestore.getInstance(firebaseApp)

        if (useEmulator) {
            firestore.useEmulator(EMULATOR_HOST, EMULATOR_PORT)
            firestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build())
                .build()
        } else {
            firestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setLocalCacheSettings(
                    PersistentCacheSettings.newBuilder()
                        .setSizeBytes(CACHE_SIZE_BYTES)
                        .build(),
                )
                .build()
            firestore.persistentCacheIndexManager?.enableIndexAutoCreation()
        }

        return FirestoreCompetitionRepository(firestore)
    }

    private fun productionApp(context: Context): FirebaseApp =
        FirebaseApp.getApps(context).firstOrNull { it.name == FirebaseApp.DEFAULT_APP_NAME }
            ?: checkNotNull(FirebaseApp.initializeApp(context)) {
                "google-services.json existe, mas o Firebase nao foi inicializado"
            }

    private fun emulatorApp(context: Context): FirebaseApp =
        FirebaseApp.getApps(context).firstOrNull { it.name == EMULATOR_APP_NAME }
            ?: FirebaseApp.initializeApp(
                context,
                FirebaseOptions.Builder()
                    .setProjectId(BuildConfig.FIREBASE_EMULATOR_PROJECT_ID)
                    .setApplicationId("1:1234567890:android:album-emulator")
                    .setApiKey("album-emulator-api-key")
                    .build(),
                EMULATOR_APP_NAME,
            )
}
