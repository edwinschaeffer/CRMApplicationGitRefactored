package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vo.PotentialLead;

public interface PotentialLeadRepository extends JpaRepository<PotentialLead, String> {

	PotentialLead findByid(String id);
}
