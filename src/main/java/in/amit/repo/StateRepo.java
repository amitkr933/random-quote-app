package in.amit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amit.entity.State;

public interface StateRepo extends JpaRepository<State, Integer> {
	
	public List<State> findByCountryId(Integer cid);
}
