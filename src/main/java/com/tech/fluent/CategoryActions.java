package com.tech.fluent;

import org.controlsfx.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.category.CategoryFormController;
import com.tech.category.CategoryTableController;
import com.tech.model.Category;
import com.tech.properties.CategoryProperty;
import com.tech.repository.CategoryRepository;

@Component
public class CategoryActions extends Action<CategoryProperty> {

	public CategoryProperty property = new CategoryProperty();

	private CategoryProperty older;

	private CategoryProperty newer;

	private Process process;

	@Autowired
	private CategoryRepository cateRepository;

	@Autowired
	private CategoryFormController categoryFormController;

	@Autowired
	private CategoryTableController categoryTableController;

	private Category cat;

	@Override
	public Crud validate() {
		validation.registerValidator(categoryFormController.mTitle,
				Validator.createEmptyValidator("Title is required"));
		return this;
	}

	@Override
	public Crud delete() {
		process = Process.DELETE;
		String title = newer.getTitle();
		cat = cateRepository.findByTitle(title);
		return this;
	}

	@Override
	public Crud update() {
		if (newer != null) {
			process = Process.UPDATE;
			cat = cateRepository.findById(newer.getId()).orElse(null);
			if (cat != null) {
				cat.setTitle(property.getTitle());
			}
		}
		return this;
	}

	@Override
	public Crud create() {
		process = Process.CREATE;
		cat = new Category();
		cat.setTitle(property.getTitle());
		return this;
	}

	@Override
	public Crud unBindSelected() {
		unregister(newer);
		return this;
	}
	
	@Override
	public Crud reset() {
		categoryTableController.resetSelection();
		categoryFormController.requestFocus();
		categoryFormController.clear();
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
			if (cat != null) {
				cateRepository.delete(cat);
				categoryTableController.mTable.getItems().remove(newer);
				notifyAction("Delete","delete successfully!");
			}
			break;
		case UPDATE:
			if (cat != null && isValid) {
				validation.setErrorDecorationEnabled(false);
				cateRepository.save(cat);
				CategoryProperty categoryProperty = new CategoryProperty(cat);
				int index = categoryTableController.mTable.getSelectionModel().getSelectedIndex();
				categoryTableController.mTable.getItems().set(index, categoryProperty);
				notifyAction("Update","update successfully!");
			} else {
				validation.setErrorDecorationEnabled(true);
			}
			break;
		case CREATE:
			if (isValid) {
				validation.setErrorDecorationEnabled(false);
				cateRepository.save(cat);
				CategoryProperty categoryProperty = new CategoryProperty(cat);
				categoryTableController.mTable.getItems().add(categoryProperty);
				notifyAction("Create","create successfully!");
			} else {
				validation.setErrorDecorationEnabled(true);
			}
			break;
		}
		return this;
	}

	
	@Override
	public void register(CategoryProperty newer) {
		this.newer = newer;
		attach();
	}

	@Override
	public void unregister(CategoryProperty older) {
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
