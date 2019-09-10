package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.BlogCategoryDao;
import com.bis.sourcelead.dao.SourceleadBlogDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadBlogDTO;
import com.bis.sourcelead.entity.BlogCategory;
import com.bis.sourcelead.entity.SourceleadBlogs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.SourceleadBlogService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class SourceleadBlogServiceImpl implements SourceleadBlogService{
	
	@Autowired
	SourceleadBlogDao sourceleadBlogDao;
	
	@Autowired
	BlogCategoryDao blogCategoryDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private CommonUtil commonUtil;

	@Override
	public SourceleadBlogs addBlog(SourceleadBlogDTO sourceleadBlogDTO, User user,String dateFormat) throws Exception {

		SourceleadBlogs sourceleadBlogs = null;
		
		if(StringUtils.isNotBlank(sourceleadBlogDTO.getBlogUUID())) {
			sourceleadBlogs = sourceleadBlogDao.getSourceleadBlogsByBlogUUID(sourceleadBlogDTO.getBlogUUID());
		}else {
			sourceleadBlogs = new SourceleadBlogs();
			sourceleadBlogs.setBlogUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			sourceleadBlogs.setCreatedBy(user);
			sourceleadBlogs.setUpdatedBy(user);
		}
		
		sourceleadBlogs.setCreatedDate(commonUtil.currentDate());
		sourceleadBlogs.setUpdatedDate(commonUtil.currentDate());
		
		if (sourceleadBlogDTO.getFileInfo() != null && sourceleadBlogDTO.getFileInfo().size()>0) {
			FileInfo fileInfo = sourceleadBlogDTO.getFileInfo().get(0);
			if(fileInfo != null) {
				
				sourceleadBlogs.setFileName(fileInfo.getFileName());
				sourceleadBlogs.setFileContentType(fileInfo.getContentType());
				sourceleadBlogs.setImgFileUrl(fileInfo.getFileURL());
				FileUploadUtil.moveFile(sourceleadBlogDTO.getStaticPath(), fileInfo, "sourceleadBlogs");
			}
		}
		
		if(sourceleadBlogDTO.getDescription() != null) {
	//	String formatedText = sourceleadBlogDTO.getDescription().replace("<.*?>", "").replaceAll("\\<.*?\\>", "").replaceAll("<style[\\w\\W]+?</style>", "");
		sourceleadBlogs.setDescription(sourceleadBlogDTO.getDescription());
		}
		
		if(sourceleadBlogDTO.getShortDescription() != null) {
		 sourceleadBlogs.setShortDescription(sourceleadBlogDTO.getShortDescription());
		}
		
		if(StringUtils.isNotBlank(sourceleadBlogDTO.getPublishOn())) {
			sourceleadBlogs.setPublishOn(commonUtil.orgLocationDateFormat(dateFormat, sourceleadBlogDTO.getPublishOn()));
		}else {
			sourceleadBlogs.setPublishOn(new Date());
		}
		
		if(sourceleadBlogDTO.getBlogTitle() != null) {
			sourceleadBlogs.setBlogTitle(sourceleadBlogDTO.getBlogTitle());
//			sourceleadBlogs.setHref(sourceleadBlogDTO.getBlogTitle().toLowerCase().replaceAll(" ", "-").replaceAll(" & ", "-and-"));
			sourceleadBlogs.setHref(sourceleadBlogDTO.getHref().toLowerCase().replaceAll(" ", "-").replaceAll(" & ", "-and-"));
		}
		
		if(sourceleadBlogDTO.getCategoryId() != null) {
			BlogCategory blogCategory = blogCategoryDao.getBlogCategoryById(Long.valueOf(sourceleadBlogDTO.getCategoryId()));
			sourceleadBlogs.setCategoryId(blogCategory);
		}
		
		sourceleadBlogs.setStatus(Constants.RECORD_ACTIVE);
		if(sourceleadBlogDTO.getKeywords() != null) {
		sourceleadBlogs.setKeywords((String.join(",", sourceleadBlogDTO.getKeywords()).replaceAll(",,", ",")).replaceAll("\\s*,\\s*$", ""));
		}
		
		sourceleadBlogs.setBlogStatus(sourceleadBlogDTO.getBlogStatus());
		
		return sourceleadBlogDao.addBlog(sourceleadBlogs);
	}

	@Override
	public SourceleadBlogs getSourceleadBlogsByBlogUUID(String blogUUID) {
		return sourceleadBlogDao.getSourceleadBlogsByBlogUUID(blogUUID);
	}

	@Override
	public SourceleadBlogs getSourceleadBlogsByBlogId(Long blogId) throws Exception {
		return sourceleadBlogDao.getSourceleadBlogsByBlogId(blogId);
	}

	@Override
	public ResponseList<SourceleadBlogDTO> getAllSourceleadBlogsByStatus(SourceleadBlogDTO sourceleadBlogDTO) throws Exception {
		
		ResponseList<SourceleadBlogs> blogsList =  sourceleadBlogDao.getAllSourceleadBlogsByStatus(sourceleadBlogDTO);
		ResponseList<SourceleadBlogDTO> sbList = new ResponseList<>();
		List<SourceleadBlogDTO> sbDTOList = new ArrayList<>();
		for(SourceleadBlogs sb : blogsList.getData()){
			SourceleadBlogDTO sbDTO = new SourceleadBlogDTO();
			sbDTO.setId(sb.getId());
			sbDTO.setBlogUUID(sb.getBlogUUID());
			sbDTO.setBlogTitle(sb.getBlogTitle());
			if(sb.getPublishOn() != null) {
				SimpleDateFormat df = new SimpleDateFormat(sourceleadBlogDTO.getOrgLocationDateFormate());
				String dateStr = df.format(sb.getPublishOn());
			sbDTO.setPublishOnStr(dateStr);
			}
			if(sb.getKeywords() != null) {
			sbDTO.setKeywords(Arrays.asList(sb.getKeywords().split("\\s*,\\s*")));
			}
			
			if(sb.getDescription() != null) {
			String des = sb.getDescription().replaceAll("\\<.*?\\>", "");
			sbDTO.setDescription(des);
			}
			
			if(sb.getShortDescription() != null) {
				
				sbDTO.setShortDescription(sb.getShortDescription());
			}
			if(sb.getCategoryId() != null) {
				sbDTO.setCategoryId(sb.getCategoryId().getCategoryName());
			}
			
			sbDTO.setCreatedBy(sb.getCreatedBy().getUserName());
			sbDTO.setPublishStatus(sb.getBlogStatus());
			/*if(sb.getPublishOn() != null) {
				Date date = sb.getPublishOn();  
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
				DateFormat dateFormatResult = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");
				sbDTO.setPublishOn(dateFormatResult.format(dateFormat.parse(sb.getPublishOn().toString())));
				Date curentDate = new Date();
				if(curentDate.equals(sb.getPublishOn()) || sb.getPublishOn().before(curentDate)) {
					sbDTO.setPublishStatus("Published");
				}else {
					sbDTO.setPublishStatus("Publish");
				}
			}*/
			
			sbDTO.setStatus(sb.getStatus().toString());
			sbDTOList.add(sbDTO);
		}
		
		sbList.setRecordsFiltered(blogsList.getRecordsFiltered());
		sbList.setRecordsTotal(blogsList.getRecordsTotal());
		sbList.setData(sbDTOList);
		return sbList;
		
		
	}

	@Override
	public SourceleadBlogs removeSourceleadBlogByBlogId(Long blogId) throws Exception {
			SourceleadBlogs sourceleadBlogs = sourceleadBlogDao.getSourceleadBlogsByBlogId(blogId);
			sourceleadBlogs.setStatus(Constants.RECORD_INACTIVE);
			sourceleadBlogs =  sourceleadBlogDao.addBlog(sourceleadBlogs);
	        return sourceleadBlogs;
	}
	

	@Override
	public List<SourceleadBlogs> getAllSourceleadBlogsByCategoryId(Long categoryId) throws Exception {
		return sourceleadBlogDao.getAllSourceleadBlogsByCategoryId(categoryId);
	}


	@Override
	public List<SourceleadBlogs> getSourceleadBlogsByCategoryId(Long categoryId) throws Exception {
		return sourceleadBlogDao.getSourceleadBlogsByCategoryId(categoryId);
	}

	@Override
	public SourceleadBlogs saveOrUpdateBlog(SourceleadBlogs sourceleadBlogs) throws Exception {
		return sourceleadBlogDao.addBlog(sourceleadBlogs);
	}

	@Override
	public List<SourceleadBlogs> getAllSourceleadBlogs() throws Exception {
		return sourceleadBlogDao.getAllSourceleadBlogs();
	}

	@Override
	public SourceleadBlogs getSourceleadBlogsByBlogTitle(String blogTitle) throws Exception {
		return sourceleadBlogDao.getSourceleadBlogsByBlogTitle(blogTitle);
	}

	@Override
	public List<SourceleadBlogs> getRelatedSourceleadBlogs(Long blogId) throws Exception {
		return sourceleadBlogDao.getRelatedSourceleadBlogs(blogId);
	}

	@Override
	public List<SourceleadBlogs> getAllSourceleadBlogsByCategoryName(String categoryName) throws Exception {
		return sourceleadBlogDao.getAllSourceleadBlogsByCategoryName(categoryName);
	}

	@Override
	public SourceleadBlogs validateSourceleadBlogTitleByBlogTitleAndBlogId(String blogTitle, String blogUUID)
			throws Exception {
		return sourceleadBlogDao.validateSourceleadBlogTitleByBlogTitleAndBlogId(blogTitle, blogUUID);
	}

	@Override
	public Boolean validateSourceleadBlogTitleByBlogTitle(String blogTitle) throws Exception {
		return sourceleadBlogDao.validateSourceleadBlogTitleByBlogTitle(blogTitle);
	}

	@Override
	public boolean publishBlog(Long id) throws Exception {
		SourceleadBlogs sourceleadBlogs=sourceleadBlogDao.getSourceleadBlogsByBlogId(id);
		if(sourceleadBlogs!=null){
			sourceleadBlogs.setBlogStatus("published");
			sourceleadBlogDao.addBlog(sourceleadBlogs);
			return true;
		}
		
		return false;
	}

}
