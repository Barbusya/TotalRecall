package com.bbbrrr8877.totalrecall.core

import android.content.Context
import com.bbbrrr8877.totalrecall.main.NavigationCommunication
import com.google.firebase.FirebaseApp
import com.google.gson.Gson

class Core(context: Context) : ProvideNavigation, ProvideStorage, ProvideManageResource,
    ProvideDispatchersList, ProvideFirebaseDatabase, ProvideLocalDatabase {

    init {
        FirebaseApp.initializeApp(context)
    }

    private val provideLocalDatabase = ProvideLocalDatabase.Base(context)
    private val provideDatabase = ProvideFirebaseDatabase.Base()
    private val manageResource = ManageResource.Base(context)
    private val navigation = NavigationCommunication.Base()
    private val storage = SimpleStorage.Base(
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    ).let { simpleStorage ->
        Storage.Base(simpleStorage, ObjectStorage.Base(simpleStorage, Gson()))
    }
    private val dispatchersList = DispatchersList.Base()

    override fun provideDispatchersList() = dispatchersList

    override fun navigation(): NavigationCommunication.Mutable {
        return navigation
    }

    override fun storage() = storage

    companion object {
        private const val STORAGE_NAME = "TOTAL RECALL APP DATA"
    }

    override fun manageResource() = manageResource

    override fun cloudDatabase() = provideDatabase.cloudDatabase()

    override fun roomDatabase() = provideLocalDatabase.roomDatabase()

}

interface ProvideManageResource {
    fun manageResource(): ManageResource
}

interface ProvideNavigation {

    fun navigation(): NavigationCommunication.Mutable
}

interface ProvideStorage {
    fun storage(): Storage
}

interface ProvideDispatchersList {
    fun provideDispatchersList(): DispatchersList
}