package com.tech.fluent;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.model.Brand;
import com.tech.properties.ProductProperty;
import com.tech.repository.BrandRepository;
import com.tech.repository.ProductRepository;

@Component
public class ProducFilter implements Filter {

	private List<ProductProperty> products = new ArrayList<>();

	@Autowired
	private ProductRepository prodRepository;
	
	@Autowired
	private BrandRepository brandRepository;

	@Override
	public Filter filter(String value, By which) {
		switch (which) {
			case NAME:
				products = prodRepository.findByName(value).stream().map(p -> new ProductProperty(p)).toList();
				break;
			case BRAND:
				Brand brand = brandRepository.findByTitle(value);
				products = prodRepository.findByBrand(brand).stream().map(p -> new ProductProperty(p)).toList();
				break;			
		}
		return this;
	}

	@Override
	public Filter reload(FilterResult result) {
		if (products != null)			
			result.load(products);
		return this;
	}

}
