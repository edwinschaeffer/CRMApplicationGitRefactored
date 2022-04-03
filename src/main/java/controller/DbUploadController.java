package controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import dao.FileUploadDAO;
import mapper.PotentialLeadMapper;
import service.FileUploadService;

@Controller
public class DbUploadController {

	@Autowired
	private PotentialLeadMapper plMapper;
	
	@Autowired
	private FileUploadService flu;
	
	@Autowired
	private FileUploadDAO fudao;
	
	@PostMapping(value = "/dbUpload")
	public @ResponseBody String uploadSpreadSheet(@RequestParam("file") MultipartFile file) throws IOException {
		fudao.insertPLlistMyBatisBatch(flu.transformExceltoPL(file));
	//	fudao.insertPLlistJPABatch(flu.transformExceltoPL(file));
		return null;
	}
}
