package com.sds.metac.output.reader.impl;

import java.io.File;
import java.util.SortedMap;

import org.apache.log4j.Logger;

import com.jramoyo.io.IndexedFileReader;
import com.sds.metac.cache.CacheEvent;
import com.sds.metac.config.ConfigManager;
import com.sds.metac.exception.MetaCException;
import com.sds.metac.file.FileManager;
import com.sds.metac.util.IndexedFileUtil;
import com.sds.metac.util.StringUtil;
import com.sds.metac.vo.config.UserSettingVO;
import com.sds.metac.vo.core.IndexDataVO;
import com.sds.metac.vo.domain.StandardVO;

public class StandardFileEvent implements CacheEvent<StandardVO> {
	
	private static final Logger log = Logger.getLogger(StandardFileEvent.class);
	
	ConfigManager configManager = ConfigManager.INSTANCE;
	FileManager fileManager = FileManager.INSTANCE;
	
	@Override
	public StandardVO doAction(Object key) {
		
		String name = (String)key;
		
		UserSettingVO userSetting = configManager.getUserSetting();
		
		// 용어에 해당하는 파일을 읽는다.
		File file = IndexedFileUtil.readTempFile(name, IndexedFileUtil.TAG_STN);
		
		// 인덱스 파일을 읽는다.
		String createIndexFileName = IndexedFileUtil.createIndexFileName(file.getName());
		File indexFile = fileManager.loadFile(userSetting.getTempFileFolder(), createIndexFileName);
		
		// 용어이 해당하는 해쉬값을 구한다.
		int hash = IndexedFileUtil.createHash(name);
		
		
		try {
			// 인덱스 파일중 해쉬값에 해당하는 위치에 실제 위치가 보관되어있다
			IndexedFileReader indexReader = new IndexedFileReader(indexFile);			
			SortedMap<Integer, String> indexTempValue = indexReader.readLines(hash, hash);			
			
			// 구분자로 나눈다음
			String[] indexInfos = indexTempValue.get(hash).split(IndexedFileUtil.INDEX_SEP);
			
			// 표준용어의 hashCode가 일치하는 값을 찾는다.
			IndexDataVO indexVO = null;
			for (String indexInfo : indexInfos) {
				if (StringUtil.isEmpty(indexInfo)) {
					continue;
				}
				
				String _key = IndexedFileUtil.getFirstJsonKey(indexInfo);
				
				if (StringUtil.equals(_key, ""+name.hashCode())) {
					indexVO = IndexDataVO.fromJson(indexInfo, IndexDataVO.class);
					break;
				}
			}
			indexReader.close();
			
			// indexVO 가 null 이면 에러
			if (indexVO == null) {
				log.debug("표준용어 [" + name + "]에 해당하는 값을 찾을 수 없습니다.");
				return null;
			}
			
			// 실제 표준용어 파일에 있는 위치를 읽는다
			Integer index = indexVO.getIndex();
			
			IndexedFileReader dataReader = new IndexedFileReader(file);
			SortedMap<Integer, String> dataTempValue = dataReader.readLines(index, index);
			
			String dataStr = dataTempValue.get(index);
			dataReader.close();
			
			return StandardVO.fromJson(dataStr, StandardVO.class);
			
		} catch (Exception e) {
			throw new MetaCException(e);
		}
	}
}
