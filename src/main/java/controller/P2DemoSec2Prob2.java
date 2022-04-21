package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.CasePageDAO;
import vo.PotentialLead;

@Controller
public class P2DemoSec2Prob2 {
     
	@Autowired
	private CasePageDAO cpDAO;

	
	@GetMapping("id/{id}")
	public String getPLById(Model model, @PathVariable String id) {
        model.addAttribute("hdList", cpDAO.getPLByIdJdbcTemplate(id));
		return "p2DemoSec2Prob2.html";
	}
	
	@GetMapping("jpaId/{id}")
	public String getPLByIdJPA(Model model, @PathVariable String id) {
		model.addAttribute("hdList", cpDAO.getPLByIdJPA(id));
		return "p2DemoSec2Prob2.html";
	}
	
	@PostMapping("loadDisburse") 
	public @ResponseBody List<PotentialLead> loadPLRecords(@RequestBody final List<PotentialLead> plList) {
		return cpDAO.savePLtoDBandReturnJPA(plList);
	}
	
	@PostMapping("loadDisburseMyBatis") 
	public @ResponseBody List<PotentialLead> loadPLRecordsMyBatis(@RequestBody final List<PotentialLead> plList) {
		return cpDAO.savePLtoDBandReturnMyBatis(plList);
	}
	@PostMapping("loadDisburseJdbcTemplate")
	public @ResponseBody List<PotentialLead> loadPLRecordsJdbcTemplate(@RequestBody final List<PotentialLead> plList) {
		return cpDAO.savePLtoDBandReturnJDBCTemplate(plList);
	}
	@GetMapping("P2DemoSec2P3JdbcTemplate") 
	public String updatePLBatchJdbcTemplate(Model model) {
		model.addAttribute("hdList", cpDAO.updatePLtoDBandReturnJDBCTemplate());
		return "p2DemoSec2Prob2.html";
	}
	@GetMapping("P2DemoSec2P3JPA") 
	public String updatePLBatchJPA(Model model) {
		model.addAttribute("hdList", cpDAO.updatePLtoDBandReturnJPA());
		return "p2DemoSec2Prob2.html";
	}
}
