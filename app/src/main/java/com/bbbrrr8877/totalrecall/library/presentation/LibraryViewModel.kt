package com.bbbrrr8877.totalrecall.library.presentation

import com.bbbrrr8877.android.BaseViewModel
import com.bbbrrr8877.android.DispatchersList
import com.bbbrrr8877.common.GoBack
import com.bbbrrr8877.common.Init
import com.bbbrrr8877.common.ReloadWithError
import com.bbbrrr8877.common.ShowProfile
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.library.data.LibraryRepository
import com.bbbrrr8877.totalrecall.main.NavigationCommunication
import com.bbbrrr8877.totalrecall.profile.presentation.ProfileScreen
import com.bbbrrr8877.totalrecall.topics.presentation.TopicsListScreen

class LibraryViewModel(
    private val navigation: NavigationCommunication.Update,
    dispatchersList: DispatchersList,
    private val libraryRepository: LibraryRepository,
    private val communication: LibraryCommunication,
) : BaseViewModel(dispatchersList), LibraryViewModelActions {

    override fun liveData() = communication.liveData()

    override fun init(firstRun: Boolean) {
        communication.map(LibraryUiState.Progress)
        libraryRepository.init(this)
    }

    override fun reload() {
        handle({ libraryRepository.data() }) {
            communication.map(LibraryUiState.Base(it))
        }
    }

    override fun error(message: String) = communication.map(LibraryUiState.Error(message))

    override fun showProfile() = navigation.map(ProfileScreen)

    override fun retry() {
        communication.map(LibraryUiState.Progress)
        reload()
    }

    override fun load(libraryItemInfo: LibraryItemInfo) {
        TODO("Not yet implemented")
    }

    override fun goBack() = navigation.map(TopicsListScreen)
}

interface LibraryViewModelActions : Init, Communication.Observe<LibraryUiState>,
    ReloadWithError, ShowProfile, LoadClickListener, GoBack
