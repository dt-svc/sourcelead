package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class ClientProjectDTO implements Serializable{

	private static final long serialVersionUID = 1L; 
	
	private String levelMaxRowNumber;
	
	private String levelMaxValue;
	
    private String level1Manager;
	
	private String level2Manager;
	
	private String clientProject;
	
	private List<ClientProjectLevelsDTO> clientProjectLevel;

	public String getLevelMaxRowNumber() {
		return levelMaxRowNumber;
	}

	public void setLevelMaxRowNumber(String levelMaxRowNumber) {
		this.levelMaxRowNumber = levelMaxRowNumber;
	}

	public String getLevelMaxValue() {
		return levelMaxValue;
	}

	public void setLevelMaxValue(String levelMaxValue) {
		this.levelMaxValue = levelMaxValue;
	}

	public String getLevel1Manager() {
		return level1Manager;
	}

	public void setLevel1Manager(String level1Manager) {
		this.level1Manager = level1Manager;
	}

	public String getLevel2Manager() {
		return level2Manager;
	}

	public void setLevel2Manager(String level2Manager) {
		this.level2Manager = level2Manager;
	}

	public String getClientProject() {
		return clientProject;
	}

	public void setClientProject(String clientProject) {
		this.clientProject = clientProject;
	}

	public List<ClientProjectLevelsDTO> getClientProjectLevel() {
		return clientProjectLevel;
	}

	public void setClientProjectLevel(List<ClientProjectLevelsDTO> clientProjectLevel) {
		this.clientProjectLevel = clientProjectLevel;
	}
}
