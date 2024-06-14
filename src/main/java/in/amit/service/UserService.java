package in.amit.service;

import java.util.Map;

import in.amit.binding.LoginForm;
import in.amit.binding.RegisterForm;
import in.amit.entity.User;

public interface UserService {
	
	public boolean saveUser(RegisterForm rf);
	public User login(LoginForm formObj);
	public Map<Integer, String> getCountry();
	public Map<Integer, String> getState(Integer countryId);
	public Map<Integer, String> getCity(Integer stateId);
	public boolean getUser(String email);
	public boolean resetPwd(String pwd, Integer userId);

}
