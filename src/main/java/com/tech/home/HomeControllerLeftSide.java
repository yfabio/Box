package com.tech.home;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

@Component
public class HomeControllerLeftSide {

	@FXML
	ComboBox<String> mNames;

	@FXML
	ComboBox<String> mBrands;	

	@FXML
	Button mSearch;

	@FXML
	Button mReload;

}
