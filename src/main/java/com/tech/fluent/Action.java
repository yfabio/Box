package com.tech.fluent;

import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.springframework.stereotype.Component;

import javafx.geometry.Pos;
import javafx.util.Duration;

@Component
public abstract class Action<T> implements Crud {	
	
	protected ValidationSupport validation = new ValidationSupport();
	
	protected boolean isValid;	
	
	public Action() {
		validation.invalidProperty().addListener((obs,older,newer) -> {
			if(!newer) {
				isValid = true;				
			}else {
				isValid = false;				
			}
		});
	}
	
	protected enum Process {
		DELETE,UPDATE,CREATE;
	}
	
	protected void notifyErrorImage() {
		Notifications notication = Notifications.create()
								   .title("Image Field")
								   .text("Image is required!")
								   .position(Pos.TOP_RIGHT)
								   .hideAfter(Duration.seconds(2.5));
		notication.showError();
	}
	
	protected void notifyAction(String action, String text) {
		Notifications notication = Notifications.create()
								   .title(action)
								   .text(text)
								   .position(Pos.TOP_RIGHT)
								   .hideAfter(Duration.seconds(2.5));
		notication.show();
	}
	
	abstract void register(T newer);
	abstract void unregister(T older);

}
