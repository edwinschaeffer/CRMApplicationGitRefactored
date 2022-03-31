package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.PotentialLeadMapper;
import repository.PotentialLeadRepository;
import vo.PotentialLead;

@Service
public class FileUploadDAO {
	
	@Autowired
	private PotentialLeadMapper plm;
	
	@Autowired
	private PotentialLeadRepository plrepo;
	
	public int insertPLlistMyBatisBatch(List<PotentialLead> plList) {
		int status = plm.insertPLBatch(plList);
		PotentialLead pl = plrepo.findByid("PL-142180");
		System.out.println(pl.getCompany());
		return status;
	}
	public void insertPLlistJPABatch(List<PotentialLead> plList) {
		plrepo.saveAll(plList);
		System.out.println("before save");
		PotentialLead pl = plrepo.findByid("PL-142180");
		System.out.println(pl.getCompany());
		for (PotentialLead pl1 : plList) {
			pl1.setCompany(pl1.getCompany().replaceAll("\'", ""));
		}
		plrepo.saveAll(plList);
		System.out.println(plList.get(0).getCompany());
		PotentialLead pl1 = plrepo.findByCompany("TANK TECH");
		System.out.println("after save");
		System.out.println(pl1.getCompany());
	}
	
}
