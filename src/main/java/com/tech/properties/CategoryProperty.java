package com.tech.properties;

import com.tech.model.Category;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CategoryProperty  {

	private LongProperty id = new SimpleLongProperty();
	private StringProperty title = new SimpleStringProperty();
	
	public CategoryProperty() {
	}

	public CategoryProperty(Category category) {
		setId(category.getId());
		setTitle(category.getTitle());
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
