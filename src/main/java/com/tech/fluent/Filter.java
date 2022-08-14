package com.tech.fluent;

import org.springframework.stereotype.Service;

@Service
public interface Filter {
			
	Filter filter(String value,By which);
	Filter reload(FilterResult result);	
}
