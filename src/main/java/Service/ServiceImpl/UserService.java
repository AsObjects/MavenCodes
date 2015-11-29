package Service.ServiceImpl;

import org.springframework.stereotype.Service;

import Entity.User;
import Service.Services;

@Service
public class UserService extends Services{
	/*
	 * 注册
	 */
	public void regist(User user){
		super.bd.insertObject("Cys_User.addUserInfo",user);
	}
	/*
	 * 删除用户信息
	 */
	public void deleteUser(User user){
		super.bd.deleteObject("Cys_User.deleteUserInfo",user);
	}
}
