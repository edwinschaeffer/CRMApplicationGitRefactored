package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.CasePageDAO;

@Controller
public class CasePageController {

	@Autowired 
	private CasePageDAO cpDAO;
	
	@GetMapping("/casePage")
	public String getCasePage() {
		return "casePage.html";
	}
	
	@GetMapping("/callServer")
	public @ResponseBody String getPLbyId(@RequestParam String plId) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		return om.writeValueAsString(cpDAO.getPLByIdMyBatis(plId));
	}
	@GetMapping("/callServerJPA")
	public @ResponseBody String getPLbyIdJPA(@RequestParam String plId) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		return om.writeValueAsString(cpDAO.getPLByIdJPA(plId));
	}
	@GetMapping("/dbUpload")
	public String getDbUploadPage() {
		return "uploadSpreadsheet.html";
	}
	
	@GetMapping("/map")
	public String getGoogleMap(Model model) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
        model.addAttribute("plList", om.writeValueAsString(cpDAO.getAllPLs()));
		return "map.html";
	}
}
