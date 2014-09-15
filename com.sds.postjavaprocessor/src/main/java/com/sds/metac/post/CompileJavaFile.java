package com.sds.metac.post;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;

import com.samsung.sds.ssa8_2.postprocessorconfiguration.PostProcessorConfiguration;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;


public class CompileJavaFile {
	// logging
	Logger logger = Logger.getLogger(CompileJavaFile.class);
	// config
	private PostProcessorConfiguration inputConfigInfo;
	
	private void initialize() {
		if(inputConfigInfo == null) {
			FileManager fileManger = FileManager.INSTANCE;
			inputConfigInfo = fileManger.readConfigXmlFile("PostProcessorConfiguration.xml", PostProcessorConfiguration.class);
		}
	}
	
	public void compile() {
		initialize();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager javaFileManager = compiler.getStandardFileManager(null, null, null);
		
		FileManager fileManger = FileManager.INSTANCE;
		File sourceFolder = fileManger.loadFolder(inputConfigInfo.getCompileConfiguration().getInputFilesPath());
		
		FilenameFilter javaFileFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith("."+inputConfigInfo.getProgramming().toString().toLowerCase())){
					return true;
				}
				return false;
			}
		};
		
		List<String> options = null;/*new ArrayList<String>();
		options.add("-d");
		options.add(getOutBuildDir().getAbsolutePath());
		options.add("-s");
		options.add(getOutSrcDir().getAbsolutePath());
		options.add("-verbose");*/
		
//		Iterable<? extends JavaFileObject> compilationUnits = javaFileManager.getJavaFileObjectsFromStrings(Arrays.asList(folder.list(filter)));
		Iterable<? extends JavaFileObject> compilationUnits = javaFileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFolder.listFiles((javaFileFilter))));
		JavaCompiler.CompilationTask task = compiler.getTask(null, javaFileManager, null, options, null, compilationUnits);
		boolean success = task.call();
		
		try {
			javaFileManager.close();
		} catch (IOException e) {
			throw new MetaCException(this.getClass()+".compile()");
		} finally {
			logger.info(success);
		}
	}
	
	
	/*public static void main(String[] args) throws IOException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		DiagnosticCollector<JavaFileObject> diagnostics = null;
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList("./inputfiles/TestClass.java"));
//		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles();
		
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
		boolean success = task.call();
		fileManager.close();
		System.out.println("Success: "+success);
	}*/
}
