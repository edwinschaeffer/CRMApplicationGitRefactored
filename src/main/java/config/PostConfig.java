package config;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Component;

import repository.PotentialLeadRepository;
import vo.PotentialLead;

@Component
public class PostConfig {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PotentialLeadRepository plRepo;
	
	@PostConstruct
	public void init() {
		jdbcTemplate.execute("ALTER TABLE POTENTIAL_LEADS ADD newLead Boolean");
		// Don't necessarily have to do it this way. We're demo-ing a JdbcTemplate Batch Update
		List<PotentialLead> plList = plRepo.findAll();
		String sql = "UPDATE POTENTIAL_LEADS SET ID = ?, newLead = ?, WEBSITE = ? WHERE ID = ?";
		int[][] updatePotentialLeadsResult = jdbcTemplate.batchUpdate(sql, plList, 50, 
				new ParameterizedPreparedStatementSetter<PotentialLead>() {

			    @Override
				public void setValues(PreparedStatement ps, PotentialLead argument) throws SQLException {
			    	if(argument.getWebsite() != null && argument.getWebsite().isBlank()) {
			    		ps.setString(3, "<a href=\"http://www.example.com\" target=\"_blank\">http://www.example.com</a>");
			    	} else {
			    		ps.setString(3, argument.getWebsite());
			    	}
			        ps.setString(1, argument.getId().replaceAll(" ", ""));
				    ps.setBoolean(2, true);
				    int idNum = Integer.valueOf(argument.getId().substring(argument.getId().indexOf("-") + 1).trim());
				    // Affects the WHERE clause therefore only the IDs over 141000 are affected. (Reason for the Blue markers)
				   //(idNum > 141000) {
				    	ps.setString(4, argument.getId());
				   // }
				}
		});
	}
}
