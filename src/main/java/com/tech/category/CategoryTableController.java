package com.tech.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.properties.CategoryProperty;
import com.tech.repository.CategoryRepository;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Component
public class CategoryTableController {

	@FXML
	public TableView<CategoryProperty> mTable;
	
	@Autowired
	private CategoryRepository catRepository;
	
	
	@FXML
	public void initialize() {
		List<CategoryProperty> categories = catRepository.findAll()
				 .stream()
				 .map(p -> new CategoryProperty(p))
				 .toList();

		mTable.getItems().addAll(categories);
	}
		
	public void resetSelection() {
		mTable.getSelectionModel().clearSelection();		
	}

	public void reload() {
		List<CategoryProperty> categories = catRepository.findAll()
				 .stream()
				 .map(p -> new CategoryProperty(p))
				 .toList();

		mTable.getItems().addAll(categories);
	}
	
	public void clear() {
		mTable.getItems().clear();
	}
		

}
