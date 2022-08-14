package com.tech.home;

import org.springframework.stereotype.Component;

import com.tech.properties.ProductProperty;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

@Component
public class CardController implements UpdateView {

	@FXML
	private Label mCardFooter;

	@FXML
	private Label mCardHeader;

	@FXML
	private ImageView mCardImage;

	@FXML
	private Label mCardText;

	@FXML
	private Label mCardTitle;

	@Override
	public void update(ProductProperty product) {		
//		mCardImage.imageProperty().bind(product.imageProperty());
//		mCardHeader.textProperty().bind(product.categoryProperty());
//		mCardTitle.textProperty().bind(product.nameProperty());
//		mCardText.textProperty().bind(product.detailProperty());
		
		mCardImage.setImage(product.getImage());
		mCardHeader.setText(product.getCategory());
		mCardTitle.setText(product.getName());
		mCardText.setText(product.getDetail());
		
		
		mCardFooter.textProperty().bind(Bindings.when(product.packedProperty())
				.then(new SimpleStringProperty("It's in the bag!"))
				.otherwise(new SimpleStringProperty("")));
	}

}
