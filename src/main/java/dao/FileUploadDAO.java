package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.PotentialLeadMapper;
import repository.PotentialLeadRepository;
import service.MyBatisBatchInsert;
import vo.PotentialLead;

@Service
public class FileUploadDAO {
	
	@Autowired
	private PotentialLeadMapper plm;
	
	@Autowired
	private PotentialLeadRepository plrepo;
	
	@Autowired
	private MyBatisBatchInsert mybb;
	
	// This is not an official Batch!
	public int insertPLlistMyBatisMultiInsert(List<PotentialLead> plList) {
        long start = System.nanoTime();
        int status = plm.insertPLBatch(plList);
        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;
        System.out.println(elapsedTimeInSecond + " seconds");
		PotentialLead pl = plrepo.findByid("PL-142180");
		System.out.println(pl.getCompany());
		return status;
	}
	
	public void insertPLlistMyBatisBatch(List<PotentialLead> plList) {
        long start = System.nanoTime();
        mybb.insertBatch(plList);
        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;
        System.out.println(elapsedTimeInSecond + " seconds");
	}
	public void insertPLlistJPABatch(List<PotentialLead> plList) {
		plrepo.saveAll(plList);
		System.out.println("before save");
		PotentialLead pl = plrepo.findByid("PL-142180");
		System.out.println(pl.getCompany());
		for (PotentialLead pl1 : plList) {
			pl1.setCompany(pl1.getCompany().replaceAll("\'", ""));
		}
        long start = System.nanoTime();
		plrepo.saveAll(plList);
        long end = System.nanoTime();
        long elapsedTime = end - start;
        double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;
        System.out.println(elapsedTimeInSecond + " seconds");
		System.out.println(plList.get(0).getCompany());
		PotentialLead pl1 = plrepo.findByCompany("TANK TECH");
		System.out.println("after save");
		System.out.println(pl1.getCompany());
	}
	
}
