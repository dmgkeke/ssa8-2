package com.sds.metac.post;

import java.io.IOException;
import java.util.Arrays;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class CompileJavaFile {
	
	
	// 소스 위치, 소스목록, 타겟위치 
	
	
	
	public static void main(String[] args) throws IOException {
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
	}
}
