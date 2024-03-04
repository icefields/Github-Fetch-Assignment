package org.scotia.githubfetcher

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GithubFetcherApplication : Application(), ImageLoaderFactory {
    /**
     * caching images was not a requirement, I added this to get logs from the image loader
     * because of an error I was getting.
     * I'm leaving it here since image caching is nice to have, I think, in this app's case
     */
    override fun newImageLoader(): ImageLoader = ImageLoader(this).newBuilder()
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCache {
            MemoryCache.Builder(this)
                .maxSizePercent(0.2)
                .strongReferencesEnabled(true)
                .build()
        }
        .diskCachePolicy(CachePolicy.ENABLED)
        .diskCache {
            DiskCache.Builder()
                .maxSizePercent(0.08)
                .directory(cacheDir)
                .build()

        }
        .logger(DebugLogger()) // TODO remove in production
        .build()
}
