package com.tech.fx;


import com.tech.properties.ProductProperty;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CustomListCell implements Callback<ListView<ProductProperty>, ListCell<ProductProperty>>{

	@Override
	public ListCell<ProductProperty> call(ListView<ProductProperty> param) {
		return new CustomCell();
	}

}
