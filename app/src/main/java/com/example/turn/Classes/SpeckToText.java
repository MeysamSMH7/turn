package com.example.turn.Classes;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;

import com.example.turn.Activity.Main.Adapter.onClickInterface;

public class SpeckToText {
    Context context;
    onClickInterface onClickInterface;
    
    public SpeckToText(Context context){ this.context=context;}
    
    public void getText(onClickInterface onClickInterface ){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
/*
        context.startActivityForResult(intent, 123);
*/

        
    }
    
}
