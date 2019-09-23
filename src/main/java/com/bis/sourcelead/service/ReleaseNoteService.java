package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ReleaseNoteDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReleaseNote;

public interface ReleaseNoteService {

	ReleaseNote saveReleaseNote(ReleaseNote releaseNote) throws Exception;

	ReleaseNote getReleaseNoteByUUId(String releaseNoteUUID) throws Exception;
	
	List<ReleaseNote>  getAllReleaseNote() throws Exception;
	
	String  saveReleaseNoteDTO(ReleaseNoteDTO  releaseNoteDTO, OrgLocations orgLocations) throws Exception;

	Boolean validateReleaseNoteName(String releaseNoteName);

	ReleaseNote validateReleaseNoteNameByReleaseUUIDAndReleaseNoteName(String releaseNoteName, String releaseUUID);
}
