import com.xiaoyan.crowd.mapper.AdminMapper;
import com.xiaoyan.crowd.mapper.RoleMapper;
import com.xiaoyan.crowd.mvc.pojo.Role;
import com.xiaoyan.crowd.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

// 指定 Spring 给 Junit 提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 配置文件的注解
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class testCon {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

//    @Test
//    public void testDataSource() throws SQLException {
//        // 1.通过数据源对象获取数据源连接
//        Connection connection = dataSource.getConnection();
//        // 2.打印数据库连接
//        System.out.println(connection);
//    }

//    @Test
// public  void testSM(){
//        int insert = adminMapper.insert(new Admin(null, "18474l", "cjv", "kcmvmv", "kcnv"));
//        System.out.println(insert);
//
//    }
//
    @Test
    public void testService(){
//        String username="roo";
//        String acc="ocv";
//        AdminExample adminExample = new AdminExample();
//        adminExample.createCriteria().andUserNameLike("%"+username+"%");
//        adminExample.or().andEmailLike("%"+acc);
//
//        List<Admin> admins = adminMapper.selectByExample(adminExample);
//        admins.forEach(System.out::println);


//        for (int i = 0; i < 250; i++) {
//            adminMapper.insert(new Admin(null,"accountName"+i,"username"+i,
//                    "email"+i,"createtime"+i,"psw"+i));
//        }


        for (int i = 0; i < 180; i++) {
            roleMapper.insert(new Role(null,"abab"+i));
        }

    }

}
