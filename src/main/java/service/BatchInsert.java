package service;

import java.util.List;

import vo.PotentialLead;

public interface BatchInsert {
	void insertBatch(List<PotentialLead> plList);
	void insertBatchPL2Demo(List<PotentialLead> plList);
}
