package Dao.DaoImpl;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import Dao.BaseDao;

@Repository
public class BaseDaoImpl implements BaseDao {
	
	@Resource
	private SqlSessionTemplate template;
	
	/*
	 *根据sql和参数查找对象 
	 */
	public Object findObject(String paramName,Object param){
		return template.selectOne(paramName,param);
	}
	
	/*
	 * 根据sql查找对象
	 */
	public Object findObject(String paramName){
		return template.selectOne(paramName);
	}
	
	/*
	 * 根据sql查找List
	 */
	public List findObjectList(String paramName){
		return template.selectList(paramName);
	}
	
	/*
	 * 根据sql和参数查找List
	 */
	public List findObjectList(String paramName,Object param){
		return template.selectList(paramName,param);
	}
	
	/*
	 * 根据sql插入一条数据
	 */
	public int insertObject(String paramName){
		return template.insert(paramName);
	}
	
	/*
	 * 根据sql和参数插入一条数据
	 */
	public int insertObject(String paramName,Object param){
		return template.insert(paramName,param);
	}
	
	/*
	 * 根据sql插入一条数据
	 */
	public int updateObject(String paramName){
		return template.update(paramName);
	}
	
	/*
	 * 根据sql和参数插入一条数据
	 */
	public int updateObject(String paramName,Object param){
		return template.update(paramName,param);
	}
	
	/*
	 * 根据sql删除一条数据
	 */
	public int deleteObject(String paramName){
		return template.delete(paramName);
	}
	
	/*
	 * 根据sql和参数删除一条数据
	 */
	public int deleteObject(String paramName,Object param){
		return template.delete(paramName,param);
	}
}
