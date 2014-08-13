package com.sds.metac.core;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	public <T> T createInstance(ClassInfoVO classInfoVO, Class<T> clazz) {
		try {
			FileManager fileManager = FileManager.INSTANCE;
			
			String path = fileManager.getResourceFilePath(classInfoVO.getClassFilePath());
			URL url = new URL(path);		
			myClassLoader.addURL(url);
			
//			ClassLoader classLoader = this.getClass().getClassLoader();
//			URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{url}, classLoader);
			
			
			Class<?> realClazz = (Class<T>) myClassLoader.loadClass(classInfoVO.getClassName());
			//Class<?> realClazz = (Class<T>) urlClassLoader.loadClass(classInfoVO.getClassName());
			return (T) realClazz.newInstance();
			
		} catch (Exception e) {
			throw new MetaCException("Class Loading에 실패하였습니다. : " + classInfoVO);
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
