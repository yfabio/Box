package com.tech.brand;

import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@Component
public class BrandFormController {

	@FXML
	public TextField mTitle;

	public void requestFocus() {
		mTitle.requestFocus();
	}

	public void clear() {
		mTitle.clear();
	}

}
