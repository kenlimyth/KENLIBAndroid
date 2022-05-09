package com.kenlib.imgloader;

import com.kenlib.util.FileUtil;
import com.kenlib.util.KENConfig;


import android.content.Context;

public class FileCache extends AbstractFileCache{

	public FileCache(Context context) {
		super(context);
	
	}


	@Override
	public String getSavePath(String url) {
		String filename = String.valueOf(url.hashCode());
		return getCacheDir() + filename;
	}

	@Override
	public String getCacheDir() {
		
		return FileUtil.getRootFilePath()+KENConfig.FILE_DIR+"/files/";
	}

}
