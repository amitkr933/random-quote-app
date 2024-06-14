package in.amit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.amit.entity.Country;

public interface CountryRepo extends JpaRepository<Country, Integer> {

}
