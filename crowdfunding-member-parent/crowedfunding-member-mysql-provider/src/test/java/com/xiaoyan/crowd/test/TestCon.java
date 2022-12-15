package com.xiaoyan.crowd.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCon {

    @Autowired
    private DataSource  dataSource;
//
//    @Autowired
//    private MemberPOMapper memberPOMapper;

    private Logger logger= LoggerFactory.getLogger(TestCon.class);

    @Test
    public void testCon() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.debug(connection.toString());
    }

    @Test
    public void testMapper()  {
//      memberPOMapper.insert(new MemberPO(null,"1","x","xx,","mx",1,2,"xx","x",2));
    }

}
