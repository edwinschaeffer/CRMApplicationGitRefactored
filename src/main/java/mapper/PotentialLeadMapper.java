package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import vo.PotentialLead;

@Mapper
public interface PotentialLeadMapper {


	@Select("SELECT * from POTENTIAL_LEADS")
	// MyBatis is case insensitive - Therefore we only have to map the fields have underscores (or named differently)
	@Results (id = "plResultMap", value = {
		      @Result(property = "ageOfBusiness", column = "AGE_OF_BUSINESS"),
		      @Result(property = "convertedDate", column = "CONVERTED_DATE"),
		      @Result(property = "convertedLeadId", column = "CONVERTED_LEAD_ID"),
		      @Result(property = "employeeCount", column = "EMPLOYEE_COUNT"),
		      @Result(property = "potentialLeadLocationLatitude", column = "POTENTIAL_LEAD_LOCATION__LATITUDE__S"),
		      @Result(property = "potentialLeadLocationLongitude", column = "POTENTIAL_LEAD_LOCATION__LONGITUDE__S"),
		      @Result(property = "zipCode", column = "ZIP_CODE")
	})
	public List<PotentialLead> getListOfAllPLs();
	
	// ResultMap uses the id from the @Results map we already created above
	@ResultMap("plResultMap")
	@Select("SELECT * from POTENTIAL_LEADS LIMIT 5")
	public List<PotentialLead> getListOfSomePLs();
	
	@ResultMap("plResultMap")
	@Select("SELECT * from POTENTIAL_LEADS WHERE ID = #{plID}")
	public PotentialLead getPLById(String plID);
	
	@Insert({"<script>", 
		"INSERT INTO POTENTIAL_LEADS", 
		"(ID, COMPANY, CITY)", 
		"VALUES", 
		  "<foreach collection='pl' item='potentialLead' open='(' separator='),(' close=')'>",
		     "(", 
		        "#{potentialLead.id},", 
		        "#{potentialLead.city},",
		        "#{potentialLead.company}", 
		     ")", 
		   "</foreach>",
		 "</script>"})
	public int insertPLBatch(@Param("pl") List<PotentialLead> pl);
	
}
