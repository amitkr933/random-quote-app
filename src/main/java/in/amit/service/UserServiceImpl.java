package in.amit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.amit.binding.LoginForm;
import in.amit.binding.RegisterForm;
import in.amit.entity.City;
import in.amit.entity.Country;
import in.amit.entity.State;
import in.amit.entity.User;
import in.amit.properties.AppProps;
import in.amit.repo.CityRepo;
import in.amit.repo.CountryRepo;
import in.amit.repo.StateRepo;
import in.amit.repo.UserRepo;
import in.amit.util.EmailUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo urepo;
	
	@Autowired
	private CountryRepo crepo;
	
	@Autowired
	private StateRepo srepo;
	
	@Autowired
	private CityRepo cityRepo;
	
	@Autowired
	private EmailUtils mail;
	
	@Autowired
	private AppProps appProps;

	@Override
	public boolean saveUser(RegisterForm formObj) {
		User userEntity = new User();
		formObj.setPwd(generateRandomPwd());
		formObj.setPwdUpdated("NO");
		BeanUtils.copyProperties(formObj, userEntity);
		User user = urepo.save(userEntity);
		if(user != null) {
			String sub = appProps.getMessages().get("regEmailSubject");
			String body = "<h2> Your password is :</h2>" +user.getPwd();
			mail.sendMail(user.getEmail(), sub, body);
			return true;
		}
		
		return false;
	}

	private String generateRandomPwd() {
		String alphanumericCharacters = "qazwsxQAZWSXyhntgbrfvedcyhnujmikolUJMFGHJKRTYUIVB7894523@#$%&*";
		Random random = new Random();
		StringBuilder randomString = new StringBuilder(6);
		for(int i=1 ; i<=6; i++) {
			int randomIndex = random.nextInt(alphanumericCharacters.length()-1);
			randomString.append(alphanumericCharacters.charAt(randomIndex));
		}
		return randomString.toString();
	}

	@Override
	public User login(LoginForm formObj) {
		User user = urepo.findByEmailAndPwd(formObj.getEmail(), formObj.getPwd());
		return user;
	}

	@Override
	public Map<Integer, String> getCountry() {
		List<Country> all = crepo.findAll();
		Map<Integer, String> map = new HashMap<>();
		all.forEach(e -> map.put(e.getCountryId(), e.getCountryName()));
		return map;
	}

	@Override
	public Map<Integer, String> getState(Integer countryId) {
		List<State> list = srepo.findByCountryId(countryId);
		Map<Integer, String> map = new HashMap<>();
		list.forEach(e -> map.put(e.getStateId(), e.getStateName()));
		return map;
	}

	@Override
	public Map<Integer, String> getCity(Integer stateId) {
		List<City> list = cityRepo.findByStateId(stateId);
		Map<Integer, String> map = new HashMap<>();
		list.forEach(e -> map.put(e.getCityId(), e.getCityName()));
		return map;
	}

	@Override
	public boolean getUser(String email) {
		User user = urepo.findByEmail(email);
		if(user==null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean resetPwd(String pwd, Integer userId) {
		Optional<User> obj = urepo.findById(userId);
		if(obj.isPresent()) {
			User user = obj.get();
			user.setPwd(pwd);
			user.setPwdUpdated("YES");
			urepo.save(user);
			return true;
		}
		
		return false;
	}

}
