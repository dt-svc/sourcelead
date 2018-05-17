package com.bis.sourcelead.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TaxDao;
import com.bis.sourcelead.dto.CentralTaxDTO;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.entity.CentralTax;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;
import com.bis.sourcelead.entity.Tds;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.TaxService;

@Service
public class TaxServiceImpl implements TaxService {

	@Autowired
	private TaxDao taxDao;

	@Override
	public List<SourceleadTaxType> getAllTaxesByType(long type) {
		return taxDao.getAllTaxesByType(type);
	}

	@Override
	public List<StateTaxDTO> getStateTaxList(String yearFrequency, long stateId) {
		List<StateTaxDTO> stateTaxList = new LinkedList<>();
		try {
			List<StateTax> stList = taxDao.getStateTaxList(yearFrequency, stateId);
			if (stList != null && !stList.isEmpty()) {
				for (StateTax st : stList) {
					StateTaxDTO stateTaxDTO = new StateTaxDTO();
					stateTaxDTO.setId(st.getId());
					stateTaxDTO.setFinancialYear(st.getFinancialYear());
					stateTaxDTO.setStateId(st.getStates().getId());
					stateTaxDTO.setStateName(st.getStates().getStateName());
					stateTaxDTO.setTaxTypeId(st.getSourceleadTaxType().getId());
					stateTaxDTO.setTaxType(st.getSourceleadTaxType().getDescription());
					stateTaxList.add(stateTaxDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stateTaxList;
	}

	@Override
	public void saveCentralTax(CentralTaxDTO centralTaxDTO, User user, OrgLocations orgLocations) {
		CentralTax centralTax = null;
		if(centralTaxDTO.getId() != null){
			//service
		}else{
			centralTax = new CentralTax();
		}
		centralTax.setCeilvalueesi(centralTaxDTO.getCeilvalueEsi());
		centralTax.setCeilvaluepf(centralTaxDTO.getCeilvaluepf());
		centralTax.setEmployeeesi(centralTaxDTO.getEmployeeEsi());
		centralTax.setEmployeepf(centralTaxDTO.getEmployeepf());
		centralTax.setEmployeresi(centralTaxDTO.getEmployerEsi());
		centralTax.setEmployerpf(centralTaxDTO.getEmployerpf());
		centralTax.setNoofemployees(centralTaxDTO.getNoofemployees());
		//centralTax.setSourceleadTaxType(centralTaxDTO.getSourceleadTaxType());
		centralTax = taxDao.storeCentralTax(centralTax);
		Tds tds = new Tds();
		tds.setAge(centralTaxDTO.getAge());
		tds.setFromamount(centralTaxDTO.getFromamount());
		tds.setToamount(centralTaxDTO.getToamount());
		tds.setPercentage(centralTaxDTO.getPercentage());
		tds.setCentraltaxid(centralTax);
		tds = taxDao.storeTds(tds);
	}

}
