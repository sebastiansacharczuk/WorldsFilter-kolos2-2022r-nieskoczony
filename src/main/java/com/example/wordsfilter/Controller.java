package com.example.wordsfilter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {
    public TextField filterField;
    public ListView wordList;
    public Label wordCountLabel;
    public void incrementWordCountLabelText() {
        wordCountLabel.setText(String.valueOf(Integer.valueOf(wordCountLabel.getText()) + 1));
    }
}