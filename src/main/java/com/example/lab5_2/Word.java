package com.example.lab5_2;

import java.util.ArrayList;

public class Word {
    public ArrayList<String> badWords = new ArrayList<String>();
    public ArrayList<String> goodWords = new ArrayList<String>();

    public Word(){
        goodWords.add("happy");
        goodWords.add("enjoy");
        goodWords.add("life");
        badWords.add("fuck");
        badWords.add("olo");
    }
}
