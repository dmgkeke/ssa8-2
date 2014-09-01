package com.sds.metac.post;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class CreateJarFile {
	public static int BUFFER_SIZE=10240;
	
	protected void createJarArchive(File archiveFile, File[] tobeJared) throws IOException {
		
		byte buffer[] = new byte[BUFFER_SIZE];
		
		// Open archive file
		FileOutputStream stream = new FileOutputStream(archiveFile);
		JarOutputStream out = new JarOutputStream(stream, new Manifest());
		
		for(int i=0; i< tobeJared.length; i++) {
			if(tobeJared[i] == null || !tobeJared[i].exists() || tobeJared[i].isDirectory()) {
				continue; // Just in case...
			}
			System.out.println("Adding "+tobeJared[i].getName());
			
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
		System.out.println("Add OK");
	}
	
	public static void main(String[] args) throws IOException {
		CreateJarFile test = new CreateJarFile();
		File archiveFile = new File("./outputfiles/test.jar");
		File[] tobeJared = {new File("./inputfiles/TestClass.class")};
		
		// Arrays.asList();
		test.createJarArchive(archiveFile, tobeJared);
	}
}