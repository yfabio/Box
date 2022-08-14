package com.tech.service;

import org.springframework.stereotype.Component;

import javafx.application.Platform;

@Component
public class WindowServiceImpl implements WindownService {
	@Override
	public void shutdown() {
		Platform.exit();
	}
}
