package me.weix.fairy.util;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
public class FileOperationUtil {

	public static String originalName(File file) {
		return file.getName();
	}

	public static String newName(File file) {
		return UUID.randomUUID() + originalName(file);
	}

	public static String uploadPath(File file) {
		return "/upload/" + newName(file);
	}

	public static void upload(CommonsMultipartFile file, File f) {
		FileOutputStream fos = null;
		InputStream in = null;

		if (!f.exists())
			f.mkdirs();
		if (!file.isEmpty()) {
			try {
				fos = new FileOutputStream(f.getPath());
				in = file.getInputStream();
				int len = 0;
				byte[] b = new byte[1024];
				while ((len = in.read(b)) != -1) {
					fos.write(b, 0, b.length);
				}
				fos.close();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
