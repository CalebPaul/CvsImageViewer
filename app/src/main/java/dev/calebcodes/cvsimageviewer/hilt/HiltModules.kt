package dev.calebcodes.cvsimageviewer.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.calebcodes.cvsimageviewer.network.FlickrRepository
import dev.calebcodes.cvsimageviewer.network.FlickrService
import dev.calebcodes.cvsimageviewer.util.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HiltModules {

    @Singleton
    @Provides
    fun providesApiService(): FlickrService {
        return Retrofit
            .Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlickrService::class.java)
    }

    @Provides
    fun provideMainRepository(flickrService: FlickrService): FlickrRepository = FlickrRepository(flickrService = flickrService)
}