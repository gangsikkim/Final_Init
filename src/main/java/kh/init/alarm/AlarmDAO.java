package kh.init.alarm;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlarmDAO {
	
	@Autowired
	private SqlSessionTemplate jdbc;

}
