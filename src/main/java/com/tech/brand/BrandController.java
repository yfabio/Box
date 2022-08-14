package com.tech.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.fluent.BrandActions;
import com.tech.fx.ControllerBase;
import com.tech.service.ImageService;
import com.tech.ui.Icons;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("/xml/brand-view.fxml")
public class BrandController extends ControllerBase {

	@FXML
	private BrandFormController brandFormController;
	
	@FXML
	private BrandActionController brandActionController;
	
	@Autowired
	private BrandTableController brandTableController;
	
	@Autowired
	private ImageService imageService;
	
	private SimpleBooleanProperty isEdit = new SimpleBooleanProperty();	
	
	@Autowired
	private BrandActions crud;	
		
	
	@FXML
	public void initialize() {
		
		var edit = imageService.image(Icons.EDIT);
		var save = imageService.image(Icons.SAVE);

		SimpleObjectProperty<Image> editImg = new SimpleObjectProperty<Image>(new Image(edit));
		SimpleObjectProperty<Image> saveImg = new SimpleObjectProperty<Image>(new Image(save));
				
		brandActionController.mDelete.disableProperty().bind(isEdit.not());
		
		brandActionController.mCancel.disableProperty().bind(brandTableController.mTable.getSelectionModel().selectedItemProperty().isNull());
				
		brandTableController.mTable.getSelectionModel().selectedItemProperty().addListener((obs,older,newer) -> {
			
			isEdit.set(brandTableController.mTable.getSelectionModel().selectedItemProperty().isNotNull().get());
			
			brandActionController.mUpsertImg.imageProperty().bind(Bindings.when(isEdit).then(editImg).otherwise(saveImg));
			
			brandActionController.mUpsert.textProperty().bind(Bindings.when(isEdit).then(new SimpleStringProperty("edit"))
					 .otherwise(new SimpleStringProperty("save")));				
			
			crud.unregister(older);			
			crud.register(newer);
									
		});
		
		brandFormController.mTitle.textProperty()
						.bindBidirectional(crud.property.titleProperty());
				
		brandActionController.mUpsert.setOnAction((event) -> onUpsert());
		brandActionController.mCancel.setOnAction((event) -> onCancel());
		brandActionController.mDelete.setOnAction((event) -> onDelete());
		
		crud.validate();
			
	}


	private void onDelete() {		
		crud.delete()
			.unbind()
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
