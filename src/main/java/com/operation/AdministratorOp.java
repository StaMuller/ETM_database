package com.operation;

import com.bean.Employee;
import com.mapper.AdministratorMapper;
import com.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

public class AdministratorOp {

    SqlSession sqlSession = MybatisUtils.getSqlSession();
    AdministratorMapper administratorMapper = sqlSession.getMapper(AdministratorMapper.class);

    public void addEmployee(Employee employee){
        administratorMapper.addEmployee(employee);
        sqlSession.commit();
    }

    public void close(){
        sqlSession.close();
    }
}
