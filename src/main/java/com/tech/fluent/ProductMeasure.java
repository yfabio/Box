package com.tech.fluent;

import java.util.DoubleSummaryStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.model.Brand;
import com.tech.repository.BrandRepository;
import com.tech.repository.ProductRepository;

@Component
public class ProductMeasure implements Measure {

	
	@Autowired
	private ProductRepository prodRepository;
	
	@Autowired
	private BrandRepository brandRepository;

	private DoubleSummaryStatistics statistics;
	
	@Override
	public Measure scale(String value, By which) {
		switch (which) {
			case NAME:
				statistics = prodRepository.findByName(value)
				 				.stream()
				 				.mapToDouble(p -> p.getWeight())
				 				.summaryStatistics();				 				 				 				
				break;
			case BRAND:
				Brand brand = brandRepository.findByTitle(value);
				statistics = prodRepository.findByBrand(brand)
							 				.stream()
							 				.mapToDouble(p -> p.getWeight())
							 				.summaryStatistics();	
				break;			
		}		
		return this;
	}

	@Override
	public Measure reload(MeasureResult result) {
		result.set(new Summary(statistics.getCount(), statistics.getSum()));
		return this;
	}

	

	
}
