package com.tech.navigation;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.fx.ControllerBase;

import net.rgielen.fxweaver.core.FxWeaver;


@Component
public class NavigatorFactory {
	
	@Autowired
	FxWeaver fxWeaver;
	
	public Navigator newInstance(URL url, Class<? extends ControllerBase> controller) {
		Navigator navigtor = () -> fxWeaver.loadView(controller);
		return navigtor;
	}

}
