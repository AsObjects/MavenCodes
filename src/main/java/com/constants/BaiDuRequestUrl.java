package com.constants;

public interface BaiDuRequestUrl {

	/*
	 * 百度公共请求
	 */
	String GET_COOKIES="http://www.baidu.com";
	
	String GET_TOKEN="https://passport.baidu.com/v2/api/?getapi&class=login&tpl=pp&tangram=true";
	
	String LOGIN_BAIDU="https://passport.baidu.com/v2/api/?login";
	//这个链接不同于其他：http://tieba.baidu.com后面要加具体登录的帖子的ID
	String LOGIN_SPECIFIC_NOTE="http://tieba.baidu.com";
	
	String SEND_NOTE="http://tieba.baidu.com/f/commit/post/add";
	
	/*
	 * java吧
	 */
	String LOGIN_JAVA="http://tieba.baidu.com/f?kw=java";
	
	/*
	 * 师院吧
	 */
	String LOGIN_MDJNU="http://tieba.baidu.com/f?kw=牡丹江师范学院";
	
	/*
	 * 镜中花_水中月吧
	 */
	String LOGIN_JHSY="http://tieba.baidu.com/f?kw=镜中花_水中月";
}
