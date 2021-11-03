package com.example.mvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvm.model.Repository
import com.example.mvvm.model.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import kotlin.random.Random


@RunWith(JUnit4::class)
class MainViewModelTest {
    private var mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var viewModel: MainViewModel
    private lateinit var repository: Repository

    @Rule
    @JvmField
    val tmp = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = Mockito.mock(RepositoryImpl::class.java)
        viewModel = MainViewModel(repository)

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun testAddEmptyNote() {
        viewModel.addNote("", "", "")

        var error = false
        viewModel.onSuccessSaveNote.observeForever {
            error = true
        }
        assertFalse(error)

        var success = false
        viewModel.onErrorSaveNote.observeForever {
            success = true
        }
        assertTrue(success)

    }

    @Test
    fun testAddNullNote() {
        viewModel.addNote(null, null, "")

        var error = false
        viewModel.onSuccessSaveNote.observeForever {
            error = true
        }
        assertFalse(error)

        var success = false
        viewModel.onErrorSaveNote.observeForever {
            success = true
        }
        assertTrue(success)
    }

    @Test
    fun testAddNDataNote() {
        viewModel.addNote("Data", "Data value", "25 окт. 2021 г.")

        var error = false
        viewModel.onErrorSaveNote.observeForever {
            error = true
        }
        assertFalse(error)

        var success = false
        viewModel.onSuccessSaveNote.observeForever {
            success = true
        }
        assertTrue(success)
    }

    @Test
    fun testPageSelected() {
        viewModel.pageSelected(Random.nextInt())

        var success = false
        viewModel.currentNote.observeForever {
            success = true
        }

        assertFalse(!success)
        assertTrue(success)
    }

}