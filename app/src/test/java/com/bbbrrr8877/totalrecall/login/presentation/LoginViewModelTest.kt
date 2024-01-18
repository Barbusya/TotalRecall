package com.bbbrrr8877.totalrecall.login.presentation

import com.bbbrrr8877.totalrecall.FakeDispatchersList
import com.bbbrrr8877.totalrecall.FakeManageResource
import com.bbbrrr8877.totalrecall.FakeNavigationCommunication
import com.bbbrrr8877.totalrecall.FunctionsCallsStack
import com.bbbrrr8877.totalrecall.login.data.LoginRepository
import com.bbbrrr8877.totalrecall.login.data.LoginResult
import com.bbbrrr8877.totalrecall.main.NavigationCommunication
import com.bbbrrr8877.totalrecall.main.Screen
import com.bbbrrr8877.totalrecall.profile.presentation.ProfileScreen
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class LoginViewModelTest {

    //region fields
    private lateinit var functionsCallsStack: FunctionsCallsStack
    private lateinit var repository: FakeLoginRepository
    private lateinit var manageResource: FakeManageResource
    private lateinit var communication: FakeLoginCommunication
    private lateinit var navigation: FakeNavigationCommunication
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dispatchesList: FakeDispatchersList
    //endregion

    @Before
    fun setup() {
        functionsCallsStack = FunctionsCallsStack.Base()
        repository = FakeLoginRepository.Base(functionsCallsStack)
        dispatchesList = FakeDispatchersList()
        manageResource = FakeManageResource("stub")
        communication = FakeLoginCommunication.Base(functionsCallsStack)
        navigation = FakeNavigationCommunication.Base(functionsCallsStack)
        loginViewModel = LoginViewModel(
            repository,
            dispatchesList,
            manageResource,
            communication,
            navigation
        )
    }

    @Test
    fun `test user not logged in`() {
        repository.initUserLoggedIn(false)

        loginViewModel.init(true)
        repository.checkUserNotLoggedInCall()
        communication.check(LoginUiState.Initial)

        functionsCallsStack.checkStack(2)
    }

    @Test
    fun `test user logged in`() {
        repository.initUserLoggedIn(true)

        loginViewModel.init(true)
        repository.checkUserNotLoggedInCall()
        communication.check(LoginUiState.Auth(manageResource))

        functionsCallsStack.checkStack(2)
    }

    @Test
    fun `test login successful`() {
        loginViewModel.login()
        communication.check(LoginUiState.Auth(manageResource))

        loginViewModel.handleResult(FakeAuthResultWrapper.Successful)
        repository.checkHandleResultCall(FakeAuthResultWrapper.Successful)
        navigation.check(ProfileScreen)

        functionsCallsStack.checkStack(3)
    }

    @Test
    fun `test login failed`() {
        loginViewModel.login()
        communication.check(LoginUiState.Auth(manageResource))

        loginViewModel.handleResult(FakeAuthResultWrapper.Failed)
        repository.checkHandleResultCall(FakeAuthResultWrapper.Failed)
        communication.check(LoginUiState.Error("stub error"))
        functionsCallsStack.checkStack(3)
    }

    @Test
    fun `test second opening`() {
        loginViewModel.init(false)
        functionsCallsStack.checkStack(0)
    }


    interface FakeLoginRepository : LoginRepository {

        fun checkHandleResultCall(authResult: AuthResultWrapper)
        fun checkUserNotLoggedInCall()
        fun initUserLoggedIn(userLoggedIn: Boolean)

        class Base(private val functionsCallsStack: FunctionsCallsStack) : FakeLoginRepository {

            private var userIsLoggedIn = false
            private var authResultList = mutableListOf<AuthResultWrapper>()
            private var authResultListIndex = 0

            override fun initUserLoggedIn(userLoggedIn: Boolean) {
                this.userIsLoggedIn = userLoggedIn
            }

            override fun checkUserNotLoggedInCall() {
                functionsCallsStack.checkCalled(USER_NOT_LOGGED_IN_CALL)
            }

            override fun userNotLoggedIn(): Boolean {
                functionsCallsStack.put(USER_NOT_LOGGED_IN_CALL)
                return !userIsLoggedIn
            }

            override fun checkHandleResultCall(authResult: AuthResultWrapper) {
                assertEquals(authResult, authResultList[authResultListIndex++])
                functionsCallsStack.checkCalled(HANDLE_RESULT_CALL)
            }

            override suspend fun handleResult(authResult: AuthResultWrapper): LoginResult {
                authResultList.add(authResult)
                functionsCallsStack.put(HANDLE_RESULT_CALL)
                return if (authResult.isSuccessful())
                    LoginResult.Success
                else
                    LoginResult.Failed("stub error")
            }

            companion object {
                private const val USER_NOT_LOGGED_IN_CALL = "LoginRepository#userNotLoggedIn"
                private const val HANDLE_RESULT_CALL = "LoginRepository#handleResult"
            }
        }
    }

    interface FakeLoginCommunication : LoginCommunication {


        fun check(state: LoginUiState)

        class Base(private val functionCallsStack: FunctionsCallsStack) : FakeLoginCommunication {

            private val list = mutableListOf<LoginUiState>()
            private var index = 0

            override fun map(source: LoginUiState) {
                functionCallsStack.put(MAP_CALL)
                list.add(source)
            }

            override fun check(state: LoginUiState) {
                assertEquals(state, list[index++])
                functionCallsStack.checkCalled(MAP_CALL)
            }

            companion object {
                private const val MAP_CALL = "LoginCommunication#map"
            }
        }

    }

    interface FakeAuthResultWrapper : AuthResultWrapper {

        object Successful : FakeAuthResultWrapper {

            override fun isSuccessful(): Boolean = true
            override fun task(): Task<GoogleSignInAccount> {
                TODO("Not yet implemented")
            }
        }

        object Failed : FakeAuthResultWrapper {

            override fun isSuccessful(): Boolean = false
            override fun task(): Task<GoogleSignInAccount> {
                TODO("Not yet implemented")
            }
        }

    }

}

