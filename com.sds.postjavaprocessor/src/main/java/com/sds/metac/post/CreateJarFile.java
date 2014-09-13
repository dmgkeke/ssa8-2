package com.sds.metac.post;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.apache.log4j.Logger;

import com.samsung.sds.ssa8_2.postprocessorconfiguration.PostProcessorConfiguration;
import com.sds.metac.file.FileManager;

public class CreateJarFile {
	// logging
	Logger logger = Logger.getLogger(CreateJarFile.class);
	// config
	private PostProcessorConfiguration inputConfigInfo;
	
	private static int BUFFER_SIZE=10240;
	private File archiveFile;
	private File[] tobeJared;
	
	
	private void initialize() {
		if(inputConfigInfo == null) {
			FileManager fileManger = FileManager.INSTANCE;
			inputConfigInfo = fileManger.readConfigXmlFile("PostProcessorConfiguration.xml", PostProcessorConfiguration.class);
			File sourceFolder = fileManger.loadFolder(inputConfigInfo.getCompileConfiguration().getInputFilesPath());
			
			FilenameFilter classFileFilter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.endsWith(".class")){
						return true;
					}
					return false;
				}
			};	
			tobeJared = sourceFolder.listFiles((classFileFilter));		
			archiveFile = fileManger.loadFile(inputConfigInfo.getCreateJarConfiguration().getOutputFilePath(), 
					inputConfigInfo.getCreateJarConfiguration().getFileName()+".jar");
		}
	}
	
	protected void createJarArchive() {
		initialize();	
		byte buffer[] = new byte[BUFFER_SIZE];
		boolean success = false;
		
		// Open archive file
		FileOutputStream stream;
		JarOutputStream out;
		try {
			stream = new FileOutputStream(archiveFile);
			out = new JarOutputStream(stream, new Manifest());
			
			for(int i=0; i< tobeJared.length; i++) {
				if(tobeJared[i] == null || !tobeJared[i].exists() || tobeJared[i].isDirectory()) {
					continue; // Just in case...
				}
				logger.info("Adding "+tobeJared[i].getName());
				
				// Add archive entry
				JarEntry jarAdd = new JarEntry(tobeJared[i].getName());
				jarAdd.setTime(tobeJared[i].lastModified());
				out.putNextEntry(jarAdd);
				
				// Write file to archive
				FileInputStream in = new FileInputStream(tobeJared[i]);
				while(true) {
					int nRead = in.read(buffer, 0, buffer.length);
					if(nRead <=0) {
						break;
					}
					out.write(buffer, 0, nRead);
				}
				in.close();
			}
			out.close();
			stream.close();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			logger.info(success);
		}
	}
	
	/*public static void main(String[] args) throws IOException {
		CreateJarFile test = new CreateJarFile();
		File archiveFile = new File("./outputfiles/test.jar");
		File[] tobeJared = {new File("./inputfiles/TestClass.class")};
		
////		void createJarArchive(File archiveFile, File[] tobeJared) 
//		test.createJarArchive(archiveFile, tobeJared);
	}*/
}