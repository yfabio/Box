package com.tech.product;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.properties.ProductProperty;
import com.tech.repository.DatabaseInfo;
import com.tech.repository.Observer;
import com.tech.repository.ProductRepository;
import com.tech.repository.Subject;
import com.tech.service.LoadProductService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Component
public class ProductTableController implements Observer {

	@FXML
	public TableView<ProductProperty> mTable;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private LoadProductService loadProductService;

	private ObservableList<ProductProperty> products = FXCollections.observableArrayList();

	private boolean tough;

	private DatabaseInfo databaseInfo = DatabaseInfo.getInstance();

	@PostConstruct
	public void posConstruct() {
		databaseInfo.register(this);
	}

	@PreDestroy
	public void preDestroy() {
		databaseInfo.unregister(this);
	}

	@FXML
	public void initialize() {
		if (products.isEmpty() || products.size() != productRepository.count() || !tough) {
			products.setAll(loadProductService.load());
			tough = true;
		}
		mTable.setItems(products);
	}

	public void resetSelection() {
		mTable.getSelectionModel().clearSelection();
	}

	public void reload() {
		mTable.setItems(products);
	}

	public void clear() {
		mTable.getItems().clear();
	}

	@Override
	public void update(Subject subject) {
		DatabaseInfo databaseInfo = (DatabaseInfo) subject;
		this.tough = databaseInfo.isTough();
	}

}
