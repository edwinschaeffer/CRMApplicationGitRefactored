package IS296.CRMApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dao.HomePageDAO;
import vo.PotentialLead;

@SpringBootTest
class CrmApplicationTests {
	
	@Autowired
	HomePageDAO hpDAO;

	@Test
	void contextLoads() {
		List<PotentialLead> plList = hpDAO.getListOfSomePLsMyBatis();
		assertEquals(5, plList.size());
	}

}
