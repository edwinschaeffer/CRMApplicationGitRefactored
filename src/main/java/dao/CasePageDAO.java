package dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.PotentialLeadMapper;
import repository.PotentialLeadRepository;
import vo.PotentialLead;

@Service
public class CasePageDAO {
	
	@Autowired
	PotentialLeadMapper plm;
	
	@Autowired
	private PotentialLeadRepository plRepo;
	
	// MyBatis ORM
	public PotentialLead getPLByIdMyBatis(String plId) {
        PotentialLead pl = plm.getPLById(plId);
        pl.setCompany(StringUtils.capitalize(pl.getCompany().toLowerCase()));
		return pl;
	}
	
	// JPA ORM
	public PotentialLead getPLByIdJPA(String plId) {
        PotentialLead pl = plRepo.findByid(plId);
        pl.setCompany(StringUtils.capitalize(pl.getCompany().toLowerCase()));
		return pl;
	}
}
