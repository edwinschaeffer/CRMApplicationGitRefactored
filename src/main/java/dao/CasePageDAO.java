package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import mapper.PotentialLeadMapper;
import repository.PotentialLeadRepository;
import service.MyBatisBatchInsert;
import vo.PotentialLead;

@Service
public class CasePageDAO {
	
	@Autowired
	PotentialLeadMapper plm;
	
	@Autowired
	private PotentialLeadRepository plRepo;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private MyBatisBatchInsert myBBatchInsert;
	
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
	
	public List<PotentialLead> savePLtoDBandReturnJPA(List<PotentialLead> plList) {
		plRepo.saveAll(plList);
		return plRepo.findAll();
	}
	
	public List<PotentialLead> savePLtoDBandReturnMyBatis(List<PotentialLead> plList) {
		myBBatchInsert.insertBatchPL2Demo(plList);
		return plm.getListOfAllPLs();
	}
	
	public List<PotentialLead> getPLByIdJdbcTemplate(String plId) {
		String sql = "SELECT Id, Company, City, EMPLOYEE_COUNT WHERE Id = ?";
		List<PotentialLead> plList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<PotentialLead>(PotentialLead.class), plId);
		return plList;	
	}
	
	public List<PotentialLead> savePLtoDBandReturnJDBCTemplate(List<PotentialLead> plList) {
		jdbcTemplate.batchUpdate(
				"insert into POTENTIAL_LEADS (ID, CITY, STATE) values(?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, plList.get(i).getId());
						ps.setString(2, plList.get(i).getCity());
						ps.setString(3, plList.get(i).getState());
					}

					public int getBatchSize() {
						return plList.size();
					}

				});
		return plRepo.findAll();
	}
	// test case similar to Sec 2 Problem 3 - Change "AREA" column from "MID-AMERICA" to MA
	public List<PotentialLead> updatePLtoDBandReturnJPA() { 
		    List<PotentialLead> plList = plRepo.findAll();
			for (PotentialLead pl : plList) {
				if(pl.getArea().contains("MID-AMERICA")) {
					pl.setArea("MA");
				}
				/*switch(pl.getArea()) {
					case "MID-AMERICA": 
					    pl.setArea("MA");	
					    break;
				} */ 
			}
			plRepo.saveAll(plList);
			return plRepo.findAll();
	}
	public List<PotentialLead> updatePLtoDBandReturnJDBCTemplate() { 
		List<PotentialLead> plList = plRepo.findAll();
		jdbcTemplate.batchUpdate(
				"UPDATE POTENTIAL_LEADS SET AREA = ? WHERE AREA = ? AND ID = ?",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "MA");
						ps.setString(2, "MID-AMERICA");
						ps.setString(3,  plList.get(0).getId());
					}

					public int getBatchSize() {
						return plList.size();
					}

				});
		return plRepo.findAll();
	}
	
	public List<PotentialLead> getAllPLs() {
		return plRepo.findAll();
	}
}
