package com.tech.service;

import org.springframework.stereotype.Service;

import com.tech.properties.ProductProperty;

import javafx.collections.ObservableList;

@Service
public interface LoadProductService {
	public ObservableList<ProductProperty> load();	
}
