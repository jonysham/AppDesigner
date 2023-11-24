package com.jonysham.appdesigner.util;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.net.URLDecoder;

import android.content.Context;
import android.content.ContentResolver;
import android.provider.MediaStore;
import android.net.Uri;
import android.os.Environment;
import android.content.ContentUris;
import android.text.TextUtils;
import android.provider.DocumentsContract;
import android.database.Cursor;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	
    public static void zip(String source, String destination) {
        try {
	        ZipOutputStream os = new ZipOutputStream(new FileOutputStream(destination));
			zip(os, source, null);
			os.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void zip(ZipOutputStream os, String filePath, String name) throws IOException {
		File file = new File(filePath);
		ZipEntry entry = new ZipEntry((name != null ? name + File.separator : "") + file.getName() + (file.isDirectory() ? File.separator : ""));
		os.putNextEntry(entry);
			
		if(file.isFile()) {
            InputStream is = new FileInputStream(file);
			int size = is.available();
			byte[] buff = new byte[size];
			int len = is.read(buff);
			os.write(buff, 0, len);
			return;
		}
			
		File[] fileArr = file.listFiles();
        
		for(File subFile : fileArr) {
			zip(os, subFile.getAbsolutePath(), entry.getName());
		}
    }
    
    public static void unzip(String source, String destination) {
        try {
	        File outdir = new File(destination);
	        ZipInputStream zin = new ZipInputStream(new FileInputStream(source));
	        ZipEntry entry;
        	String name, dir;
            
        	while ((entry = zin.getNextEntry()) != null) {
	        	name = entry.getName();
                
	        	if(entry.isDirectory()) {
			        mkdirs(outdir, name);
			        continue;
		        }
                
	        	dir = dirpart(name);
                
	        	if(dir != null) {
	            	mkdirs(outdir, dir);
		        }
                
		        extractFile(zin, outdir, name);
        	}
        	zin.close();
        } catch (IOException e) {
	        e.printStackTrace();
        }
    }
    
    private static void extractFile(ZipInputStream in, File outdir, String name) throws IOException {
	    byte[] buffer = new byte[4096];
	    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outdir, name)));
	    int count = -1;
	    while ((count = in.read(buffer)) != -1)
    	out.write(buffer, 0, count);
	    out.close();
    }

    private static void mkdirs(File outdir, String path) {
	    File d = new File(outdir, path);
	    if(!d.exists()) {
        	d.mkdirs();
        }
    }

    private static String dirpart(String name) {
	    int s = name.lastIndexOf(File.separatorChar);
	    return s == -1 ? null : name.substring(0, s);
    }
    
	public static String readFromAsset(String path, Context context) {
		try {
			InputStream inputStream = context.getAssets().open(path);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] _buf = new byte[1024];
			int i;

			while((i = inputStream.read(_buf)) != -1){
				outputStream.write(_buf, 0, i);
			}

			outputStream.close();
			inputStream.close();

			return outputStream.toString();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return "";
    }
	
	public static void copyFileFromAsset(String filename, String outPath, Context context) {
   	 AssetManager assetManager = context.getAssets();

   	 InputStream in;
  	  OutputStream out;
		
  	  try {
      	  in = assetManager.open(filename);
       	 String newFileName = outPath + "/" + filename;
      	  out = new FileOutputStream(newFileName);

       	 byte[] buffer = new byte[1024];
       	 int read;
			
       	 while ((read = in.read(buffer)) != -1) {
          	  out.write(buffer, 0, read);
      	  }
			
      	  in.close();
      	  out.flush();
      	  out.close();
   	 } 
		catch (Exception e) {
       	 e.printStackTrace();
    	}
	}
	
	private static void createNewFile(String path) {
        int lastSep = path.lastIndexOf(File.separator);
        if (lastSep > 0) {
            String dirPath = path.substring(0, lastSep);
            makeDir(dirPath);
        }

        File file = new File(path);

        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String path) {
        createNewFile(path);

        StringBuilder sb = new StringBuilder();
        FileReader fr = null;
        try {
            fr = new FileReader(new File(path));

            char[] buff = new char[1024];
            int length = 0;

            while ((length = fr.read(buff)) > 0) {
                sb.append(new String(buff, 0, length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public static void writeFile(String path, String str) {
        createNewFile(path);
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(new File(path), false);
            fileWriter.write(str);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyFile(String sourcePath, String destPath) {
        if (!isExistFile(sourcePath)) return;
        createNewFile(destPath);

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(sourcePath);
            fos = new FileOutputStream(destPath, false);

            byte[] buff = new byte[1024];
            int length = 0;

            while ((length = fis.read(buff)) > 0) {
                fos.write(buff, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void copyDir(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        File[] files = oldFile.listFiles();
        File newFile = new File(newPath);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        for (File file : files) {
            if (file.isFile()) {
                copyFile(file.getPath(), newPath + "/" + file.getName());
            } else if (file.isDirectory()) {
                copyDir(file.getPath(), newPath + "/" + file.getName());
            }
        }
    }

    public static void moveFile(String sourcePath, String destPath) {
        copyFile(sourcePath, destPath);
        deleteFile(sourcePath);
    }

    public static void deleteFile(String path) {
        File file = new File(path);

        if (!file.exists()) return;

        if (file.isFile()) {
            file.delete();
            return;
        }

        File[] fileArr = file.listFiles();

        if (fileArr != null) {
            for (File subFile : fileArr) {
                if (subFile.isDirectory()) {
                    deleteFile(subFile.getAbsolutePath());
                }

                if (subFile.isFile()) {
                    subFile.delete();
                }
            }
        }

        file.delete();
    }

    public static boolean isExistFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void makeDir(String path) {
        if (!isExistFile(path)) {
            File file = new File(path);
            file.mkdirs();
        }
    }

    public static void listDir(String path, ArrayList<String> list) {
        File dir = new File(path);
        if (!dir.exists() || dir.isFile()) return;

        File[] listFiles = dir.listFiles();
        if (listFiles == null || listFiles.length <= 0) return;

        if (list == null) return;
        list.clear();
        for (File file : listFiles) {
            list.add(file.getAbsolutePath());
        }
    }

	public static String convertUriToFilePath(final Context context, final Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    path = Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);

                if (!TextUtils.isEmpty(id)) {
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                }

                final Uri contentUri = ContentUris
					.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                path = getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
					split[1]
                };

                path = getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            path = getDataColumn(context, uri, null, null);
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }

        if (path != null) {
            try {
                return URLDecoder.decode(path, "UTF-8");
            } catch(Exception e) {
                return null;
            }
        }
        return null;
    }
	
    @SuppressLint("Recycle")
	private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        final String column = MediaStore.Images.Media.DATA;
        final String[] projection = {
			column
        };

        try {
			Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {

        }
        return null;
    }
	
	private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
	
	private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
	
    public static boolean isDirectory(String path) {
        if (!isExistFile(path)) return false;
        return new File(path).isDirectory();
    }

    public static boolean isFile(String path) {
        if (!isExistFile(path)) return false;
        return new File(path).isFile();
    }

    public static long getFileLength(String path) {
        if (!isExistFile(path)) return 0;
        return new File(path).length();
    }

    public static String getExternalStorageDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getPackageDataDir(Context context) {
        return context.getExternalFilesDir(null).getAbsolutePath();
    }

    public static String getPublicDir(String type) {
        return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
    }
	
	public static String getLastSegmentFromPath(String path) {
		return path.substring(path.lastIndexOf("/") + 1, path.length());
	}
}