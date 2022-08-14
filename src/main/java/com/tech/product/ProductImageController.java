package com.tech.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.service.ImageService;
import com.tech.ui.Icons;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Component
public class ProductImageController {

	@FXML
	Button mBrowser;
	
	@FXML
	public  ImageView mImage;
	
	@Autowired
	private ImageService imageService;

	public void resetImage() {
		Image image = new Image(imageService.image(Icons.DEFAULT_IMG));
		if(mImage.getImage()!= null && !(mImage.getImage().equals(image))) {
			mImage.setImage(image);			
		}
	}

}
