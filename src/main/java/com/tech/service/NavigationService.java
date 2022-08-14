package com.tech.service;

import java.net.URL;

import org.springframework.stereotype.Service;

import com.tech.fx.ControllerBase;
import com.tech.navigation.Navigator;

@Service
public interface NavigationService {
	public Navigator getNavigator(URL url,Class<? extends ControllerBase> controller);
}
