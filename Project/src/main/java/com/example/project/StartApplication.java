package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.example.project.controller.LoginController;
import com.example.project.repository.ClientRepo;
import com.example.project.repository.FlightRepo;
import com.example.project.repository.TicketRepo;
import com.example.project.service.Service;

import java.io.IOException;

public class StartApplication extends Application {
        Service service;

        @Override
        public void start(Stage primaryStage) throws IOException {
            this.service = new Service(new ClientRepo("jdbc:postgresql://localhost:5432/examen","postgres","J!@#$D%^&*4"),
                    new FlightRepo("jdbc:postgresql://localhost:5432/examen","postgres","J!@#$D%^&*4"),
                    new TicketRepo("jdbc:postgresql://localhost:5432/examen","postgres","J!@#$D%^&*4"));

            primaryStage.setTitle("START PAGE");
            startView(primaryStage);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }

        private void startView(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("loginView.fxml"));
            AnchorPane Layout = fxmlLoader.load();
            stage.setScene(new Scene(Layout));

            LoginController startController = fxmlLoader.getController();
            startController.setService(service);
        }
}