package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import mapper.PotentialLeadMapper;
import vo.PotentialLead;

@Service
public class HomePageDAO {
	private JdbcTemplate jdbcTemplate;
	@Autowired
	PotentialLeadMapper plm;
	
	// MyBatis ORM 
	public List<PotentialLead> getListOfAllPLsMyBatis() {
		return plm.getListOfAllPLs();
	}
	public List<PotentialLead> getListOfSomePLsMyBatis() {
		return plm.getListOfSomePLs();
	}
	
	// JDBCTemplate is below
	// Spring is smart enough to know so it doesn't have to use Autowire if a
	// Constructor exists.
	public HomePageDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

	public List<Map<String, Object>> getListofAllPLsJDBC() {
		return jdbcTemplate.queryForList("SELECT * from POTENTIAL_LEADS");
	}
	
	public List<PotentialLead> getTransformedList() {
		List<PotentialLead> plList = getListOfAllPLsMyBatis();
		List<PotentialLead> plListFiltered = new ArrayList<PotentialLead>();
		for (PotentialLead pl : plList) {
			if(pl.getWebsite() != null && pl.getWebsite().isBlank()) {
	    		pl.setWebsite("<a href=\"http://www.example.com\" target=\"_blank\">http://www.example.com</a>");
	    	} 
		    pl.setNewLead(false);
		    int idNum = Integer.valueOf(pl.getId().substring(pl.getId().indexOf("-") + 1).trim());
		    // Affects the WHERE clause therefore only the IDs over 141000 are affected. (Reason for the Blue markers)
		    if(idNum < 141000) {
		    	plListFiltered.add(pl);
		    }
		}
		plm.updatePLBatch(plListFiltered);
		return plListFiltered;
	}

	public List<PotentialLead> getListofAllPLsRowMapper() {

		return jdbcTemplate.query("SELECT * from POTENTIAL_LEADS", new RowMapper<PotentialLead>() {

			@Override
			public PotentialLead mapRow(ResultSet rs, int rowNumber) throws SQLException {
				PotentialLead pl = new PotentialLead();
				pl.setId(rs.getString("ID"));
				pl.setCompany(rs.getString("COMPANY"));
				return pl;
			}
		});

	}
	
}
