package com.kenlib.imgloader;

import java.io.File;


import android.content.Context;
import android.util.Log;

import com.kenlib.util.FileUtil;


public abstract class AbstractFileCache {

	private String dirString;
	
	public AbstractFileCache(Context context) {
		
		dirString = getCacheDir();
		boolean ret = FileUtil.createDirectory(dirString);
		Log.e("", "FileHelper.createDirectory:" + dirString + ", ret = " + ret);
	}
	
	public File getFile(String url) {
		File f = new File(getSavePath(url));
		return f;
	}
	
	public abstract String  getSavePath(String url);
	public abstract String  getCacheDir();

	public void clear() {
		FileUtil.delFileAndDir(dirString);
	}

}
