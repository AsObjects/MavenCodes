package TestAction;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.Service.ServiceImpl.UserService;
import com.model.MccUser;

@Component("TestFirstActiong")
public class TestFirstActiong {
	
	@Resource
	private UserService us;
	
	public String execute() {
		System.out.println("OK");
		MccUser user=new MccUser();
		user.setPassWord("aaa");
		user.setUserName("cc");
		user.setId(5);
		return "success";
	}
}
