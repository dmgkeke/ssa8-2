package com.sds.metac.post;

import java.io.IOException;
import java.util.Arrays;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JavaCompile {
	public static void main(String[] args) throws IOException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		DiagnosticCollector<JavaFileObject> diagnostics = null;
		
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		
//		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles();
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList("./inputfiles/TestClass.java"));
		
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
		boolean success = task.call();
		fileManager.close();
		System.out.println("Success: "+success);
	}
}
