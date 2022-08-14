package com.tech.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tech.ui.IOUtils;
import com.tech.ui.Icons;

import javafx.scene.image.Image;

@Component
public class ImageServiceImpl implements ImageService {
	
	
	@Value("${tech.com.images.path}")
	private String imgSource;
	
	@PostConstruct
	private void postConstruct() {
		try {
			Path dir = Paths.get(imgSource,"box\\img");		
			if(!Files.exists(dir)) {
				System.out.println(Files.createDirectory(dir));
			}
		} catch (IOException e) {			
		}
	}
		
	@Override
	public InputStream image(Icons action) {
		return ImageServiceImpl.class.getResourceAsStream(action.path());
	}

	@Override
	public Image loadImage(File file) {
		return IOUtils.loadImage(file);
	}

	@Override
	public Path imageStore(Path source,Path target) {
		return IOUtils.imageStore(source, target);
	}
	
	@Override
	public String getImageName(long id) {
		return String.format("%04d-img.png", id);
	}

	@Override
	public void deleteImage(Path dir) {
		IOUtils.deleteImage(dir);	
	}

	@Override
	public Path getImagePath() {
		return IOUtils.getImagePath(imgSource);
	}

}
