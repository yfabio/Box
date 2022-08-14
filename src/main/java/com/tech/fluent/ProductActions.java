package com.tech.fluent;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.controlsfx.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.model.Brand;
import com.tech.model.Category;
import com.tech.model.Product;
import com.tech.product.ProductFormController;
import com.tech.product.ProductImageController;
import com.tech.product.ProductTableController;
import com.tech.properties.ProductProperty;
import com.tech.repository.BrandRepository;
import com.tech.repository.CategoryRepository;
import com.tech.repository.DatabaseInfo;
import com.tech.repository.ProductRepository;
import com.tech.service.ImageService;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

@Component
public class ProductActions extends Action<ProductProperty> {

	public ProductProperty property = new ProductProperty();

	private ProductProperty older;

	private ProductProperty newer;

	private Process process;

	@Autowired
	private ProductRepository proRepository;

	@Autowired
	private CategoryRepository catRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private ProductTableController productTableController;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ProductFormController proFormController;

	@Autowired
	private ProductImageController productImageController;

	private Product product;
	
	private DatabaseInfo databaseInfo = DatabaseInfo.getInstance();
	
	@Override
	public Crud validate() {
		
		Validator<TextField> isNameValid = Validator.createEmptyValidator("Name is required!");
		Validator<TextField> isBrandValid = Validator.createEmptyValidator("Brand is required!");
		Validator<TextField> isQtyValid = Validator.createEmptyValidator("Qty is required!");
		Validator<TextField> isSizeValid = Validator.createEmptyValidator("Size is required!");
		Validator<TextField> isWeighValid = Validator.createEmptyValidator("Weight is required!");
		Validator<TextField> isColorValid = Validator.createEmptyValidator("Color is required!");

		Validator<ComboBox<String>> isStateValid = Validator.createEmptyValidator("State is required!");
		Validator<ComboBox<String>> isCategoryValid = Validator.createEmptyValidator("Category is required!");

		validation.registerValidator(proFormController.mName, isNameValid);
		validation.registerValidator(proFormController.mBrand, isBrandValid);
		validation.registerValidator(proFormController.mQty, isQtyValid);
		validation.registerValidator(proFormController.mSize, isSizeValid);
		validation.registerValidator(proFormController.mWeight, isWeighValid);
		validation.registerValidator(proFormController.mColor, isColorValid);
		validation.registerValidator(proFormController.mState, isStateValid);
		validation.registerValidator(proFormController.mCategory, isCategoryValid);
		
		return this;
	}

	@Override
	public Crud delete() {
		process = Process.DELETE;
		product = proRepository.findById(newer.getId()).orElse(null);
		validation.setErrorDecorationEnabled(false);
		return this;
	}

	@Override
	public Crud update() {
		if (newer != null) {
			process = Process.UPDATE;
			product = proRepository.findById(newer.getId()).orElse(null);
			if (product != null) {
				Path source = property.getImgPath();				
				if (source != null) {
					Path target = Paths.get(product.getImgPath());
					Path dir = imageService.imageStore(source, target);
					product.setImgPath(dir.toString());
				}
				updateValues(product);
			}
		}
		return this;
	}

	@Override
	public Crud create() {
		process = Process.CREATE;
		product = new Product();
		updateValues(product);
		return this;
	}

	@Override
	public Crud commit() {
		switch (process) {
		case DELETE:
			if (product != null) {
				String imgPath = product.getImgPath();
				imageService.deleteImage(Paths.get(imgPath));
				proRepository.delete(product);
				productTableController.mTable.getItems().remove(newer);
				notifyAction("Delete","delete successfully!");
			}
			break;
		case UPDATE:
			if (product != null && isValid) {
				validation.setErrorDecorationEnabled(false);
				proRepository.save(product);
				int index = productTableController.mTable.getSelectionModel().getSelectedIndex();
				ProductProperty productProperty = new ProductProperty(product);
				productTableController.mTable.getItems().set(index, productProperty);
				notifyAction("Update","update successfully!");
			} else {
				validation.setErrorDecorationEnabled(true);
			}
			break;
		case CREATE:
			if(property.getImgPath()==null) {
				notifyErrorImage();					   
			}else {
				if (isValid) {
					validation.setErrorDecorationEnabled(false);
					product = proRepository.save(product);
					Path source = property.getImgPath();
					Path target = imageService.getImagePath().resolve(imageService.getImageName(product.getId()));
					Path dir = imageService.imageStore(source, target);
					product.setImgPath(dir.toString());
					proRepository.save(product);
					ProductProperty productProperty = new ProductProperty(product);
					productTableController.mTable.getItems().add(productProperty);
					notifyAction("Create","create successfully!");
				} else {
					validation.setErrorDecorationEnabled(true);
				}
			}
			break;
		}
		databaseInfo.setTough(false);
		return this;
	}

	@Override
	public Crud reset() {
		proFormController.requestFocus();
		productImageController.resetImage();
		productTableController.resetSelection();
		proFormController.clear();
		property.setImage(null);
		property.setImgPath(null);
		return this;
	}

	@Override
	public Crud unBindSelected() {
		unregister(newer);
		return this;
	}

	@Override
	public Crud unbind() {
		detach();
		return this;
	}

	@Override
	public void register(ProductProperty newer) {
		this.newer = newer;
		attach();
	}

	@Override
	public void unregister(ProductProperty older) {
		this.older = older;
		detach();
	}

	private Category getCategory(String title) {
		return catRepository.findByTitle(title);
	}

	private Brand getBrand(String title) {
		return brandRepository.findByTitle(title);
	}

	private void updateValues(Product obj) {
		obj.setName(property.getName());
		obj.setColor(property.getColor());
		obj.setSize(property.getSize());
		obj.setBrand(getBrand(property.getBrand()));
		obj.setState(property.getState());

		obj.setQty(property.getQty() == null || property.getQty().isBlank()  ? null : Integer.valueOf(property.getQty()));
		obj.setWeight(property.getWeight() == null || property.getWeight().isBlank() ? null : Double.valueOf(property.getWeight()));

		obj.setPacked(property.isPacked());
		obj.setDetail(property.getDetail());
		obj.setCategory(getCategory(property.getCategory()));

	}

	private void attach() {
		if (newer != null) {
			property.nameProperty().bindBidirectional(newer.nameProperty());
			property.colorProperty().bindBidirectional(newer.colorProperty());
			property.sizeProperty().bindBidirectional(newer.sizeProperty());
			property.brandProperty().bindBidirectional(newer.brandProperty());
			property.categoryProperty().bindBidirectional(newer.categoryProperty());
			property.stateProperty().bindBidirectional(newer.stateProperty());
			property.qtyProperty().bindBidirectional(newer.qtyProperty());
			property.weightProperty().bindBidirectional(newer.weightProperty());
			property.packedProperty().bindBidirectional(newer.packedProperty());
			property.detailProperty().bindBidirectional(newer.detailProperty());
			property.imageProperty().bindBidirectional(newer.imageProperty());
		}
	}

	private void detach() {
		if (older != null) {
			property.nameProperty().unbindBidirectional(older.nameProperty());
			property.colorProperty().unbindBidirectional(older.colorProperty());
			property.sizeProperty().unbindBidirectional(older.sizeProperty());
			property.brandProperty().unbindBidirectional(older.brandProperty());
			property.categoryProperty().unbindBidirectional(older.categoryProperty());
			property.stateProperty().unbindBidirectional(older.stateProperty());
			property.qtyProperty().unbindBidirectional(older.qtyProperty());
			property.weightProperty().unbindBidirectional(older.weightProperty());
			property.packedProperty().unbindBidirectional(older.packedProperty());
			property.detailProperty().unbindBidirectional(older.detailProperty());
			property.imageProperty().unbindBidirectional(older.imageProperty());
		}
	}
	
	
	

//	private byte[] readImage() {
//	int width = getImgWidth();
//	int height = getImgHeight();
//	byte[] pixelBytes = new byte[width * height * 4];		  
//	property.getImage().getPixelReader().getPixels(0, 0, width, height,
//	        PixelFormat.getByteBgraInstance(),
//	        pixelBytes, 0, width * 4);		
//	return pixelBytes;
//}
//	
//private int getImgWidth() {
//	return (int) property.getImage().getWidth();
//}
//	
//private int getImgHeight() {
//	return (int) property.getImage().getHeight();
//}

}
