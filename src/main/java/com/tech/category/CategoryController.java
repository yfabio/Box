package com.tech.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.fluent.CategoryActions;
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
@FxmlView("/xml/category-view.fxml")
public class CategoryController extends ControllerBase {

	@FXML
	private CategoryFormController categoryFormController;
	
	@FXML
	private CategoryActionController categoryActionController;
	
	@Autowired
	private CategoryTableController categoryTableController;
	
	@Autowired
	private ImageService imageService;
	
	private SimpleBooleanProperty isEdit = new SimpleBooleanProperty();	
	
	@Autowired
	private CategoryActions crud;	
		
	
	@FXML
	public void initialize() {
		
		var edit = imageService.image(Icons.EDIT);
		var save = imageService.image(Icons.SAVE);

		SimpleObjectProperty<Image> editImg = new SimpleObjectProperty<Image>(new Image(edit));
		SimpleObjectProperty<Image> saveImg = new SimpleObjectProperty<Image>(new Image(save));
				
		categoryActionController.mDelete.disableProperty().bind(isEdit.not());
		
		categoryActionController.mCancel.disableProperty().bind(categoryTableController.mTable.getSelectionModel().selectedItemProperty().isNull());
				
		categoryTableController.mTable.getSelectionModel().selectedItemProperty().addListener((obs,older,newer) -> {
			
			isEdit.set(categoryTableController.mTable.getSelectionModel().selectedItemProperty().isNotNull().get());
			
			categoryActionController.mUpsertImg.imageProperty().bind(Bindings.when(isEdit).then(editImg).otherwise(saveImg));
			
			categoryActionController.mUpsert.textProperty().bind(Bindings.when(isEdit).then(new SimpleStringProperty("edit"))
					 .otherwise(new SimpleStringProperty("save")));				
			
			crud.unregister(older);			
			crud.register(newer);
									
		});
		
		categoryFormController.mTitle.textProperty()
						.bindBidirectional(crud.property.titleProperty());
				
		categoryActionController.mUpsert.setOnAction((event) -> onUpsert());
		categoryActionController.mCancel.setOnAction((event) -> onCancel());
		categoryActionController.mDelete.setOnAction((event) -> onDelete());
		
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
