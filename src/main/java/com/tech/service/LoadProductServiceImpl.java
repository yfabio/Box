package com.tech.service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.properties.ProductProperty;
import com.tech.repository.ProductRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component
public class LoadProductServiceImpl implements LoadProductService {
		
	@Autowired
	private ProductRepository productRepository;
		
	@Override
	public ObservableList<ProductProperty> load() {		
		ExecutorService ex = Executors.newSingleThreadExecutor();
		try {
			Load load = new Load();
			Future<List<ProductProperty>> future = ex.submit(load);
			return FXCollections.observableArrayList(future.get());
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException();
		}finally {
			ex.shutdown();
		}
	}
	
	
	private class Load implements Callable<List<ProductProperty>>{
		@Override
		public List<ProductProperty> call() throws Exception {
			return productRepository.findAll().stream().map(p -> new ProductProperty(p)).toList();
		}
		
	}
	
	
	
	
	
	

}
