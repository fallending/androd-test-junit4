package com.example.notetaker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.notetaker.R;

import dagger.android.support.DaggerAppCompatActivity;

public class NotesListActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
    }
}