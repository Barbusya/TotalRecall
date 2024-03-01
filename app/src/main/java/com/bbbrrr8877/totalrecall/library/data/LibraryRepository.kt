package com.bbbrrr8877.totalrecall.library.data

import com.bbbrrr8877.common.InitialReloadCallback
import com.bbbrrr8877.common.ReloadWithError

interface LibraryRepository : InitialReloadCallback {

    suspend fun data(): List<LibraryList>

    class Base(
        private val cloudDataSource: LibraryCloudDataSource
    ) : LibraryRepository {
        override suspend fun data() = try {
            val list = mutableListOf<LibraryList>()
            val library = cloudDataSource.library()
            if (library.isEmpty())
                list.add(LibraryList.NoLibraryHint)
            else
                list.addAll(library)
            list
        } catch (e: Exception) {
            listOf(LibraryList.Error(e.message ?: "error"))
        }

        override fun init(reload: ReloadWithError) = cloudDataSource.init(reload)
    }
}