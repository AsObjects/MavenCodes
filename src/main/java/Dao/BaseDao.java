package Dao;

import java.util.List;

public interface BaseDao<T> {

	/*
	 *根据sql和参数查找对象 
	 */
	public Object findObject(String paramName,Object param);
	
	/*
	 * 根据sql查找对象
	 */
	public Object findObject(String paramName);
	
	/*
	 * 根据sql查找List
	 */
	public List findObjectList(String paramName);
	
	/*
	 * 根据sql和参数查找List
	 */
	public List findObjectList(String paramName,Object param);
	
	/*
	 * 根据sql插入一条数据
	 */
	public int insertObject(String paramName);
	
	/*
	 * 根据sql和参数插入一条数据
	 */
	public int insertObject(String paramName,Object param);
	
	/*
	 * 根据sql插入一条数据
	 */
	public int updateObject(String paramName);
	
	/*
	 * 根据sql和参数插入一条数据
	 */
	public int updateObject(String paramName,Object param);
	
	/*
	 * 根据sql删除一条数据
	 */
	public int deleteObject(String paramName);
	
	/*
	 * 根据sql和参数删除一条数据
	 */
	public int deleteObject(String paramName,Object param);
	
}
