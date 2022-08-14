package com.tech.menu;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.brand.BrandController;
import com.tech.category.CategoryController;
import com.tech.home.HomeController;
import com.tech.navigation.Navigator;
import com.tech.product.ProductController;
import com.tech.service.NavigationService;
import com.tech.service.WindownService;
import com.tech.ui.UI;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;

@Component
public class ToolBarController {

	private ObjectProperty<Navigator> navigation = new SimpleObjectProperty<Navigator>();

	@Autowired
	private WindownService action;

	@Autowired
	private NavigationService navigationService;

	@PostConstruct
	public void postConstruct() {
		navigation.set(navigationService.getNavigator(getClass().getResource(UI.HOME.url()), HomeController.class));
	}

	@FXML
	public void onHome() {
		navigation.set(navigationService.getNavigator(getClass().getResource(UI.HOME.url()), HomeController.class));
	}

	@FXML
	public void onProduct() {
		navigation
				.set(navigationService.getNavigator(getClass().getResource(UI.PRODUCT.url()), ProductController.class));
	}

	@FXML
	public void onCategory() {
		navigation.set(
				navigationService.getNavigator(getClass().getResource(UI.CATEGORY.url()), CategoryController.class));
	}
	
	@FXML
	public void onBrand() {
		navigation.set(
				navigationService.getNavigator(getClass().getResource(UI.BRAND.url()), BrandController.class));
	}
	

	@FXML
	public void onShutdown() {
		action.shutdown();
	}

	public ObjectProperty<Navigator> getNavigator() {
		return navigation;
	}

	

}
