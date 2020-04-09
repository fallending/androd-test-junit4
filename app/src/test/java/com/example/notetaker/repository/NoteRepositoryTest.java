package com.example.notetaker.repository;

import com.example.notetaker.db.NoteDao;
import com.example.notetaker.models.Note;
import com.example.notetaker.ui.Resource;
import com.example.notetaker.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;

import static com.example.notetaker.repository.NoteRepository.INSERT_FAILURE;
import static com.example.notetaker.repository.NoteRepository.INSERT_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static com.example.notetaker.util.TestUtil.TEST_NOTE_1;
import static org.mockito.Mockito.when;

public class NoteRepositoryTest {

    private static final Note NOTE1 = new Note(TestUtil.TEST_NOTE_1);

    //system under test
    private NoteRepository noteRepository;

    //this is a unit test, so unlike instrumentation tests, it cannot depend on the Android Framework
    //therefore, we use Mockito to mock components that utilise components of the Android Framework
    private NoteDao noteDao;

    //this method will be called before each of the @Tests
    //we want a fresh DAO and repository before each test, therefore use this   
    @BeforeEach
    public void init(){
        noteDao = mock(NoteDao.class);
        noteRepository = new NoteRepository(noteDao);
    }

    @Test
    void insertNote_returnRow() throws Exception{
        //arrange
        final Long insertedRow = 1L;
        final Single<Long> returnedData = Single.just(insertedRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        //act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst(); //use blockingFirst when returning a Flowable

        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        //assert
        assertEquals(Resource.success(1, INSERT_SUCCESS), returnedValue);
    }

    @Test
    void insertNote_returnFail() throws Exception {
        final Long insertedRow = -1L;
        final Single<Long> returnedData = Single.just(insertedRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst(); //use blockingFirst when returning a Flowable

        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        //assert
        assertEquals(Resource.error(null, INSERT_FAILURE), returnedValue);
    }

    @Test
    void insertNoteNullTitle_throwsException() throws Exception{
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Note note = new Note(TEST_NOTE_1);
                note.setTitle(null);

                noteRepository.insertNote(note);
            }
        });
    }
}