package com.tech.fx;

import com.tech.home.UpdateView;
import com.tech.properties.ProductProperty;
import com.tech.ui.UI;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;

public class CustomCell extends ListCell<ProductProperty> {

	@Override
	protected void updateItem(ProductProperty product, boolean empty) {
		super.updateItem(product, empty);

		try {
			if (product == null || empty) {
				setText(null);
				setGraphic(null);
			} else {
				UpdateTaskService updateTaskService = new UpdateTaskService(product);
				updateTaskService.start();
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while loading products into the list");
		}

	}

	private class UpdateTaskService extends Service<Void> {
		
		FXMLLoader fxmlLoader = new FXMLLoader();
		private Pane root = new Pane();
		private ProductProperty product;

		public UpdateTaskService(ProductProperty product) {
			this.product = product;
		}		

		private class UpdateTask extends Task<Void> {
			
			@Override
			protected Void call() throws Exception {
				fxmlLoader.setLocation(getClass().getResource(UI.CARD_VIEW.url()));
				root = fxmlLoader.load();
				UpdateView updateView = fxmlLoader.getController();
				updateView.update(product);
				return null;
			}
			

			@Override
			protected void succeeded() {				
				setGraphic(root);
			}

		}

		@Override
		protected Task<Void> createTask() {
			return new UpdateTask();
		}
	}

}
