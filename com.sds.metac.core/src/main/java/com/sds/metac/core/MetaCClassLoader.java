package com.sds.metac.core;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.sds.metac.config.ConfigManager;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;
import com.sds.metac.vo.core.ClassInfoVO;

public enum MetaCClassLoader {
	INSTANCE;
	
	
	private MyClassLoader myClassLoader;	

	private MetaCClassLoader() {
		URLClassLoader loader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		
		myClassLoader = new MyClassLoader(loader.getURLs());
	}
	
	
	public <T> T createInstance(ClassInfoVO classInfoVO, Class<T> clazz) {
		T ret = createInstance(classInfoVO.getClassFilePath(), classInfoVO.getClassName(), clazz);
		if (ret == null) {
			throw new MetaCException("Class Loading에 실패하였습니다. : " + classInfoVO);
		}
		return ret;
	}
	
	public <T> T createInstanceNoError(ClassInfoVO classInfoVO, Class<T> clazz) {
		return createInstance(classInfoVO.getClassFilePath(), classInfoVO.getClassName(), clazz);
	}
	
	public <T> T createUIInstanceNoError(ClassInfoVO classInfoVO, Class<T> clazz) {
		return createInstance(classInfoVO.getClassFilePath(), classInfoVO.getUiClassName(), clazz);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T createInstance(String classFilePath, String className, Class<T> clazz) {
		try {
			FileManager fileManager = FileManager.INSTANCE;
			ConfigManager configManager = ConfigManager.INSTANCE;
			String implementationFolder = configManager.getUserSetting().getImplementationFolder();
			
			String filePath = implementationFolder + FileManager.FOLDER_SEP + classFilePath;
			
			
			String path = fileManager.getResourceFilePath(filePath);
			URL url = new URL(path);		
			myClassLoader.addURL(url);
			
	//		ClassLoader classLoader = this.getClass().getClassLoader();
	//		URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{url}, classLoader);
			
			
			Class<?> realClazz = (Class<T>) myClassLoader.loadClass(className);
			//Class<?> realClazz = (Class<T>) urlClassLoader.loadClass(classInfoVO.getClassName());
			return (T) realClazz.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	class MyClassLoader extends URLClassLoader {

		private List<URL> urls = new ArrayList<URL>();
		
		public MyClassLoader(URL[] urls) {
			super(urls);
			
			for (URL url : urls) {
				this.urls.add(url);
			}
		}
		
		public void addURL(URL url) {
			boolean isExist = false;
			for (URL _url : urls) {
				if (_url.sameFile(url)) {
					isExist = true;
					break;
				}
			}
			
			if (!isExist) {
				urls.add(url);
				super.addURL(url);
			}
		}
	}
}
