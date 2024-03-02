package com.example.project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.project.StartApplication;
import com.example.project.entities.Client;
import com.example.project.service.Service;

public class LoginController {
    private Service service;
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;

    public void setService(Service service) {
        this.service = service;
    }

    public void handleLogin(ActionEvent actionEvent){
        try{
            String username = usernameField.getText();
            Client c = service.getClientByUsername(username);
            if(c == null)
                MessageAlert.showErrorMessage(null, Alert.AlertType.INFORMATION,"Clientul nu exista!");
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("clientView.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                ClientController clientController = fxmlLoader.getController();
                clientController.setService(service, c);
                stage.show();
                //((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}