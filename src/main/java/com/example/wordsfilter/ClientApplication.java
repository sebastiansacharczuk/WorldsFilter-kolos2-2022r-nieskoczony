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

public class ClientApplication extends Application {
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

        Thread receiveThread = new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                    Platform.runLater(() -> controller.incrementWordCountLabelText()); // Wywołanie metody na wątku JavaFX
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