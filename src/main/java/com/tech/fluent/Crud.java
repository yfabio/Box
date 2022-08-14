package com.tech.fluent;

import org.springframework.stereotype.Service;

@Service
public interface Crud {
	
	Crud validate();

	Crud delete();

	Crud update();

	Crud create();
	
	Crud commit();
		
	Crud unbind();
	
	Crud reset();
	
	Crud unBindSelected();
		
}
