package com.bis.sourcelead.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OrgStorageDetailDTO;

import net.coobird.thumbnailator.Thumbnails;

public class FileUploadUtil {
	public String cropImage(String staticPath, MultipartFile multiFile, int x, int y, int w, int h)
	{
		try {
			BufferedImage bufferedImage = Thumbnails.of(multiFile.getInputStream()).useExifOrientation(true).scale(1).asBufferedImage();
			bufferedImage = bufferedImage.getSubimage(x, y, w, h);
			if (bufferedImage == null) {
				return "failed";
			}
			bufferedImage = Thumbnails.of(bufferedImage).size(250, 250).asBufferedImage();
			String fileName = multiFile.getOriginalFilename();
			String extenstion = fileName.substring(fileName.lastIndexOf("."));
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			long time = new Date().getTime();
			String url = staticPath.substring(staticPath.lastIndexOf("/")) + "/profile/" + fileName
					+ time + extenstion;
			fileName = "/temp/" + fileName + time + extenstion;
			File folderFile = new File(staticPath, "/profile");
			if (!folderFile.exists())
				folderFile.mkdirs();
			folderFile = new File(staticPath, "/temp");
			if (!folderFile.exists())
				folderFile.mkdirs();
			File file = new File(staticPath, fileName);
			ImageIO.write(bufferedImage, extenstion.substring(1), file);
			return url;
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}
	
	public String uploadFile(String staticPath, long orgId, long locationId, String fileType, MultipartFile multipartFile)
	{
		String fileName = multipartFile.getOriginalFilename();
		String extenstion = fileName.substring(fileName.lastIndexOf("."));
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		long time = new Date().getTime();
		String url = staticPath.substring(staticPath.lastIndexOf("/")) + "/" + orgId + "/"
				+ locationId + "/" + fileType + "/" + fileName + time + extenstion;
		fileName = "/" + orgId + "/" + locationId + "/temp/" + fileName + time + extenstion;
		File folderFile = new File(staticPath, "/" + orgId + "/" + locationId + "/" + fileType);
		if (!folderFile.exists())
			folderFile.mkdirs();
		folderFile = new File(staticPath, "/" + orgId + "/" + locationId + "/temp");
		if (!folderFile.exists())
			folderFile.mkdirs();
		File file = new File(staticPath, fileName);
		try {
			multipartFile.transferTo(file);
			return url;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public List<FileInfo> uploadZipFile(String staticPath, long orgId, long locationId, String fileType, MultipartFile multipartFile)
	{
		List<FileInfo> fileInfoList = new ArrayList<>();
		String fileName = multipartFile.getOriginalFilename();
		String extenstion = fileName.substring(fileName.lastIndexOf("."));
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		long time = new Date().getTime();
		// String url = staticPath.substring(staticPath.lastIndexOf("/")) + "/"
		// + orgId + "/" + locationId + "/" + fileType
		// + "/" + fileName + time + extenstion;
		fileName = "/" + orgId + "/" + locationId + "/temp-files/" + fileName + time + extenstion;
		File folderFile = new File(staticPath, "/" + orgId + "/" + locationId + "/" + fileType);
		if (!folderFile.exists())
			folderFile.mkdirs();
		folderFile = new File(staticPath, "/" + orgId + "/" + locationId + "/temp-files");
		if (!folderFile.exists())
			folderFile.mkdirs();
		File file = new File(staticPath, fileName);
		try {
			multipartFile.transferTo(file);
			List<FileInfo> list = extractAll1(file.toPath(), folderFile.toPath(), orgId, locationId, staticPath);
			if (list != null)
				fileInfoList.addAll(list);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return fileInfoList;
		// return "";
	}
	
	private List<FileInfo> extractAll1(Path fromZip, Path toDirectory, long orgId, long locationId, String staticPath) throws IOException
	{
		final List<FileInfo> fileInfoList = new ArrayList<>();
		FileSystem zipFs = FileSystems.newFileSystem(fromZip, null);
		long time1 = new Date().getTime();
		for (Path root : zipFs.getRootDirectories()) {
			Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
				{
					String fileName = file.toString().substring(file.toString().lastIndexOf("/")
							+ 1);
					String extenstion = fileName.substring(fileName.lastIndexOf("."));
					long time = new Date().getTime();
					fileName = fileName.substring(0, fileName.lastIndexOf("."));
					File f = new File(toDirectory.toFile().getAbsolutePath() + time1);
					if (!f.exists())
						f.mkdirs();
					fileName = fileName + time + extenstion;
					Files.copy(file, new File(toDirectory.toFile().getAbsolutePath()
							+ time1, fileName).toPath());
					FileInfo fileInfo = new FileInfo();
					// fileInfo.setDate(new
					// Date(file.toFile().lastModified()).toString());
					fileInfo.setContentType(extenstion.equalsIgnoreCase(".doc")
							? "application/msword"
							: "");
					fileInfo.setTempFolderName("temp-files" + time1);
					fileInfo.setFileName(fileName);
					String url = staticPath.substring(staticPath.lastIndexOf("/")) + "/" + orgId
							+ "/" + locationId + "/resumes" + "/" + fileInfo.getFileName();
					fileInfo.setFileURL(url);
					fileInfoList.add(fileInfo);
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
				{
					return super.preVisitDirectory(dir, attrs);
				}
			});
		}
		return fileInfoList;
	}
	
	public OrgStorageDetailDTO getOrgStorageDetails(String staticPath, long orgId, String orgName)
	{
		File file = new File(staticPath, orgId + "");
		return null;
	}
	
	public static void moveFile(String staticPath, FileInfo fileInfo, String type) throws IOException
	{
		File folder = new File(staticPath);
		folder = folder.getParentFile();
		File file = new File(folder, fileInfo.getFileURL());
		File fromFile = new File(folder, fileInfo.getFileURL().replaceFirst(type, StringUtils.isNotBlank(fileInfo.getTempFolderName())
				? fileInfo.getTempFolderName()
				: "temp"));
		Files.move(fromFile.toPath(), file.toPath());
	}
	
	public static String moveFile(String staticPath, long orgId, long orgLocationId, String type, File file) throws IOException
	{
		String url = staticPath.substring(staticPath.lastIndexOf("/")) + "/" + orgId + "/"
				+ orgLocationId + "/" + type + "/" + file.getPath();
		File saveFileFolder = new File(staticPath, "/" + orgId + "/" + orgLocationId + "/" + type);
		if (!saveFileFolder.exists())
			saveFileFolder.mkdirs();
		try {
			Files.move(file.toPath(), new File(saveFileFolder, file.getName()).toPath());
			return url;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void fillUploadedFileInfo(Object object, List<Map<String, String>> fileInfo) throws Exception
	{
		List<FileInfo> uploadedFileInfo = new ArrayList<>();
		if (fileInfo != null) {
			for (Map<String, String> map : fileInfo) {
				Iterator<String> it = map.keySet().iterator();
				FileInfo f = new FileInfo();
				while (it.hasNext()) {
					String key = it.next();
					String value = map.get(key);
					if (key.equalsIgnoreCase("fileName"))
						f.setFileName(value);
					else if (key.equalsIgnoreCase("fileURL"))
						f.setFileURL(value);
					else
						f.setContentType(value);
				}
				uploadedFileInfo.add(f);
			}
		}
		object.getClass().getMethod("setUploadedFileInfo", List.class).invoke(object, uploadedFileInfo);
	}
}
