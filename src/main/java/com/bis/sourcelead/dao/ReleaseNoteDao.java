package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ReleaseNote;

public interface ReleaseNoteDao {
	
	/**
	 * @author Laxman
	 * @param department
	 * @return
	 * @throws Exception
	 */
	ReleaseNote saveReleaseNote(ReleaseNote releaseNote) throws Exception;

	ReleaseNote getReleaseNoteByUUId(String releaseNoteUUID) throws Exception;
	
	List<ReleaseNote>  getAllReleaseNote() throws Exception;
	
	Boolean validateReleaseNoteName(String releaseNoteName);
	
	ReleaseNote validateReleaseNoteNameByReleaseUUIDAndReleaseNoteName(String releaseNoteName, String releaseUUID);

}
