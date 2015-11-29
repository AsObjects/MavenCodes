package TestAction;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;


import Entity.User;
import Service.ServiceImpl.UserService;

@Component("TestFirstActiong")
public class TestFirstActiong {
	
	@Resource
	private UserService us;
	
	public String execute() {
		System.out.println("OK");
		User user=new User();
		user.setPassWord("aaa");
		user.setUserName("cc");
		us.regist(user);
		user.setId(5);
		us.deleteUser(user);
		return "success";
	}
}
