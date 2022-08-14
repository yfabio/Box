package com.tech.properties;

import com.tech.model.Brand;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BrandProperty  {

	private LongProperty id = new SimpleLongProperty();
	private StringProperty title = new SimpleStringProperty();
	
	public BrandProperty() {
	}

	public BrandProperty(Brand brand) {
		setId(brand.getId());
		setTitle(brand.getTitle());
	}

	public LongProperty idProperty() {
		return this.id;
	}
	

	public long getId() {
		return this.idProperty().get();
	}
	

	public void setId(long id) {
		this.idProperty().set(id);
	}
	

	public StringProperty titleProperty() {
		return this.title;
	}
	

	public String getTitle() {
		return this.titleProperty().get();
	}
	

	public void setTitle( String title) {
		this.titleProperty().set(title);
	}
	

	
	

	
}
