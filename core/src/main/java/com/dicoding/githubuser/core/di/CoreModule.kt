package com.dicoding.githubuser.core.di

import androidx.room.Room
import com.dicoding.githubuser.core.data.UserRepository
import com.dicoding.githubuser.core.data.source.local.LocalDataSource
import com.dicoding.githubuser.core.data.source.local.room.UserDatabase
import com.dicoding.githubuser.core.data.source.remote.RemoteDataSource
import com.dicoding.githubuser.core.data.source.remote.network.ApiService
import com.dicoding.githubuser.core.domain.repository.IUserRepository
import com.dicoding.githubuser.core.utlis.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<UserDatabase>().userDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java, "User.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "*.github.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/1UPHAdcUbUoOcd5rDTD/0oMSnngCU6YzXzpByO4CCp4=")
            .add(hostname, "sha256/RQeZkB42znUfsDIIFWIRiYEcKl7nHwNFwWCrnMMJbVc=")
            .add(hostname, "sha256/r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IUserRepository> {
        UserRepository(
            get(),
            get(),
            get()
        )
    }
}