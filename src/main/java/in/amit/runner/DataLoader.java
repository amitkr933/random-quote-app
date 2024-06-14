package in.amit.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.amit.entity.City;
import in.amit.entity.Country;
import in.amit.entity.State;
import in.amit.repo.CityRepo;
import in.amit.repo.CountryRepo;
import in.amit.repo.StateRepo;

@Component
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	private CountryRepo countryRepo;
	
	@Autowired
	private StateRepo stateRepo;
	
	@Autowired
	private CityRepo cityRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		countryRepo.deleteAll();
		stateRepo.deleteAll();
		cityRepo.deleteAll();
		
		Country country1 = new Country(1, "India");
		Country country2 = new Country(2, "USA");
		Country country3 = new Country(3, "UK");
		List<Country> countries = Arrays.asList(country1, country2, country3);
		countryRepo.saveAll(countries);
		
		State state1 = new State(1, "Bihar", 1);
		State state2 = new State(2, "Rajasthan", 1);
		State state3 = new State(3, "Texas", 2);
		State state4 = new State(4, "California", 2);
		State state5 = new State(5, "London", 3);
		State state6 = new State(6, "Manchester", 3);
		List<State> states = Arrays.asList(state1, state2, state3, state4, state5, state6);
		stateRepo.saveAll(states);
		
		City c1 = new City(1, "Patna", 1);
		City c2 = new City(2, "Gaya", 1);
		City c3 = new City(3, "Jaipur", 2);
		City c4 = new City(4, "Kota", 2);
		City c5 = new City(5, "Texas1", 3);
		City c6 = new City(6, "Texas2", 3);
		City c7 = new City(7, "California1", 4);
		City c8 = new City(8, "California2", 4);
		City c9 = new City(9, "London1", 5);
		City c10 = new City(10, "London2", 5);
		City c11 = new City(11, "Manchester1", 6);
		City c12 = new City(12, "Manchester2", 6);
		List<City> cities = Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12);
		cityRepo.saveAll(cities);

	}

}
