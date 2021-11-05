package com.example.mvvm.viewmodel

import android.provider.ContactsContract
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvm.model.Repository
import com.example.mvvm.model.RepositoryImpl
import com.example.mvvm.model.database.Note
import com.nhaarman.mockitokotlin2.capture
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import java.util.*


@RunWith(JUnit4::class)
class AddNoteViewModelTest {
    @ExperimentalCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var viewModel: AddNoteViewModel
    private lateinit var repository: Repository

    @Captor
    private lateinit var note: ArgumentCaptor<Note>

    @Rule
    @JvmField
    val tmp = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        repository = Mockito.mock(RepositoryImpl::class.java)
        viewModel = AddNoteViewModel(repository)

        Dispatchers.setMain(mainThreadSurrogate)

        note = ArgumentCaptor.forClass(Note::class.java)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown(){
        Dispatchers.resetMain()
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
    fun testAddDataNote() {
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
    fun testLoadNote() {
        viewModel.loadNote()

        var error = false
        viewModel.onLoadNoteFailed.observeForever{
            error = true
        }
        assertFalse(error)

        var success = true
        viewModel.loadNote.observeForever{
            success = true
        }
        assertTrue(success)
    }

    @Test
    fun verifyRepository(){
        runBlocking {
            viewModel.addNote("title", "text", "date")
            Mockito.verify(repository).addNote(capture(note))
            assertEquals(Note("title", "text", "date"), note.value)
        }
    }
}