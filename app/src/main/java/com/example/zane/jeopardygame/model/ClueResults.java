package com.example.zane.jeopardygame.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClueResults {

    @SerializedName("clues")
    @Expose
    private ArrayList<Clues> clues;

    public ArrayList<Clues> getClues() {
        return clues;
    }

    public void setClues(ArrayList<Clues> clues) {
        this.clues = clues;
    }
}
