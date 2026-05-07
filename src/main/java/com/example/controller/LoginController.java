package com.example.controller;

import com.example.App;
import com.example.dao.EczaciDAO;
import com.example.model.Eczaci;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    
    private App mainApp;
    private final EczaciDAO eczaciDAO = new EczaciDAO();

    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private Label errorLbl;

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLogin() {
        String u = userField.getText();
        String p = passField.getText();
        Eczaci loggedIn = eczaciDAO.girisYap(u, p);
        if (loggedIn != null) {
            mainApp.showDashboardScreen(loggedIn);
        } else {
            errorLbl.setText("Hatalı kullanıcı adı veya şifre!");
        }
    }

    @FXML
    private void handleBack() {
        if (mainApp != null) {
            mainApp.showMainScreen();
        }
    }
}
