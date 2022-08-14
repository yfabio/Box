package com.tech.product;


import java.io.File;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.fluent.ProductActions;
import com.tech.fx.ControllerBase;
import com.tech.repository.BrandRepository;
import com.tech.repository.CategoryRepository;
import com.tech.service.ImageService;
import com.tech.ui.IOUtils;
import com.tech.ui.Icons;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxmlView;


@Component
@FxmlView("/xml/product-view.fxml")
public class ProductController extends ControllerBase  {

	@Autowired
	private ProductFormController productFormController;

	@FXML
	private ProductActionController productActionController;

	@Autowired
	private ProductTableController productTableController;
	
	@Autowired
	private ProductImageController productImageController;
	
	@Autowired
	private CategoryRepository catRepository;
	
	@Autowired
	private BrandRepository brandRepository;
				
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ProductActions crud;
	
	private SimpleBooleanProperty isEdit = new SimpleBooleanProperty();
	
	private SimpleObjectProperty<Path> imgPath = new SimpleObjectProperty<Path>();	
	
	@FXML
	public void initialize() {

		var edit = imageService.image(Icons.EDIT);
		var save = imageService.image(Icons.SAVE);
		
		SimpleObjectProperty<Image> editImg = new SimpleObjectProperty<Image>(new Image(edit));
		SimpleObjectProperty<Image> saveImg = new SimpleObjectProperty<Image>(new Image(save));
						
		productActionController.mDelete.disableProperty().bind(isEdit.not());
		
		productActionController.mCancel.disableProperty().bind(productTableController.mTable.getSelectionModel().selectedItemProperty().isNull());
				
		productTableController.mTable.getSelectionModel().selectedItemProperty().addListener((obs, older, newer) -> {
			
			isEdit.set(productTableController.mTable.getSelectionModel().selectedItemProperty().isNotNull().get());
			
			productActionController.mUpsertImg.imageProperty().bind(Bindings.when(isEdit).then(editImg).otherwise(saveImg));		
						
			productActionController.mUpsert.textProperty().bind(Bindings.when(isEdit).then(new SimpleStringProperty("edit"))
					 .otherwise(new SimpleStringProperty("save")));
						
			crud.unregister(older);
			crud.register(newer);
			
		});
				
		productActionController.mUpsert.setOnAction((event) -> onUpsert());
		productActionController.mCancel.setOnAction((event) -> onCancel());
		productActionController.mDelete.setOnAction((event) -> onDelete());
		productImageController.mBrowser.setOnAction((event) -> onBrowser(event));
				
		productFormController.mName.textProperty().bindBidirectional(crud.property.nameProperty());	
		productFormController.mColor.textProperty().bindBidirectional(crud.property.colorProperty());
		productFormController.mSize.textProperty().bindBidirectional(crud.property.sizeProperty());		
		productFormController.mBrand.valueProperty().bindBidirectional(crud.property.brandProperty());		
		productFormController.mState.valueProperty().bindBidirectional(crud.property.stateProperty());
	    productFormController.mQty.textProperty().bindBidirectional(crud.property.qtyProperty());
	    productFormController.mWeight.textProperty().bindBidirectional(crud.property.weightProperty());
		productFormController.mPacked.selectedProperty().bindBidirectional(crud.property.packedProperty());
	    productFormController.mDetail.textProperty().bindBidirectional(crud.property.detailProperty());
	    productFormController.mCategory.valueProperty().bindBidirectional(crud.property.categoryProperty());
	    productImageController.mImage.imageProperty().bindBidirectional(crud.property.imageProperty());
	  
	    imgPath.bindBidirectional(crud.property.imgPathProperty());
	    
	    List<String> titles = catRepository.findAll().stream().map(p -> p.getTitle()).toList();
	    List<String> brands = brandRepository.findAll().stream().map(p -> p.getTitle()).toList();
	    
		productFormController.mCategory.getItems().addAll(titles);
		productFormController.mBrand.getItems().addAll(brands);
	    
		crud.validate();
		
			    
	}

	private void onBrowser(ActionEvent event) {
		Window window = ((Button)event.getSource()).getScene().getWindow();
		File file = IOUtils.openFileDialog(window);
		if(file!=null) {
			imgPath.set(file.toPath());			
			productImageController.mImage.setImage(imageService.loadImage(file));			
		}
	}


	private void onDelete() {
		crud.delete()
			.commit()
			.reset();		
	}

	private void onCancel() {
		isEdit.set(false);				
		crud.unBindSelected()
			.reset();		
	}


	private void onUpsert() {
		if(isEdit.get()) {
			crud.update()				
				.unBindSelected()				
				.unbind()
				.commit()				
				.reset();
				
		}else {
			crud.create()
				.unbind()
				.commit()
				.reset();			
		}		
	}



}
