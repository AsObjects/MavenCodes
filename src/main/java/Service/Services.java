package Service;

import javax.annotation.Resource;

import org.springframework.stereotype.*;

import Dao.DaoImpl.BaseDaoImpl;

@Service
public class  Services{

	@Resource
	protected BaseDaoImpl bd;
}
