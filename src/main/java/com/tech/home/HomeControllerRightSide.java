package com.tech.home;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

@Component
public class HomeControllerRightSide {

	@FXML
	ComboBox<String> mNames;

	@FXML
	ComboBox<String> mBrands;

	@FXML
	Label mTotal;

	@FXML
	Label mWeight;

	@FXML Button mClean;

	@FXML Button mScale;



}
