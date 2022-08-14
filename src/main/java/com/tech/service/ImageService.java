package com.tech.service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import com.tech.ui.Icons;

import javafx.scene.image.Image;

@Service
public interface ImageService {
	public InputStream image(Icons action);
	public Image loadImage(File file);
	public Path imageStore(Path source, Path target);	
	public String getImageName(long id);
	public void deleteImage(Path dir);
	public Path getImagePath();
}
