package com.example;

import com.example.controller.MainController;
import com.example.controller.SearchController;
import com.example.controller.LoginController;
import com.example.controller.DashboardController;
import com.example.model.Eczaci;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Eczane/İlaç Stok Takip Sistemi");
        showMainScreen();
        stage.show();
    }

    public void showMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/MainView.fxml"));
            Parent root = loader.load();
            MainController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSearchScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/SearchView.fxml"));
            Parent root = loader.load();
            SearchController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/LoginView.fxml"));
            Parent root = loader.load();
            LoginController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDashboardScreen(Eczaci loggedInUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/DashboardView.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.setMainApp(this, loggedInUser);
            primaryStage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
