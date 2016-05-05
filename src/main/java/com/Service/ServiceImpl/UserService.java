package com.Service.ServiceImpl;

import org.springframework.stereotype.Service;

import com.Service.Services;
import com.model.MccUser;

@Service
public class UserService extends Services{
	/*
	 * 注册
	 */
	public void regist(MccUser user){
		super.bd.insertObject("Cys_User.addUserInfo",user);
	}
	/*
	 * 删除用户信息
	 */
	public void deleteUser(MccUser user){
		super.bd.deleteObject("Cys_User.deleteUserInfo",user);
	}
}
