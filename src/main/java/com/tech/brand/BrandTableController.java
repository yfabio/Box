package com.tech.brand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.properties.BrandProperty;
import com.tech.repository.BrandRepository;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Component
public class BrandTableController {

	@FXML
	public TableView<BrandProperty> mTable;
	
	@Autowired
	private BrandRepository brandRepository;
	
	
	@FXML
	public void initialize() {
		List<BrandProperty> brands = brandRepository.findAll()
				 .stream()
				 .map(p -> new BrandProperty(p))
				 .toList();

		mTable.getItems().addAll(brands);
	}
		
	public void resetSelection() {
		mTable.getSelectionModel().clearSelection();		
	}

	public void reload() {
		List<BrandProperty> brands = brandRepository.findAll()
				 .stream()
				 .map(p -> new BrandProperty(p))
				 .toList();

		mTable.getItems().addAll(brands);
	}
	
	public void clear() {
		mTable.getItems().clear();
	}
		

}
