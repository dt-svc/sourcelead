package com.bis.sourcelead.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateResumeDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OrgStorageDetailDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateImportLog;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.ImmigrationCandidateDocumentsHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateForms;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;

import net.coobird.thumbnailator.Thumbnails;

public class FileUploadUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
    public static String getFileContent(String staticPath, String contentFileUrl) {
        StringBuilder fileContent = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(staticPath, contentFileUrl)));
            String line;
            while((line = br.readLine()) != null) {
                fileContent.append(line).append(System.getProperty("line.separator"));
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return fileContent.toString();
    }

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
		String extenstion = "";
		if(fileName.lastIndexOf(".") != -1)
		extenstion = fileName.substring(fileName.lastIndexOf("."));
		if(fileName.lastIndexOf(".") != -1)
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
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Files.move(file.toPath(), inFile.toPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
					fileInfo.setContentType(extenstion.equalsIgnoreCase(".doc") || extenstion.equalsIgnoreCase(".docx")
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
		File file = new File(folder, type.length == 1 ? fileInfo.getFileURL().replaceAll(" ", "_") : fileInfo.getFileURL().replace(type[0], type[1]).replaceAll(" ", "_"));
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
			System.out.println("File moved to : " + url);
			return url;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static String moveBulkImportFailureFile(String staticPath, long orgId, long orgLocationId, String type, File file)
			throws IOException {
		String url = staticPath.substring(staticPath.lastIndexOf("/")) + "/" + orgId + "/" + orgLocationId + "/" + type + "/" + file.getName();
		File saveFileFolder = new File(staticPath, "/" + orgId + "/" + orgLocationId + "/" + type);
		if (!saveFileFolder.exists())
			saveFileFolder.mkdirs();
		try {
			Files.move(file.toPath(), new File(saveFileFolder, file.getName()).toPath());
			System.out.println("File moved to : " + url);
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
	
	public static String getResumeZipFileName(String staticPath, long orgId, long orgLocationId, String uploadid, boolean failed) {
		File file = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
	    try {
			file = File.createTempFile("temp/"+(failed ? "failed" : "existed")+"-", ""+ uploadid + ".zip");
			fos = new FileOutputStream(file);
			zos = new ZipOutputStream(fos);
			
			File folder = new File(staticPath, orgId+"/"+orgLocationId+"/resumes-"+(failed ? "failed" : "existed")+"-"+uploadid);
			if(!folder.exists() || folder.list().length == 0) {
				return "No "+(failed ? "Failed" : "Duplicate")+" Resumes Found";
			}
			File files[] = folder.listFiles();
			for (File f : files) {
				try(FileInputStream fis = new FileInputStream(f);) {
					byte[] buffer = new byte[128];
					ZipEntry entry = new ZipEntry(f.getName());
				    zos.putNextEntry(entry);
				    int read = 0;
			          while ((read = fis.read(buffer)) != -1) {
			            zos.write(buffer, 0, read);
			          }
				    zos.closeEntry();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception e1) {
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
	
	
	public static String getImmigrationZipFiles(String staticPath, Long orgId, Long orgLocationId,Long immigrationId,  Long docId , List <ImmigrationCandidateDocumentsHistory> docsList, Map<String, String> fileNameParameters) {
		File file = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
	    try {
			if(docId == null) {
	    	file = new File(com.google.common.io.Files.createTempDir(), fileNameParameters.get("immiUUID")+"_"+fileNameParameters.get("firstName")+"_"+fileNameParameters.get("lastName")+"_Docs"+".zip");
			}
			else if(docId != null && immigrationId != null) {
				file =  new File(com.google.common.io.Files.createTempDir(), fileNameParameters.get("firstName")+"_"+fileNameParameters.get("lastName")+"_"+fileNameParameters.get("docName")+".zip");
			}
	    	fos = new FileOutputStream(file);
			zos = new ZipOutputStream(fos);
			File folder = new File(staticPath, orgId+"/"+orgLocationId+"/immigrationCandidateDocuments");
			if(!folder.exists() || folder.list().length == 0) {
				if(immigrationId == null)
				return "No Documents Found";
			}
			File files[] = folder.listFiles();
			for (File f : files) {
			for(ImmigrationCandidateDocumentsHistory docObj: docsList){
				String fullFileName = FilenameUtils.getName(docObj.getUplodedFileUrl());
				if(f.getName().equalsIgnoreCase(fullFileName)){
				try(FileInputStream fis = new FileInputStream(f);) {
					byte[] buffer = new byte[128];
					ZipEntry entry = new ZipEntry(f.getName());
				    zos.putNextEntry(entry);
				    int read = 0;
			          while ((read = fis.read(buffer)) != -1) {
			            zos.write(buffer, 0, read);
			          }
				    zos.closeEntry();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			 }
			}
			}
		} catch (Exception e1) {
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
	
	
	public static String getImmigrationFormZipFiles(String staticPath, Long orgId, Long orgLocationId,Long immigrationId, List <ImmigrationCandidateForms> formsList, Map<String, String> fileNameParameters) {
		File file = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
	    try {
	    	
	    	file = new File(com.google.common.io.Files.createTempDir(),fileNameParameters.get("immiUUID")+"_"+fileNameParameters.get("firstName")+"_"+fileNameParameters.get("lastName")+"_Forms"+".zip");
	    	fos = new FileOutputStream(file);
			zos = new ZipOutputStream(fos);
			File folder = new File(staticPath, orgId+"/"+orgLocationId+"/immigrationCandidateForms");
			if(!folder.exists() || folder.list().length == 0) {
				return "No Documents Found";
			}
			File files[] = folder.listFiles();
			for (File f : files) {
			for(ImmigrationCandidateForms docObj: formsList){
				String fullFileName = FilenameUtils.getName(docObj.getUploadedFileUrl());
				if(f.getName().equalsIgnoreCase(fullFileName)){
				try(FileInputStream fis = new FileInputStream(f);) {
					byte[] buffer = new byte[128];
					ZipEntry entry = new ZipEntry(f.getName());
				    zos.putNextEntry(entry);
				    int read = 0;
			          while ((read = fis.read(buffer)) != -1) {
			            zos.write(buffer, 0, read);
			          }
				    zos.closeEntry();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			 }
			}
			}
		} catch (Exception e1) {
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

	public static String getAllImmigrationZipFiles(String staticPath, Long orgId, Long orgLocationId,
			Long immigrationId, HashMap<String, Object> docsListMap, Map<String, String> fileNameParameters) {
		File file = null;
		boolean DocsFoundFlag= false;
		System.out.println("insideget   AllImmigrationZipFiles  ------->");
		try {
			List<ImmigrationCandidateForms> formsList = (List<ImmigrationCandidateForms>) docsListMap.get("immForms");
			file = new File(com.google.common.io.Files.createTempDir(),
					fileNameParameters.get("immiUUID") + "_" + fileNameParameters.get("firstName") + "_"
							+ fileNameParameters.get("lastName") + "_" + "Immigration_Pacakage" + ".zip");
			File folder = new File(staticPath, orgId + "/" + orgLocationId + "/immigrationCandidateForms");

			// create a folder Directory name pattern is
			// ImmigrationUUID_FirstNam_LastName_Forms.
			File immFormFolder = new File(com.google.common.io.Files.createTempDir(),
					fileNameParameters.get("immiUUID") + "_" + fileNameParameters.get("firstName") + "_"
							+ fileNameParameters.get("lastName") + "_" + "Forms");
			immFormFolder.mkdir(); // make a immigration directory.

			File files[] = folder.listFiles();
			if (folder.exists() && folder.list().length >0) {
				DocsFoundFlag =true;
			}
			
			InputStream inStream = null;
			OutputStream outStream = null;
			for (File f : files) {
				for (ImmigrationCandidateForms docObj : formsList) { // for start
					String fullFileName = FilenameUtils.getName(docObj.getUploadedFileUrl());

					if (f.getName().equalsIgnoreCase(fullFileName)) {
						File afile = new File(f.getAbsolutePath());
						File bfile = new File(immFormFolder.getAbsolutePath() + File.separator + f.getName());

						try {
							inStream = new FileInputStream(afile);
							outStream = new FileOutputStream(bfile);

							byte[] buffer = new byte[1024];

							int length;
							// copy the file content in bytes
							while ((length = inStream.read(buffer)) > 0) {

								outStream.write(buffer, 0, length);

							}
							inStream.close();
							outStream.close();
							// delete the original file
							// afile.delete();
							// System.out.println("File is copied successful!");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} ///
			} // adding files to immigration forms to immigration form folder is completed.
				// start adding immDocs to
				// create a folder Directory name pattern is
				// ImmigrationUUID_FirstNam_LastName_Docs.
			List<ImmigrationCandidateDocumentsHistory> docsList = (List<ImmigrationCandidateDocumentsHistory>) docsListMap.get("immDocs");
			File immDocFolder = new File(com.google.common.io.Files.createTempDir(),
					fileNameParameters.get("immiUUID") + "_" + fileNameParameters.get("firstName") + "_"
							+ fileNameParameters.get("lastName") + "_" + "Docs");
			immDocFolder.mkdir(); // make a immigration doc folder .
			File docfolder = new File(staticPath, orgId + "/" + orgLocationId + "/immigrationCandidateDocuments");
			files = docfolder.listFiles();
			if (docfolder.exists() && docfolder.list().length > 0) {
				DocsFoundFlag =true;
			}
			inStream = null;
			outStream = null;
			for (File f : files) {
				for (ImmigrationCandidateDocumentsHistory docObj : docsList) { // for start
					String fullFileName = FilenameUtils.getName(docObj.getUplodedFileUrl());

					if (f.getName().equalsIgnoreCase(fullFileName)) {
						File afile = new File(f.getAbsolutePath());
						File bfile = new File(immDocFolder.getAbsolutePath() + File.separator + f.getName());

						try {
							inStream = new FileInputStream(afile);
							outStream = new FileOutputStream(bfile);

							byte[] buffer = new byte[1024];

							int length;
							// copy the file content in bytes
							while ((length = inStream.read(buffer)) > 0) {
								outStream.write(buffer, 0, length);
							}
							inStream.close();
							outStream.close();
							// delete the original file
							// afile.delete();
							// System.out.println("File is copied successful!");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} ///
			} // adding files to immigration doc folder is completed.
			// Adding canidate Resume to candidate docs list folder
			CandidateResumes candidateResume = (CandidateResumes) docsListMap.get("cadidateResume");
			if(candidateResume != null) {
				File resumeDocfolder = new File(staticPath, orgId + "/" + orgLocationId + "/resumes");
				files = resumeDocfolder.listFiles();
				if (resumeDocfolder.exists() && resumeDocfolder.list().length > 0) {
					DocsFoundFlag =true;
				}
				inStream = null;
				outStream = null;
				for (File f : files) {
						String fullFileName = FilenameUtils.getName(candidateResume.getFileURL());
						if (f.getName().equalsIgnoreCase(fullFileName)) {
							File afile = new File(f.getAbsolutePath());
							File bfile = new File(immDocFolder.getAbsolutePath() + File.separator + f.getName());
							try {
								inStream = new FileInputStream(afile);
								outStream = new FileOutputStream(bfile);
	
								byte[] buffer = new byte[1024];
	
								int length;
								// copy the file content in bytes
								while ((length = inStream.read(buffer)) > 0) {
									outStream.write(buffer, 0, length);
								}
								inStream.close();
								outStream.close();
								// delete the original file
								// afile.delete();
								// System.out.println("File is copied successful!");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
				}// end adding candidate resume to candidate docs folder. 
			}
			
			// adding files to candidate employer doc folder is starts here.
			List<CandidateEmployers> empdocsList = (List<CandidateEmployers>) docsListMap.get("employerDocs");
			//System.out.println("empdocsList size is "+empdocsList.size());
			
			
			File empDocFolder = new File(com.google.common.io.Files.createTempDir(),
					fileNameParameters.get("immiUUID") + "_" + fileNameParameters.get("firstName") + "_"
							+ fileNameParameters.get("lastName") + "_" + "EmployerDocs");
			
			File employerAndEduDocfolder = new File(staticPath, orgId + "/" + orgLocationId + "/candidateDocuments");
			files = employerAndEduDocfolder.listFiles();
			if (employerAndEduDocfolder.exists() && employerAndEduDocfolder.list().length > 0) {
				DocsFoundFlag =true;
			}
			empDocFolder.mkdir(); // make a Employer doc folder .
			inStream = null;
			outStream = null;
			for (File f : files) {
				for (CandidateEmployers docObj : empdocsList) { // for start
					String fullFileName = FilenameUtils.getName(docObj.getEmployerFileUrl());

					if (f.getName().equalsIgnoreCase(fullFileName)) {
						File afile = new File(f.getAbsolutePath());
						File bfile = new File(empDocFolder.getAbsolutePath() + File.separator + f.getName());

						try {
							inStream = new FileInputStream(afile);
							outStream = new FileOutputStream(bfile);

							byte[] buffer = new byte[1024];

							int length;
							// copy the file content in bytes
							while ((length = inStream.read(buffer)) > 0) {
								outStream.write(buffer, 0, length);
							}
							inStream.close();
							outStream.close();
							// delete the original file
							// afile.delete();
							// System.out.println("File is copied successful!");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} ///
			} // adding files to candidate employer doc folder is completed.
			// adding files to candidate employer doc folder is starts here.
						List<CandidateEducation> eduDocsList = (List<CandidateEducation>) docsListMap.get("educationDocs");
						//System.out.println("eduDocsList size is "+eduDocsList.size());
						
						
						File eduDocFolder = new File(com.google.common.io.Files.createTempDir(),
								fileNameParameters.get("immiUUID") + "_" + fileNameParameters.get("firstName") + "_"
										+ fileNameParameters.get("lastName") + "_" + "EducationDocs");
						
						eduDocFolder.mkdir(); // make a Employer doc folder .
						inStream = null;
						outStream = null;
						for (File f : files) {
							for (CandidateEducation docObj : eduDocsList) { // for start
								String fullFileName = FilenameUtils.getName(docObj.getEducationFileUrl());

								if (f.getName().equalsIgnoreCase(fullFileName)) {
									File afile = new File(f.getAbsolutePath());
									File bfile = new File(eduDocFolder.getAbsolutePath() + File.separator + f.getName());

									try {
										inStream = new FileInputStream(afile);
										outStream = new FileOutputStream(bfile);

										byte[] buffer = new byte[1024];

										int length;
										// copy the file content in bytes
										while ((length = inStream.read(buffer)) > 0) {
											outStream.write(buffer, 0, length);
										}
										inStream.close();
										outStream.close();
										// delete the original file
										// afile.delete();
										// System.out.println("File is copied successful!");
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							} ///
						} // adding files to candidate employer doc folder is completed.
			if(!DocsFoundFlag) {
			   return "No Documents Found";
			}
			String dirPath = immFormFolder.getAbsolutePath();
			File zipFileName = new File(file.getAbsolutePath());
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
			zipDirectory(dirPath, out, immFormFolder.getName()); // adding Immigration form folder to zip
			dirPath = immDocFolder.getAbsolutePath();
			zipDirectory(dirPath, out, immDocFolder.getName()); // adding Immigration doc folder to zip
			dirPath = empDocFolder.getAbsolutePath();      
			zipDirectory(dirPath, out, empDocFolder.getName());// adding Candidate Employers folder to zip
			dirPath = eduDocFolder.getAbsolutePath();      
			zipDirectory(dirPath, out, eduDocFolder.getName());// adding Candidate Education folder to zip
			out.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return file == null ? "" : file.getAbsolutePath();
	}

	public static void zipDirectory(String dirPath, ZipOutputStream zos, String parentFolderName)
			throws IOException { /* Place appropriate code form your "zipdirectory" method here */
		//System.out.println("dirPath  is " + dirPath);
		File f = new File(dirPath);
		String[] flist = f.list();
		//System.out.println("flist  size is  " + flist.length);
		for (int i = 0; i < flist.length; i++) {
			File ff = new File(f, flist[i]);
			if (ff.isDirectory()) {
				zipDirectory(ff.getPath(), zos, parentFolderName);
				continue;
			}
			String filepath = ff.getPath();
			//System.out.println("File Name is " + parentFolderName + File.separator + ff.getName());
			ZipEntry entries = new ZipEntry(parentFolderName + File.separator + ff.getName());
			zos.putNextEntry(entries);
			FileInputStream fis = new FileInputStream(filepath);
			int buffersize = 1024;
			byte[] buffer = new byte[buffersize];
			int count;
			while ((count = fis.read(buffer)) != -1) {
				zos.write(buffer, 0, count);
			}
			fis.close();
		}
	}
	


	public static String processResumeFiles(Map<String, List<ResumeParserJsonUtil>> parsedList, 
			List<FileInfo> fileInfoList, HttpSession session, String uploadid, String staticPath, 
			SourceleadResumeParserNew sourceleadResumeParser,
			CandidateService candidateService, CandidateResumesService candidateResumeService,
			String country, OrgLocations loginOrgLocation, String sourceleadUrl, User user,
			Long requirementId, String needApplicantPermit, String notifyToApplicant,CandidateGlobalUtil candidateGlobalUtil) {
		List<ResumeParserJsonUtil> successList = new ArrayList<>();
		List<ResumeParserJsonUtil> failedList = new ArrayList<>();
		List<ResumeParserJsonUtil> existedList = new ArrayList<>();
		parsedList.put("completed", successList);
		parsedList.put("failed", failedList);
		parsedList.put("existed", existedList);
		int i = 1;
		int count = fileInfoList.size();
		String candidateIds = "";
		for (FileInfo fileInfo : fileInfoList) {
			if(session != null) {
				session.setAttribute("uploadstatus" + uploadid,
				session.getAttribute("uploadstatus" + uploadid) + "<br/>Processing " + fileInfo.getFileName());
			}
			File file = new File(staticPath.substring(0, staticPath.lastIndexOf("/")), fileInfo.getFileURL().replace(
					"resumes",
					StringUtils.isNotBlank(fileInfo.getTempFolderName()) ? fileInfo.getTempFolderName() : "temp"));
			ResumeParserJsonUtil resume = null;
			try {
				resume = sourceleadResumeParser.getCandidateDetailsFromResume1(file, country, loginOrgLocation.getDateFormat(), fileInfo.getFileName());
				resume.setFileInfo(fileInfo);
				if (resume.getEmail() == null || resume.getEmail().isEmpty() || resume.getEmail().equals("null")) {
					throw new Exception("Email ID missing");
				}
				Candidate c = candidateService.findByCandidateEmail(resume.getEmail(), loginOrgLocation.getId());
				boolean flag = false;
				if (c != null && c.getId() > 0) {
					CandidateResumes cr = candidateResumeService.getCandidateResumeByCandidateId(c.getId());
					if (cr == null || cr.getFileURL() == null || cr.getFileURL().isEmpty())
						flag = true;
				}
				if (c != null && c.getId() > 0 && !flag) {
					fileInfo.setFileURL(fileInfo.getFileURL().replace("resumes", "resumes-existed-" + uploadid));
					FileUploadUtil.moveFile(staticPath, fileInfo, "resumes-existed-" + uploadid);
					addToResponse(0, session, uploadid, resume, fileInfo, count, i++, sourceleadUrl, candidateService);
					existedList.add(resume);
					candidateIds += (candidateIds.isEmpty() ? "" : ",") + c.getId();
					continue;
				} else if (flag) {
					CandidateResumeDTO candidateResumeDTO = new CandidateResumeDTO();
					List<FileInfo> fileInfoList1 = new ArrayList<>();
					fileInfoList1.add(fileInfo);
					candidateResumeDTO.setStaticPath(staticPath);
					candidateResumeDTO.setFileInfo(fileInfoList1);
					Long candidateGlobalId = candidateService.getCandidateIdByGlobalCandidateId(c.getId());
					candidateResumeService.storeCandidateResume(candidateResumeDTO, user, candidateGlobalId, null, loginOrgLocation);
					addToResponse(2, session, uploadid, resume, fileInfo, count, i++, sourceleadUrl, candidateService);
					successList.add(resume);
					candidateIds += (candidateIds.isEmpty() ? "" : ",") + c.getId();
				} else {
					CandidateDTO candidateDto = new CandidateDTO();
					candidateDto.setFileName(fileInfo.getFileName());
					candidateDto.setFileContentType(fileInfo.getContentType());
					candidateDto.setFileUrl(fileInfo.getFileURL());
					List<FileInfo> fileInfoList1 = new ArrayList<>();
					fileInfoList1.add(fileInfo);
					candidateDto.setFileInfo(fileInfoList1);
					candidateDto.setStaticPath(staticPath);
					
					candidateDto.setFirstName(resume.getFirstName() != null ? resume.getFirstName() : "");
					candidateDto.setMiddleName(resume.getMiddleName() != null ? resume.getMiddleName() : "");
					candidateDto.setLastName(resume.getLastName() != null ? resume.getLastName() : "");
					candidateDto.setEmail(resume.getEmail());
					Iterator<String> it = resume.getPhoneNumbers().iterator();
					while (it.hasNext()) {
						if (candidateDto.getMobilePhone() == null)
							candidateDto.setMobilePhone(it.next());
						else
							candidateDto.setAlternateMobilePhone(it.next());
					}
					candidateDto.setCity(resume.getCity() != null ? resume.getCity() : "");
					candidateDto.setState(resume.getState() != null ? resume.getState() : "");
					candidateDto.setCountry(resume.getCountry() != null ? resume.getCountry() : "");
					candidateDto.setZipcode(resume.getZipcode() != null ? resume.getZipcode() : "");
					candidateDto.setDateofBirth(resume.getDob() != null ? resume.getDob() : "");
					candidateDto.setMaritalStatus(resume.getMaritalStatus() != null ? resume.getMaritalStatus() : "");
					candidateDto.setGender(resume.getGender() != null ? resume.getGender() : "");
					candidateDto.setDesignation(resume.getDesignation() != null ? resume.getDesignation() : "");
					candidateDto.setExperienceYears(resume.getTotalYearsExp() + "");
					candidateDto.setExperienceMonths(resume.getTotalMonthsExp() + "");
					candidateDto.setCandidateEmployers(resume.getEmployeers() != null ? String.join(", ", resume.getEmployeers()) : "");
					candidateDto.setCandidateSkills(resume.getSkills() != null ? String.join(", ", resume.getSkills()) : "");
					candidateDto.setCandidateEducation(resume.getEducations() != null ? String.join(", ", resume.getEducations()) : "");
					candidateDto.setSummary(resume.getSummary() != null ? resume.getSummary() : "");
					candidateDto.setCandidateCreationType(Constants.IMPORT_CANDIDATE);
					CandidateGlobal storedCandidate = candidateGlobalUtil.storeCandidateGlobal(candidateDto, loginOrgLocation, user);
					if(requirementId > 0) {
						candidateIds += (candidateIds.isEmpty() ? "" : ",") + storedCandidate.getId();
						System.out.println("Add Applicant for requirement " + requirementId + " to newly imported candidate");
					}
					addToResponse(1, session, uploadid, resume, fileInfo, count, i++, sourceleadUrl, candidateService);
					successList.add(resume);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				fileInfo.setFileURL(fileInfo.getFileURL().replace("resumes", "resumes-failed-" + uploadid));
				try {
					moveFile(staticPath, fileInfo, "resumes-failed-" + uploadid);
				} catch (IOException e) {
					e.printStackTrace();
				}
				addToResponse(-1, session, uploadid, resume, fileInfo, count, i++, sourceleadUrl, candidateService);
				failedList.add(resume);
			}
		}
		/*try {
			if (requirementId > 0 && requirementRestController != null && !candidateIds.isEmpty()) {
				requirementRestController.submitRecommendationCandidates(requirementId, candidateIds, "Y", needApplicantPermit, notifyToApplicant, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		return candidateIds;
	}
	
	public static void processConfiguredEmailShedulerForResumesParsing(List<FileInfo> fileInfoList, String staticPath, 
			SourceleadResumeParserNew sourceleadResumeParser,
			CandidateService candidateService, CandidateResumesService candidateResumeService,
			String country, OrgLocations loginOrgLocation, User user,CandidateGlobalUtil candidateGlobalUtil) {
		List<ResumeParserJsonUtil> successList = new ArrayList<>();
		List<ResumeParserJsonUtil> failedList = new ArrayList<>();
		List<ResumeParserJsonUtil> existedList = new ArrayList<>();
		//System.out.println("inside processConfiguredEmailShedulerForResumesParsing -->"+fileInfoList.size());
		logger.debug("********inside  parsing resumes**********fileInfoList size is **==>"+fileInfoList.size());
		for (FileInfo fileInfo : fileInfoList) {
				//System.out.println("File name is -->"+fileInfo.getFileName());
			File file = new File(staticPath.substring(0, staticPath.lastIndexOf("/")), fileInfo.getFileURL().replace(
					"resumes",
					StringUtils.isNotBlank(fileInfo.getTempFolderName()) ? fileInfo.getTempFolderName() : "temp"));
			ResumeParserJsonUtil resume = null;
			try {
				resume = sourceleadResumeParser.getCandidateDetailsFromResume1(file, country, loginOrgLocation.getDateFormat(), fileInfo.getFileName());
				resume.setFileInfo(fileInfo);
				if (resume.getEmail() == null || resume.getEmail().isEmpty() || resume.getEmail().equals("null")) {
					throw new Exception("Email ID missing");
				}
				Candidate c = candidateService.findByCandidateEmail(resume.getEmail(), loginOrgLocation.getId());
				boolean flag = false;
				if (c != null && c.getId() > 0) {
					CandidateResumes cr = candidateResumeService.getCandidateResumeByCandidateId(c.getId());
					if (cr == null || cr.getFileURL() == null || cr.getFileURL().isEmpty())
						flag = true;
				}
				if (c != null && c.getId() > 0 && !flag) {
					fileInfo.setFileURL(fileInfo.getFileURL().replace("resumes", "resumes-existed-"));
					FileUploadUtil.moveFile(staticPath, fileInfo, "resumes-existed-");
					existedList.add(resume);
					continue;
				} else if (flag) {
					CandidateResumeDTO candidateResumeDTO = new CandidateResumeDTO();
					List<FileInfo> fileInfoList1 = new ArrayList<>();
					fileInfoList1.add(fileInfo);
					candidateResumeDTO.setStaticPath(staticPath);
					candidateResumeDTO.setFileInfo(fileInfoList1);
					Long candidateGlobalId = candidateService.getGlobalCandidateIdByCandidateId(c.getId());
					candidateResumeService.storeCandidateResume(candidateResumeDTO, user, candidateGlobalId, null, loginOrgLocation);
					successList.add(resume);
				} else {
					CandidateDTO candidateDto = new CandidateDTO();
					logger.debug("Stroing the candidate with the Email id ==>"+resume.getEmail());
					candidateDto.setEmail(resume.getEmail());
					candidateDto.setCandidateSkills(resume.getSkills() != null ? String.join(", ", resume.getSkills()) : "");
					Iterator<String> it = resume.getPhoneNumbers().iterator();
					while (it.hasNext()) {
						if (candidateDto.getMobilePhone() == null)
							candidateDto.setMobilePhone(it.next());
						else
							candidateDto.setAlternateMobilePhone(it.next());
					}
					candidateDto.setFileName(fileInfo.getFileName());
					candidateDto.setFileContentType(fileInfo.getContentType());
					candidateDto.setFileUrl(fileInfo.getFileURL());
					List<FileInfo> fileInfoList1 = new ArrayList<>();
					fileInfoList1.add(fileInfo);
					candidateDto.setFileInfo(fileInfoList1);
					candidateDto.setStaticPath(staticPath);
					candidateDto.setFirstName(resume.getFirstName() != null ? resume.getFirstName() : "");
					candidateDto.setLastName(resume.getLastName() != null ? resume.getLastName() : "");
					candidateDto.setCandidateCreationType(Constants.IMPORT_CANDIDATE);
					candidateGlobalUtil.storeCandidateGlobal(candidateDto, loginOrgLocation, user);
					successList.add(resume);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				fileInfo.setFileURL(fileInfo.getFileURL().replace("resumes", "resumes-failed-"));
				try {
					moveFile(staticPath, fileInfo, "resumes-failed-");
				} catch (IOException e) {
					e.printStackTrace();
				}
				failedList.add(resume);
			}
		}
		//System.out.println("Succesfully parsed resumes count ==> "+successList.size());
		logger.debug("Succesfully parsed resumes count ==>"+successList.size());
		//System.out.println("Existed resumes count ==> "+existedList.size());
		logger.debug("Existed resumes count ==> "+existedList.size());
		//System.out.println("Failed resumes count ==> "+failedList.size());
		logger.debug("Failed resumes count ==> "+failedList.size());
	}
	

	public static void addToResponse(int type, HttpSession session, String uploadid, ResumeParserJsonUtil resume,
			FileInfo fileInfo, int count, int i, String sourceleadUrl, CandidateService candidateService) {
		saveLogMessage(uploadid, "<br/><br/>"
				+ (type == 1 ? "<span class='a'>Candidate (Resume) added"
						: type == 2 ? "<span class='b'>Candidate Existed, resume attached "
								: type == 0 ? "<span class='c'>Candidate Existed"
										: "<span class='b'>Resume parsing failed")
				+ "</span><br/>File Name: " + fileInfo.getFileName() + "<br/>Name: " + resume.getFirstName() + (resume.getLastName() != null ? resume.getLastName() : "") + "<br/>Email: "
				+ (resume.getEmail() == null || resume.getEmail().isEmpty() || resume.getEmail().equals("null") ? "" : resume.getEmail()), candidateService);
		saveLogMessage(uploadid,
				"<br/><a href='"+ (sourceleadUrl.replace("/app", "")) + fileInfo.getFileURL() + "' target='_blank'>Click here to download the Resume</a>", candidateService);
		if(session != null)
			session.setAttribute("uploadstats" + uploadid, i + " of " + count + " Parsed...");
	}
	
	public static void saveLogMessage(String uploadId, String message, CandidateService candidateService) {
		CandidateImportLog c = new CandidateImportLog();
		c.setMessage(message);
		c.setUploadId(uploadId);
		candidateService.storeCandidateImportLog(c);
	}


}