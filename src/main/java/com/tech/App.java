package com.tech;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.tech.main.MainController;
import com.tech.ui.Icons;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;


public class App extends Application {

	private ConfigurableApplicationContext springContext;		
	private Parent root;
	
	
	@Override
	public void start(Stage stage) throws Exception {		
		
		//FXMLLoader fxmlLoader = FXMLExpander.load(stage, getClass().getResource(UI.MAIN.url()));
		//springContext = SpringApplication.run(Main.class);		
		//fxmlLoader.setControllerFactory(springContext::getBean);	
		
		FxWeaver fxWeaver = springContext.getBean(FxWeaver.class);
		root = fxWeaver.loadView(MainController.class);				
		Image icon = new Image(getClass().getResourceAsStream(Icons.APP.path()));		
		Scene scene = new Scene(root);				
		stage.getIcons().add(icon);
		stage.centerOnScreen();
		stage.setScene(scene);
		stage.show();
	}
	
	
	@Override
	public void init() throws Exception {		
		String[] args = getParameters().getRaw().toArray(new String[0]);		
		springContext = new SpringApplicationBuilder()
							.sources(Main.class)
							.run(args);
		
	}


	@Override
	public void stop() throws Exception {
		springContext.close();
	}
}
