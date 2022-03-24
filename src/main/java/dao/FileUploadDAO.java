package dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.PotentialLeadMapper;

@Service
public class FileUploadDAO {
	
	@Autowired
	private PotentialLeadMapper plm;
	
}
