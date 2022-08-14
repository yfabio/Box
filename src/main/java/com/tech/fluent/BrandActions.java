package com.tech.fluent;

import org.controlsfx.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.brand.BrandFormController;
import com.tech.brand.BrandTableController;
import com.tech.model.Brand;
import com.tech.properties.BrandProperty;
import com.tech.repository.BrandRepository;

@Component
public class BrandActions extends Action<BrandProperty> {

	public BrandProperty property = new BrandProperty();
	
	private BrandProperty older;
	
	private BrandProperty newer;

	private Process process;

	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private BrandFormController brandFormController;
	
	@Autowired
	private BrandTableController brandTableController;
			
	private Brand brand;
	
	@Override
	public Crud validate() {		
		validation.registerValidator(brandFormController.mTitle, 
				Validator.createEmptyValidator("Title is required"));		
		return this;
	}

	@Override
	public Crud delete() {
		process = Process.DELETE;		
		String title = newer.getTitle(); 
		brand = brandRepository.findByTitle(title);
		return this;
	}

	@Override
	public Crud update() {
		if(newer!=null) {
			process = Process.UPDATE;
			brand = brandRepository.findById(newer.getId()).orElse(null);
			if(brand!=null) {
				brand.setTitle(property.getTitle());
			}			
		}		
		return this;
	}

	@Override
	public Crud create() {
		process = Process.CREATE;
		brand = new Brand();
		brand.setTitle(property.getTitle());
		return this;
	}
	
	@Override
	public Crud unBindSelected() {
		unregister(newer);
		return this;
	}
	
	@Override
	public Crud reset() {
		brandTableController.resetSelection();
		brandFormController.requestFocus();
		brandFormController.clear();
		return this;
	}

	
	@Override
	public Crud unbind() {
		detach();
		return this;
	}

	@Override
	public Crud commit() {
		switch (process) {
			case DELETE:
				if(brand!=null) {
					brandRepository.delete(brand);	
					brandTableController.mTable.getItems().remove(newer);
					notifyAction("Delete","delete successfully!");
				}					
				break;		
			case UPDATE:
				if(brand!=null && isValid) {
					validation.setErrorDecorationEnabled(true);
					brandRepository.save(brand);
					BrandProperty brandProperty = new BrandProperty(brand);
					int index = brandTableController.mTable.getSelectionModel().getSelectedIndex();
					brandTableController.mTable.getItems().set(index, brandProperty);
					notifyAction("Update","update successfully!");
				}else {
					validation.setErrorDecorationEnabled(true);
				}
				break;
			case CREATE:
				if(isValid) {
					brandRepository.save(brand);
					validation.setErrorDecorationEnabled(false);
					BrandProperty brandProperty = new BrandProperty(brand);
					brandTableController.mTable.getItems().add(brandProperty);
					notifyAction("Create","create successfully!");
				}else {
					validation.setErrorDecorationEnabled(true);
				}				
				break;
		}	
		return this;
	}
	
	@Override
	public void register(BrandProperty newer) {
		this.newer = newer;
		attach();
	}

	@Override
	public void unregister(BrandProperty older) {
		this.older = older;
		detach();
	}

	private void attach() {
		if (newer != null)
			property.titleProperty().bindBidirectional(newer.titleProperty());
	}

	private void detach() {
		if (older != null)
			property.titleProperty().unbindBidirectional(older.titleProperty());
	}

	

	
}
