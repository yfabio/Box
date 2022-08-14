package com.tech.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tech.fx.Content;
import com.tech.menu.ToolBarController;
import com.tech.service.WindownService;
import com.tech.ui.StageAwareController;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;


@Component
@FxmlView("/xml/main-view.fxml")
public class MainController implements StageAwareController {

	@FXML
	private ToolBarController toolBarController;

	@FXML
	private Content content;

	@Autowired
	private WindownService windownService;
	
		
	@FXML
	public void initialize() {		
		content.getNavigator().bind(toolBarController.getNavigator());			
	}

	@Override
	public void setStage(Stage stage) {
		stage.setOnCloseRequest(e -> {windownService.shutdown();});		
	}

}
