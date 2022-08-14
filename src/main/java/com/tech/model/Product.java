package com.tech.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(length = 40, nullable = false)
	private String name;

	@Column(length = 40, nullable = false)
	private String color;

	@Column(length = 40, nullable = false)
	private String size;

	@Column(length = 40, nullable = false)
	private String state;

	@Column(nullable = false)
	private Integer qty;

	@Column(nullable = false)
	private Double weight;

	@Column(nullable = true)
	private Boolean packed;

	@Column(length = 250, nullable = true)
	private String detail;

	// @Lob
	// @Column(nullable = false)
	// private byte[] image;

	// @Column(nullable = false)
	// private Integer imgWidth;

	// @Column(nullable = false)
	// private Integer imgHeight;

	@Column(length = 255, nullable = true)
	private String imgPath;

	@ManyToOne()
	@JoinColumn(name = "category_id")
	private Category category;
	
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Brand getBrand() {
		return brand;
	}
	
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Boolean getPacked() {
		return packed;
	}

	public void setPacked(Boolean packed) {
		this.packed = packed;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public String getImgPath() {
		return imgPath;
	}


	public void setCategory(Category category) {
		this.category = category;
	}
	

	public Category getCategory() {
		return category;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	public byte[] getImage() {
//		return image;
//	}
//
//	public void setImage(byte[] image) {
//		this.image = image;
//	}
//
//	public Integer getImgWidth() {
//		return imgWidth;
//	}
//
//	public void setImgWidth(Integer imgWidth) {
//		this.imgWidth = imgWidth;
//	}
//
//	public Integer getImgHeight() {
//		return imgHeight;
//	}
//
//	public void setImgHeight(Integer imgHeight) {
//		this.imgHeight = imgHeight;
//	}
//
//	public String getImg() {
//		return img;
//	}
//
//	public void setImg(String img) {
//		this.img = img;
//	}
	
	
	

	
	

}
