package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.FileUploadDAO;
import vo.PotentialLead;

@Service
public class FileUploadService {

	@Autowired
	private FileUploadDAO flDAO;
	
	public void transformExceltoPL(MultipartFile file) throws IOException {
		InputStream is = file.getInputStream();
		Workbook wb = WorkbookFactory.create(is);
		Sheet sheet = wb.getSheet("extradata");
		DataFormatter formatter = new DataFormatter();
		List<PotentialLead> plList = new ArrayList<PotentialLead>();
		
		for (int i = 0; i < 2; i++) {
			Row row = sheet.getRow(i);
			PotentialLead pl = new PotentialLead();
			for (int j = 0; j < 17; j++) {
				Cell cell = row.getCell(j);
				String cellValue = formatter.formatCellValue(cell).trim();
				if (cellValue == null) {
					continue;
				}
				System.out.println(cellValue);
			}
		}
	}
}
