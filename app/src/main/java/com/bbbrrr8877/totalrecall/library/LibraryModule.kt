package com.bbbrrr8877.totalrecall.library

import com.bbbrrr8877.totalrecall.core.Core
import com.bbbrrr8877.totalrecall.core.Module
import com.bbbrrr8877.totalrecall.library.data.LibraryCloudDataSource
import com.bbbrrr8877.totalrecall.library.data.LibraryRepository
import com.bbbrrr8877.totalrecall.library.presentation.LibraryCommunication
import com.bbbrrr8877.totalrecall.library.presentation.LibraryViewModel

class LibraryModule(private val core: Core) : Module<LibraryViewModel> {
    override fun viewModel() = LibraryViewModel(
        core.navigation(),
        core.provideDispatchersList(),
        LibraryRepository.Base(
            LibraryCloudDataSource.Base(core)
        ),
        LibraryCommunication.Base(),
    )
}