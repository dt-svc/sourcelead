package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.LetterHeadDTO;
import com.bis.sourcelead.entity.LetterHead;

public interface LetterHeadService {

LetterHead storeLetterHeadData(LetterHeadDTO letterHeadDTO) throws Exception;

}
