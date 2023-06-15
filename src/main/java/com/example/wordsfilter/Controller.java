package com.example.wordsfilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class Controller {
    public TextField filterField;
    public ListView wordList;
    public Label wordCountLabel;
    public void incrementWordCountLabelText() {
        wordCountLabel.setText(String.valueOf(Integer.valueOf(wordCountLabel.getText()) + 1));
    }
    public void setWordList(List<String> words){
        ObservableList<String> observableList = FXCollections.observableArrayList(words);
        wordList.setItems(observableList);
    }


}