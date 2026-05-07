package com.example.controller;

import com.example.App;
import javafx.fxml.FXML;

public class MainController {
    
    private App mainApp;

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleSearchClick() {
        if (mainApp != null) {
            mainApp.showSearchScreen();
        }
    }

    @FXML
    private void handleLoginClick() {
        if (mainApp != null) {
            mainApp.showLoginScreen();
        }
    }
}
