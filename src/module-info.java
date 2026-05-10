module ilacsistemi {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
	opens com.ilacsistemi.controller to javafx.fxml;
	opens com.ilacsistemi.model to javafx.base;
	
	exports application;
	exports com.ilacsistemi.controller;
	exports com.ilacsistemi.model;
	exports com.ilacsistemi.util;
}
