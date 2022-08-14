package com.tech.properties;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.tech.model.Product;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class ProductProperty  {
	
	private LongProperty id = new SimpleLongProperty();

	private StringProperty name = new SimpleStringProperty();

	private StringProperty color = new SimpleStringProperty();

	private StringProperty size = new SimpleStringProperty();

	private StringProperty brand = new SimpleStringProperty();

	private StringProperty state = new SimpleStringProperty();

	private StringProperty qty = new SimpleStringProperty();

	private StringProperty weight = new SimpleStringProperty();

	private SimpleBooleanProperty packed = new SimpleBooleanProperty();

	private StringProperty detail = new SimpleStringProperty();

	private ObjectProperty<Image> image = new SimpleObjectProperty<Image>();

	private ObjectProperty<Path> imgPath = new SimpleObjectProperty<Path>();

	private StringProperty category = new SimpleStringProperty();
	
	public ProductProperty() {
	}

	public ProductProperty(Product product) {

		setId(product.getId());
		setName(product.getName());
		setColor(product.getColor());
		setSize(product.getSize());
		setBrand(product.getBrand() != null ? product.getBrand().getTitle() : "");
		setState(product.getState());
		setQty(product.getQty());
		setWeight(product.getWeight());
		setDetail(product.getDetail());
		setPacked(product.getPacked());
		setCategory(product.getCategory() != null ? product.getCategory().getTitle() : "");
		setImgPath(Paths.get(product.getImgPath()));
		setImagePath();

		// setImage(product.getImage());
		// setImage(product.getImgWidth(), product.getImgHeight(), product.getImage());
	}
	
	
	public LongProperty idProperty() {
		return this.id;
	}

	public long getId() {
		return this.idProperty().get();
	}

	public void setId(long id) {
		this.idProperty().set(id);
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	public String getName() {
		return this.nameProperty().get();
	}

	public void setName(final String name) {
		this.nameProperty().set(name);
	}

	public StringProperty colorProperty() {
		return this.color;
	}

	public String getColor() {
		return this.colorProperty().get();
	}

	public void setColor(String color) {
		this.colorProperty().set(color);
	}

	public StringProperty sizeProperty() {
		return this.size;
	}

	public String getSize() {
		return this.sizeProperty().get();
	}

	public void setSize(final String size) {
		this.sizeProperty().set(size);
	}

	public StringProperty brandProperty() {
		return this.brand;
	}

	public String getBrand() {
		return this.brandProperty().get();
	}

	public void setBrand(final String brand) {
		this.brandProperty().set(brand);
	}

	public StringProperty stateProperty() {
		return this.state;
	}

	public String getState() {
		return this.stateProperty().get();
	}

	public void setState(String state) {
		this.stateProperty().set(state);
	}

	public StringProperty qtyProperty() {
		return this.qty;
	}

	public String getQty() {
		return this.qtyProperty().get();
	}

	public void setQty(Integer qty) {
		this.qtyProperty().set(String.valueOf(qty));
	}

	public StringProperty weightProperty() {
		return this.weight;
	}

	public String getWeight() {
		return this.weightProperty().get();
	}

	public void setWeight(Double weight) {
		this.weightProperty().set(String.valueOf(weight));
	}

	public SimpleBooleanProperty packedProperty() {
		return this.packed;
	}

	public boolean isPacked() {
		return this.packedProperty().get();
	}

	public void setPacked(final boolean packed) {
		this.packedProperty().set(packed);
	}

	public StringProperty detailProperty() {
		return this.detail;
	}

	public String getDetail() {
		return this.detailProperty().get();
	}

	public void setDetail(final String detail) {
		this.detailProperty().set(detail);
	}

	public ObjectProperty<Image> imageProperty() {
		return this.image;
	}

	public Image getImage() {
		return this.imageProperty().get();
	}

	public void setImage(Image image) {
		this.imageProperty().set(image);
	}

	public ObjectProperty<Path> imgPathProperty() {
		return this.imgPath;
	}

	public Path getImgPath() {
		return this.imgPathProperty().get();
	}

	public void setImgPath(Path imgPath) {
		this.imgPathProperty().set(imgPath);
	}

	public StringProperty categoryProperty() {
		return this.category;
	}

	public String getCategory() {
		return this.categoryProperty().get();
	}

	public void setCategory(String category) {
		this.categoryProperty().set(category);
	}

	private void setImagePath() {
		ExecutorService ex = Executors.newSingleThreadExecutor();
		try {
			URL url = getImgPath().toUri().toURL();
			Future<Image> future = ex.submit(() -> new Image(url.toString(),500,500,false,false,true));			
			setImage(future.get());
		} catch (InterruptedException | ExecutionException | MalformedURLException e) {
			throw new RuntimeException(e);
		}finally {
			ex.shutdown();
		}
	}

	
	
	
	

//	private Image writeImage() {
//		int width = (int) property.getImage().getWidth();
//		int height = (int) property.getImage().getHeight();
//		WritableImage writableImage = new WritableImage(width, height);
//		writableImage.getPixelWriter().setPixels(0, 0, width, height,
//		        PixelFormat.getByteBgraInstance(),
//		        readImage(), 0, width * 4);
//		//ImageView view3 = new ImageView(writableImage);
//		return writeImage();
//	}

//	private void setImage(int imgWidth,int imgHeight,byte[] pixelBytes) {	
//		WritableImage writableImage = new WritableImage(imgWidth, imgHeight);
//		writableImage.getPixelWriter().setPixels(0, 0, imgWidth, imgHeight,
//		        PixelFormat.getByteBgraInstance(),
//		        pixelBytes, 0, imgWidth * 4);
//		this.imageProperty().set(writableImage);			
//	}
//	

}
