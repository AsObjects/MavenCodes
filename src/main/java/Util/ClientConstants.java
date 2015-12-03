package Util;

public class ClientConstants {
		
		    
		
		public final static String ATTR_CLIENTREQUEST="ClientRequest";
		
		public final static String ATTR_REQUESTMSG="RequestMsg";
		
		
		// 请求消息
		public final static String TAG_REQUEST = "request";
		
		// 响应消息
		public final static String TAG_RESPONSE = "response";
		
		// 消息头
		public final static String TAG_MSG_HEAD = "head";
		
		// 消息体
		public final static String TAG_MSG_BODY = "body";
		
		// 消息体_list节点
		public final static String TAG_MSG_BODY_LIST = "list";
		
		// 消息体_对象节点
		public final static String TAG_MSG_BODY_OBJECT = "object";
		
		// 消息体_string节点
		public final static String TAG_MSG_BODY_STRING = "string";
		
		// 请求消息头_版本验证码
		public final static String TAG_REQUEST_HEAD_CHECKCODE = "checkCode";
		
		// 请求消息头_sessionId
		public final static String TAG_REQUEST_HEAD_SESSIONID = "sessionId";
		
		// 请求消息头_手机号码
		public final static String TAG_REQUEST_HEAD_CLIENTID = "clientId";
		
		// 请求消息头_消息请求操作类型
		public final static String TAG_REQUEST_HEAD_ACTIONTYPE = "actionType";
		
		public final static String TAG_RESP_SERVICEUSER_TAG="serviceUser";
		
		//序列号
		public final static String TAG_HEAD_SERIALNUM ="serialnum";
		
		//短信方式登录成功，返回token
		public final static String TAG_RESP_SMS_LOGINSUCESS_TOKEN="token";
		
		// 消息头列表
		public final static String[] TAG_REQUEST_HEAD_NODES =
		        new String[] {TAG_REQUEST_HEAD_CHECKCODE,
		                TAG_REQUEST_HEAD_SESSIONID, TAG_REQUEST_HEAD_CLIENTID,
		                TAG_REQUEST_HEAD_ACTIONTYPE,TAG_HEAD_SERIALNUM
		
		        };
		
		// 响应消息头_结果响应码
		public final static String TAG_RESPONSE_HEAD_RESULTCODE = "resultCode";
		//响应消息中如果是异常码时，可以返回参数值
		public final static String TAG_RESPONSE_HEAD_RESULTPARAM="resultParam";
		
		// 响应消息头_响应描述信息
		public final static String TAG_RESPONSE_HEAD_RESULTDESC = "resultDesc";
		
		// 响应消息头_结果响应码
		public final static String TAG_RESPONSE_BODY_MSGCODE = "msgCode";
		
		// 响应消息头_响应描述信息
		public final static String TAG_RESPONSE_BODY_MSGDESC = "msgDesc";
		
		
		// 消息头列表
		public final static String[] TAG_RESPONSE_HEAD_NODES =
		        new String[] {TAG_RESPONSE_HEAD_RESULTCODE,
		                TAG_RESPONSE_HEAD_RESULTDESC};
		
		// 分页请求参数
		public final static String TAG_MSG_BODY_PAGESIZE = "pageSize";
		
		// 当前分页ID页号
		public final static String TAG_MSG_BODY_CURRENTPAGEID = "currentPageId";
		
		// 分页数据纪录总条数
		public final static String TAG_MSG_BODY_RECORDCOUNT = "recordCount";
		// 分页总页数
		public final static String TAG_MSG_BODY_PAGECOUNT = "pageCount";
		//当前页第一条纪录序号号
		public final static String TAG_MSG_BODY_CURPAGE_FIRSTROWID="curFirstRowId";
		
		
		
		
		
		// 消息体内容公共标签列表
		public final static String[] TAG_RESPONSE_BODY_NODES =
		        new String[] {TAG_MSG_BODY_PAGESIZE, TAG_MSG_BODY_CURRENTPAGEID
		
		        };
}
