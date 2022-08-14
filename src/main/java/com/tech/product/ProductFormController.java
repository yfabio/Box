package com.tech.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@Component
public class ProductFormController {

	@FXML
	public ComboBox<String> mCategory;
	
	@FXML
	public ComboBox<String> mBrand;

	@FXML
	public TextField mColor;

	@FXML
	public TextArea mDetail;

	@FXML
	public TextField mName;

	@FXML
	public CheckBox mPacked;

	@FXML
	public TextField mQty;

	@FXML
	public TextField mSize;

	@FXML
	public ComboBox<String> mState;

	@FXML
	public  TextField mWeight;
	
	@Autowired
	ProductImageController productImageController;

	public void requestFocus() {
		mName.requestFocus();		
	}

	public void clear() {
		mBrand.getSelectionModel().clearSelection();
		mCategory.getSelectionModel().clearSelection();
		mColor.clear();
		mDetail.clear();
		mName.clear();
		mPacked.selectedProperty().set(false);
		mQty.clear();
		mSize.clear();
		mState.getSelectionModel().clearSelection();
		mWeight.clear();		
	}

}
