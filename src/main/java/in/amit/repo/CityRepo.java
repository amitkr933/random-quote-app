package in.amit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amit.entity.City;

public interface CityRepo extends JpaRepository<City, Integer> {
	
	public List<City> findByStateId(Integer sid);
}
