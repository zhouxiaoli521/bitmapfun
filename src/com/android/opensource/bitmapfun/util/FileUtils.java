/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * auther：xiaoying
 * date：2013-9-25
 * version：v1.0
 */	
package com.android.opensource.bitmapfun.util;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;

/**
 * File tools class.
 * 
 * @author xiaoying
 * 
 */
public class FileUtils {
	/**
	 * Clean cache directory
	 * @param context
	 */
	public static void cleanInternalCache(Context context) {
		deleteFile(context.getCacheDir());
	}

	/**
	 * Clean database directory
	 * @param context
	 */
	public static void cleanDatabases(Context context) {
		String [] databases = context.databaseList();
		if(databases != null) {
			for (String database : databases) {
				context.deleteDatabase(database);
			}
		}
	}
	
	/**
	 * Delete database directory
	 * @param context
	 */
	public static void deleteDatabasesDir(Context context) {
		deleteFile(new File(context.getApplicationInfo().dataDir + "/databases"));
	}

	/**
	 * Clean shared_prefs directory
	 * @param context
	 */
	public static void cleanSharedPreference(Context context) {
		deleteFile(new File(context.getApplicationInfo().dataDir + "/shared_prefs"));
	}
	
	/**
	 * Delete database by name.
	 * @param context
	 * @param dbName
	 */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}
	
	

	/**
	 * Clean files directory
	 * @param context
	 */
	public static void cleanFiles(Context context) {
		deleteFile(context.getFilesDir());
	}

	/**
	 * Clean external cache directory.
	 * @param context
	 */
	@SuppressLint("NewApi")
	public static void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteFile(context.getExternalCacheDir());
		}
	}

	/**
	 * Clean all data
	 * @param context
	 * @param filepath
	 */
	public static void cleanApplicationData(Context context, String... filepath) {
		cleanInternalCache(context);
		cleanExternalCache(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		for(String file : filepath) {
			deleteFile(new File(file));
		}
	}

	/**
	 * Delete file(include not empty directory)
	 * @param file
	 */
	public static void deleteFile(File file) {
		if(file.exists()) {
			if(file.isDirectory()) {
				File [] files = file.listFiles();
				for (File file2 : files) {
					deleteFile(file2);
				}
			}
			file.delete();
		}
	}
	
	/**
	 * Get file size(include directory)
	 * @param file
	 * @return
	 */
	public static long getFileSize(File file) {
		long size = 0L;
		if(file.exists()) {
			size += file.length();
			if(file.isDirectory()) {
				File [] files = file.listFiles();
				for (File file2 : files) {
					size += getFileSize(file2);
				}
			}
		}
		return size;
	}
	
	/**
	 * Change byte to KB/MB/GB...（keep two float point）
	 * @param context
	 * @param size
	 * @return
	 */
	public static String formatByte(Context context, long size){
		return Formatter.formatFileSize(context, size);// Change byte to KB or MB, etc.
	}
	
	/**
	 * Change byte to KB/MB/GB...(Keep Integer)
	 * @param context
	 * @param size
	 * @return
	 */
	public static String formatByteFixed(long size){
		if(size <= 0) return "0B";
		if(size < 1024) return size + "B"; else size = size/1024;
		if(size < 1024) return size + "KB"; else size = size/1024;
		if(size < 1024) return size + "MB"; else size = size/1024;
		if(size < 1024) return size + "GB"; else size = size/1024;
		if(size < 1024) return size + "TB"; else size = size/1024;
		if(size < 1024) return size + "PB"; else size = size/1024;
		if(size < 1024) return size + "EB"; else size = size/1024;
		if(size < 1024) return size + "ZB"; else size = size/1024;
		if(size < 1024) return size + "YB"; else size = size/1024;
		if(size < 1024) return size + "NB"; else size = size/1024;
		if(size < 1024) return size + "DB"; else size = size/1024; return size + "CB";
	}
}
