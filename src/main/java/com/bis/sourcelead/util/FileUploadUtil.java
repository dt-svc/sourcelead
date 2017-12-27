package com.bis.sourcelead.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OrgStorageDetailDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;

import net.coobird.thumbnailator.Thumbnails;

public class FileUploadUtil {

	public String cropImage(String staticPath, File inFile, int x, int y, int w, int h) {
		try {
			BufferedImage bufferedImage = Thumbnails.of(new FileInputStream(inFile)).useExifOrientation(true).scale(1).asBufferedImage();
			if(x > 0 || y > 0 || w > 0 || h > 0)
				bufferedImage = bufferedImage.getSubimage(x, y, w, h);
			if (bufferedImage == null) {
				return "failed";
			}
			bufferedImage = Thumbnails.of(bufferedImage).size(250, 250).asBufferedImage();
			String fileName = inFile.getName().replace("&", "").replace("%", "").replace("?", "");
			String extenstion = fileName.substring(fileName.lastIndexOf("."));
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			long time = new Date().getTime();
			String url = staticPath.substring(staticPath.lastIndexOf("/")) + "/profile/" + fileName + time + extenstion;
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

	public String uploadFile(String staticPath, long orgId, long locationId, String fileType,
			File inFile) {
		String fileName = inFile.getName().replace("&", "").replace("%", "").replace("?", "");;
		String extenstion = fileName.substring(fileName.lastIndexOf("."));
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		long time = new Date().getTime();
		String url = staticPath.substring(staticPath.lastIndexOf("/")) + "/" + orgId + "/" + locationId + "/" + fileType
				+ "/" + fileName + time + extenstion;
		fileName = "/" + orgId + "/" + locationId + "/temp/" + fileName + time + extenstion;
		File folderFile = new File(staticPath, "/" + orgId + "/" + locationId + "/" + fileType);
		if (!folderFile.exists())
			folderFile.mkdirs();
		folderFile = new File(staticPath, "/" + orgId + "/" + locationId + "/temp");
		if (!folderFile.exists())
			folderFile.mkdirs();

		File file = new File(staticPath, fileName);
		try {
			Files.move(inFile.toPath(), file.toPath());
			return url;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public List<FileInfo> uploadZipFile(String staticPath, long orgId, long locationId, String fileType, File inFile)
	{
		List<FileInfo> fileInfoList = new ArrayList<>();
		String fileName = inFile.getName();
		String extenstion = fileName.substring(fileName.lastIndexOf("."));
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		long time = new Date().getTime();
		fileName = "/" + orgId + "/" + locationId + "/temp-files/" + fileName + time + extenstion;
		File folderFile = new File(staticPath, "/" + orgId + "/" + locationId + "/" + fileType);
		if (!folderFile.exists())
			folderFile.mkdirs();
		folderFile = new File(staticPath, "/" + orgId + "/" + locationId + "/temp-files");
		if (!folderFile.exists())
			folderFile.mkdirs();
		File file = new File(staticPath, fileName);
		try {
			Files.move(inFile.toPath(), file.toPath());
			List<FileInfo> list = extractAll1(file.toPath(), folderFile.toPath(), orgId, locationId, staticPath);
			if (list != null)
				fileInfoList.addAll(list);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return fileInfoList;
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
							+ 1).replace("&", "").replace("%", "").replace("?", "");
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

	public OrgStorageDetailDTO getOrgStorageDetails(String staticPath, long orgId, String orgName) {
		File file = new File(staticPath, orgId + "");

		return null;
	}

	public static void moveFile(String staticPath, FileInfo fileInfo, String... type) throws IOException
	{
		File folder = new File(staticPath);
		folder = folder.getParentFile();
		File file = new File(folder, type.length == 1 ? fileInfo.getFileURL() : fileInfo.getFileURL().replace(type[0], type[1]));
		File file1 = file.getParentFile();
		if(!file.exists())
			file1.mkdirs();
		File fromFile = new File(folder, fileInfo.getFileURL().replaceFirst(type[0], StringUtils.isNotBlank(fileInfo.getTempFolderName())
				? fileInfo.getTempFolderName()
				: "temp"));
		Files.move(fromFile.toPath(), file.toPath());
	}

	public static String moveFile(String staticPath, long orgId, long orgLocationId, String type, File file)
			throws IOException {
		String url = staticPath.substring(staticPath.lastIndexOf("/")) + "/" + orgId + "/" + orgLocationId + "/" + type + "/" + file.getPath();
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

	public static void fillUploadedFileInfo(Object object, List<Map<String, String>> fileInfo) throws Exception {
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
	
	public static String getResumeZipFileName(String staticPath, HttpServletRequest request, String uploadid, boolean failed) {
		File file = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
	    try {
			file = File.createTempFile("temp/"+(failed ? "failed" : "existed")+"-", ""+ uploadid + ".zip");
			fos = new FileOutputStream(file);
			zos = new ZipOutputStream(fos);
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			long orgId = 0, orgLocationId = 0;
			if (org != null) {
				orgId = org.getId();
			}
			if (loginOrgLocation != null) {
				orgLocationId = loginOrgLocation.getId();
			}

			File folder = new File(staticPath, orgId+"/"+orgLocationId+"/resumes-"+(failed ? "failed" : "existed")+"-"+uploadid);
			if(!folder.exists() || folder.list().length == 0)
				return "No "+(failed ? "failed" : "existed")+" files found";
			File files[] = folder.listFiles();
			for (File f : files) {
				try(FileInputStream fis = new FileInputStream(f);) {
					ZipEntry entry = new ZipEntry(f.getName());
					byte b[] = new byte[fis.available()];
					fis.read(b);
					entry.setExtra(b);
				    zos.putNextEntry(entry);
				    zos.closeEntry();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if(zos != null)
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}	
		return file == null ? "" : file.getAbsolutePath();
	}

}