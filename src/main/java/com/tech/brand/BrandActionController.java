package com.tech.brand;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

@Component
public class BrandActionController {

	@FXML
	Button mCancel;

	@FXML
	Button mDelete;

	@FXML
	Button mUpsert;

	@FXML
	ImageView mUpsertImg;
	

}
