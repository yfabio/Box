package com.tech.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.fx.ControllerBase;
import com.tech.navigation.Navigator;
import com.tech.navigation.NavigatorFactory;


@Component
public class NavigationServiceImpl implements NavigationService {
	
	@Autowired
	private NavigatorFactory navigatorFactory;

	@Override
	public Navigator getNavigator(URL url, Class<? extends ControllerBase> controller) {
		return navigatorFactory.newInstance(url, controller);
	}

	
	
}
