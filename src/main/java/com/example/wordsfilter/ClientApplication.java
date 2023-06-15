package com.example.wordsfilter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClientApplication extends Application {
    private List<String> words;
    private Socket socket;
    private PrintWriter writer;
    private Controller controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("WordsFilter");
        stage.setScene(scene);
        stage.show();

        socket = new Socket("localhost", 5000);

        controller = fxmlLoader.getController(); // Przypisanie kontrolera

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);

        words = new ArrayList<>();
        Comparator<String> comparator = Comparator.comparing(str -> str.split(" ")[1]);

        Thread receiveThread = new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);

                    LocalTime currentTime = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String formattedTime = currentTime.format(formatter);
                    words.add(formattedTime+ " " + message);

                    Collections.sort(words, comparator);
                    Platform.runLater(() -> controller.incrementWordCountLabelText()); // Wywołanie metody na wątku JavaFX
                    Platform.runLater(() -> controller.setWordList(words));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receiveThread.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (socket != null) {
            socket.close();
        }
    }

    public void send(String message) {
        writer.println(message);
    }

    public void broadcast(String message) {
        writer.println(message);
    }

    public static void main(String[] args) {
        launch();
    }
}