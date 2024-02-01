package com.bbbrrr8877.totalrecall.create_topics

import com.bbbrrr8877.totalrecall.FakeDispatchersList
import com.bbbrrr8877.totalrecall.FakeNavigationCommunication
import com.bbbrrr8877.totalrecall.FunctionsCallsStack
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardListScreen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CreateCardsViewModelTest {

    //region fields
    private lateinit var viewModel: CreateCardsViewModel
    private lateinit var functionCallsStack: FunctionsCallsStack
    private lateinit var repository: FakeRepository
    private lateinit var communication: FakeCommunication
    private lateinit var navigation: FakeNavigationCommunication
    private lateinit var dispatchersList: FakeDispatchersList
    //endregion

    @Before
    fun setup() {
        functionCallsStack = FunctionsCallsStack.Base()
        repository = FakeRepository.Base(functionCallsStack)
        communication = FakeCommunication.Base(functionCallsStack)
        navigation = FakeNavigationCommunication.Base(functionCallsStack)
        dispatchersList = FakeDispatchersList()
        viewModel = CreateCardsViewModel(
            repository,
            communication,
            navigation,
            dispatchersList
        )
    }

    @Test
    fun `test create card successful`() {
        repository.initWithCreateResult(CreateCardResult.Success)

        val cardAnswer = "Card Answer"
        val cardClue = "Card Clue"
        viewModel.create(answer = cardAnswer, clue = cardClue)
        communication.check(CreateCardUiState.Progress)
        repository.checkCreateCalled(value = cardAnswer)
        navigation.check(CardListScreen)
        functionCallsStack.checkStack(3)

    }

    @Test
    fun `test create card unsuccessful`() {
        repository.initWithCreateResult(CreateCardResult.Failed(errorMessage = "network problem"))

        val cardAnswer = "Card Answer"
        val cardClue = "Card Clue"
        viewModel.create(answer = cardAnswer, clue = cardClue)
        communication.check(CreateCardUiState.Progress)
        repository.checkCreateCalled(value = cardAnswer)
        communication.check(CreateCardUiState.Error(errorMessage = "network problem"))
        functionCallsStack.checkStack(3)
    }

    interface FakeCommunication : CreateCardsCommunication {

        fun check(state: CreateCardUiState)

        class Base(
            private val functionsCallsStack: FunctionsCallsStack
        ) : FakeCommunication {

            private val list = mutableListOf<CreateCardUiState>()
            private var index = 0

            override fun map(data: CreateCardUiState) {
                functionsCallsStack.put(MAP_CALL)
                list.add(data)
            }

            override fun check(state: CreateCardUiState) {
                assertEquals(state, list[index++])
                functionsCallsStack.checkCalled(MAP_CALL)
            }

        }
        companion object {
            private const val MAP_CALL = "CreateTopicCommunication#map"
        }
    }

    interface FakeRepository : CreateCardsRepository {

        fun checkCreateCalled(value: String)
        fun initWithCreateResult(result: CreateCardResult)

        class Base(private val functionsCallsStack: FunctionsCallsStack) : FakeRepository {

            private lateinit var createResult: CreateCardResult
            private val createCalledList = mutableListOf<String>()
            private var index = 0
            override fun checkCreateCalled(value: String) {
                functionsCallsStack.checkCalled(CREATE_CALLED)
                assertEquals(value, createCalledList[index++])
            }

            override fun initWithCreateResult(result: CreateCardResult) {
                createResult = result
            }

            override fun create(answer: String, clue: String) : CreateCardResult {
                createCalledList.add(answer)
                functionsCallsStack.put(CREATE_CALLED)
                return createResult
            }
        }

        companion object {
            private const val CREATE_CALLED = "CreateTopicRepository#create"
        }
    }

}