package com.bbbrrr8877.totalrecall.create_topics

import com.bbbrrr8877.totalrecall.FakeDispatchersList
import com.bbbrrr8877.totalrecall.FakeManageResource
import com.bbbrrr8877.totalrecall.FakeNavigationCommunication
import com.bbbrrr8877.totalrecall.FunctionsCallsStack
import com.bbbrrr8877.totalrecall.createTopics.data.CreateTopicResult
import com.bbbrrr8877.totalrecall.createTopics.data.CreateTopicsRepository
import com.bbbrrr8877.totalrecall.createTopics.presentation.CardListScreen
import com.bbbrrr8877.totalrecall.createTopics.presentation.CreateTopicScreen
import com.bbbrrr8877.totalrecall.createTopics.presentation.CreateTopicUiState
import com.bbbrrr8877.totalrecall.createTopics.presentation.CreateTopicsCommunication
import com.bbbrrr8877.totalrecall.createTopics.presentation.CreateTopicsViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CreateTopicViewModelTest {

    //region fields
    private lateinit var viewModel: CreateTopicsViewModel
    private lateinit var functionsCallsStack: FunctionsCallsStack
    private lateinit var repository: FakeRepository
    private lateinit var manageResource: FakeManageResource
    private lateinit var communication: FakeCommunication
    private lateinit var navigation: FakeNavigationCommunication
    private lateinit var dispatchersList: FakeDispatchersList
    //endregion

    @Before
    fun setup() {
        functionsCallsStack = FunctionsCallsStack.Base()
        repository = FakeRepository.Base(functionsCallsStack)
        manageResource = FakeManageResource("stub")
        communication = FakeCommunication.Base(functionsCallsStack)
        navigation = FakeNavigationCommunication.Base(functionsCallsStack)
        dispatchersList = FakeDispatchersList()
        viewModel = CreateTopicsViewModel(
            repository,
            manageResource,
            communication,
            navigation,
            dispatchersList,
        )
    }

    @Test
    fun `test create topic successful`() {
        repository.initWithExistingTopic(emptyList())
        repository.initWithCreateTopicResult(CreateTopicResult.Success)

        val topicName = "Topic Name"
        viewModel.checkTopic(name = topicName)
        repository.checkContainsCalled(value = topicName)
        communication.check(CreateTopicUiState.CanCreateTopic)

        viewModel.create(name = topicName)
        communication.check(CreateTopicUiState.Progress)
        repository.checkCreateCalled(value = topicName)
        navigation.check(CardListScreen)
        functionsCallsStack.checkStack(5)
    }

    @Test
    fun `test create topic unsuccessful`() {
        repository.initWithExistingTopic(emptyList())
        repository.initWithCreateTopicResult(CreateTopicResult.Failed(errorMessage = "network problem"))

        val topicName = "Topic Name"
        viewModel.checkTopic(name = topicName)
        repository.checkContainsCalled(value = topicName)
        communication.check(CreateTopicUiState.CanCreateTopic)

        viewModel.create(name = topicName)
        communication.check(CreateTopicUiState.Progress)
        repository.checkCreateCalled(value = topicName)
        communication.check(CreateTopicUiState.Error(errorMessage = "network problem"))
        functionsCallsStack.checkStack(5)
    }

    @Test
    fun `test topic with name already exists`() {
        val topicName = "Topic Name"
        repository.initWithExistingTopic(listOf(topicName))
        repository.initWithCreateTopicResult(CreateTopicResult.Success)

        viewModel.checkTopic(name = topicName)
        repository.checkContainsCalled(value = topicName)
        communication.check(CreateTopicUiState.TopicAlreadyExists)
        functionsCallsStack.checkStack(2)
    }

    interface FakeRepository : CreateTopicsRepository {

        fun checkContainsCalled(value: String)
        fun checkCreateCalled(value: String)
        fun initWithExistingTopic(list: List<String>)
        fun initWithCreateTopicResult(result: CreateTopicResult)

        class Base(private val functionsCallsStack: FunctionsCallsStack) : FakeRepository {

            private lateinit var createResult: CreateTopicResult
            private val nameList = mutableListOf<String>()
            private val containsCalledList = mutableListOf<String>()
            private var containsCalledIndex = 0
            private val createCalledList = mutableListOf<String>()
            private var createCalledIndex = 0

            override fun initWithExistingTopic(list: List<String>) {
                nameList.clear()
                nameList.addAll(list.map { it.lowercase() })
            }

            override fun initWithCreateTopicResult(result: CreateTopicResult) {
                createResult = result
            }

            override fun checkContainsCalled(value: String) {
                functionsCallsStack.checkCalled(CONTAINS_CALLED)
                assertEquals(value, containsCalledList[containsCalledIndex++])
            }

            override fun checkCreateCalled(value: String) {
                functionsCallsStack.checkCalled(CREATE_CALLED)
                assertEquals(value, createCalledList[createCalledIndex++])
            }

            override fun create(name: String): CreateTopicResult {
                createCalledList.add(name)
                functionsCallsStack.put(CREATE_CALLED)
                return createResult
            }

            override fun contains(name: String): Boolean {
                containsCalledList.add(name)
                functionsCallsStack.put(CONTAINS_CALLED)
                return nameList.contains(name.lowercase())
            }

            companion object {
                private const val CONTAINS_CALLED = " CreateTopicRepository#contains"
                private const val CREATE_CALLED = "CreateTopicRepository#create"
            }
        }

    }

    interface FakeCommunication : CreateTopicsCommunication {

        fun check(state: CreateTopicUiState)

        class Base(private val functionsCallsStack: FunctionsCallsStack) : FakeCommunication {

            private val list = mutableListOf<CreateTopicUiState>()
            private var index = 0

            override fun map(data: CreateTopicUiState) {
                functionsCallsStack.put(MAP_CALL)
                list.add(data)
            }

            override fun check(state: CreateTopicUiState) {
                assertEquals(state, list[index++])
                functionsCallsStack.checkCalled(MAP_CALL)
            }
        }

        companion object {
            private const val MAP_CALL = "CreateTopicCommunication#map"
        }

    }
}