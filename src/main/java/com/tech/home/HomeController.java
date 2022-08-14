package com.tech.home;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.fluent.By;
import com.tech.fluent.Filter;
import com.tech.fluent.Measure;
import com.tech.fluent.Summary;
import com.tech.fx.ControllerBase;
import com.tech.fx.CustomListCell;
import com.tech.fx.NoSelectionModel;
import com.tech.properties.ProductProperty;
import com.tech.repository.DatabaseInfo;
import com.tech.repository.Observer;
import com.tech.repository.ProductRepository;
import com.tech.repository.Subject;
import com.tech.service.LoadProductService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("/xml/home-view.fxml")
public class HomeController extends ControllerBase implements Observer  {
	
	@FXML 
	private ListView<ProductProperty> cardView;
	
	@Autowired
	private HomeControllerLeftSide homeLeftSide;
	
	@Autowired
	private HomeControllerRightSide homeRightSide;
	
	@Autowired
	private ProductRepository prodRepository;

	@Autowired
	private Filter filter;
	
	@Autowired
	private Measure measure;
	
	@Autowired
	private LoadProductService loadProductService;
	
	private By which;
		
	private ObservableList<ProductProperty> products = FXCollections.observableArrayList();
	
	private boolean toughBase;
	
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
			
		 var names = prodRepository.findAll()
				 				.stream().map(e -> new String(e.getName()))
				 					  .distinct()
				 					  .toList();
		
		 var brands = prodRepository.findAll()
				 					.stream()
				 					.map(e -> new String(e.getBrand().getTitle()))
				 					.distinct()
				 					.toList();
		
		 
		
		cardView.setCellFactory(new CustomListCell());
		cardView.setSelectionModel(new NoSelectionModel<ProductProperty>());
						
		homeLeftSide.mNames.getItems().addAll(names);
		homeLeftSide.mBrands.getItems().addAll(brands);		
		homeRightSide.mBrands.getItems().addAll(brands);
		homeRightSide.mNames.getItems().addAll(names);
				
		
		homeLeftSide.mReload.setOnAction(e -> onReload());
		homeLeftSide.mSearch.setOnAction(e -> onSearch());		
		homeRightSide.mScale.setOnAction(e -> onScale());
		homeRightSide.mClean.setOnAction(e -> onClean());
		
		
		homeLeftSide.mNames.getSelectionModel().selectedItemProperty().addListener((obs,o,n)->{
			which = By.NAME;
		});
		
		homeLeftSide.mBrands.getSelectionModel().selectedItemProperty().addListener((obs,o,n)->{
			which = By.BRAND;
		});
				
		homeRightSide.mNames.getSelectionModel().selectedItemProperty().addListener((obs,o,n)->{
			which = By.NAME;
		});
		
		homeRightSide.mBrands.getSelectionModel().selectedItemProperty().addListener((obs,o,n)->{
			which = By.BRAND;
		});
				
				

		if(products.isEmpty() || products.size() != prodRepository.count() || !toughBase) {			
			products.setAll(loadProductService.load());
			toughBase = true;
		}
		
		cardView.getItems().setAll(products);
		
	}

	
	private void onClean() {
		Stream.of(homeRightSide.mTotal,homeRightSide.mWeight).forEach(e -> e.setText(""));
	}

	private void onScale() {
		if(which!=null) {
			switch (which) {
				case NAME:
						measure.scale(homeRightSide.mNames.getSelectionModel().getSelectedItem(), which)
						.reload(result -> summary(result));
					break;	
				case BRAND:
					measure.scale(homeRightSide.mBrands.getSelectionModel().getSelectedItem(), which)
					.reload(result -> summary(result));
					break;				
			}
		}
	}

	private void summary(Summary result) {
		homeRightSide.mTotal.setText(String.valueOf(result.total));
		homeRightSide.mWeight.setText(String.format("%.03f",result.weightSum));
	}

	private void onSearch() {	
		if(which!=null) {
			cardView.getItems().clear();
			switch (which) {
				case NAME:
						filter.filter(homeLeftSide.mNames.getSelectionModel().getSelectedItem(),which)
						.reload(result -> reload(result));
					break;	
				case BRAND:					
						filter.filter(homeLeftSide.mBrands.getSelectionModel().getSelectedItem(),which)
						.reload(result -> reload(result));	
					break;				
			}
		}
	}

	private void onReload() {		
		cardView.getItems().setAll(products);
	}
	
	private void reload(List<ProductProperty> load) {
		cardView.getItems().clear();
		cardView.getItems().setAll(load);
	}


	@Override
	public void update(Subject subject) {
		DatabaseInfo databaseInfo = (DatabaseInfo) subject;
		this.toughBase = databaseInfo.isTough();	
	}


	

	
	
	
	
	
	
	
	
	
	
	
	
}
