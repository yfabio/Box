package com.tech.fx;

import com.tech.navigation.Navigator;

import javafx.animation.FadeTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class Content extends StackPane {
	
	private ObjectProperty<Navigator> navigator = new SimpleObjectProperty<Navigator>();
				
	public Content() {		
		navigator.addListener((evt,older,newer) -> {						
			this.getChildren().clear();
			this.getChildren().add(newer.navigate());			
			animate();
		});				
	}
	
	private void animate() {
		FadeTransition fade = new FadeTransition();
		fade.setFromValue(0.1);		
		fade.setToValue(1);
		fade.setDuration(Duration.millis(750));		
		fade.setNode(this);	
		fade.play();
	}
	
	public ObjectProperty<Navigator> getNavigator() {
		return navigator;
	}
	
}
