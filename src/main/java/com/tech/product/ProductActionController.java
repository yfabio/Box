package com.tech.product;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

@Component
public class ProductActionController {

	@FXML 
	Button mUpsert;
	
	@FXML 
	ImageView mUpsertImg;

	@FXML 
	Button mCancel;

	@FXML 
	Button mDelete;

}
