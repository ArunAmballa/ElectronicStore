package com.arun.service.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.arun.exceptions.BadApiRequestException;
import com.arun.service.FileService;



@Service
public class FileServiceImpl implements FileService{

	private Logger logger=LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		
		//abc.png
		String originalFilename=file.getOriginalFilename();
		logger.info("Filename {}",originalFilename);
		
		String filename=UUID.randomUUID().toString();
		
		String extension=originalFilename.substring(originalFilename.lastIndexOf("."));
		
		String fileNamewithExtension=filename+extension;
		
		String fullPathWithFileName=path+fileNamewithExtension;
		
		logger.info("full Path Image {}",fullPathWithFileName);
		if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") ||extension.equalsIgnoreCase(".jpeg"))
		{
			File folder=new File(path);
			
			if(!folder.exists()) {
				
				folder.mkdirs();
				
			}
			
			Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
			
			return fileNamewithExtension;
			
		}
		else
		{
			throw new BadApiRequestException("File with this"+extension+"Not allowed");
		}
		
		
	}

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {
		
		String fullPath=path+File.separator+name;
		
		InputStream inputStream=new FileInputStream(fullPath);
		
		
		return inputStream;
	}

}
