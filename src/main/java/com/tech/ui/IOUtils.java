package com.tech.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class IOUtils {
	
	public static File openFileDialog(Window window) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));
		 return fileChooser.showOpenDialog(window);
	}
	
	public static Image loadImage(File file) {
		Optional<Image> op = Optional.empty();
		try {
			op = Optional.of(new Image(Files.newInputStream(file.toPath())));
		} catch (IOException e) {
			throw new RuntimeException();
		}
		return op.orElseThrow(() -> new RuntimeException("Unable to load Image"));
	}
	
	public static Path imageStore(Path source, Path target) {
		try {			
			return Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static void deleteImage(Path dir) {
		try {
			Files.delete(dir);
		} catch (IOException e) {
			throw new RuntimeException();
		}	
	}
	
	public static Path getImagePath(String imgPath) {
		return Paths.get(imgPath, "box","img");
	}
	
}
