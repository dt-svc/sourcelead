package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.annotations.Required;

public class AssetCategoryDTO extends SearchDTO implements Serializable {
		
		@Required(desc="category name")
		private String categoryName;
		
		@CleanHtml(escape=false)
		private String description;

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
}
