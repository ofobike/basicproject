package www.baidu.com.test;

import com.alibaba.druid.sql.visitor.SQLASTOutputVisitorUtils;
import org.junit.Test;
import www.baidu.com.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @create: 2018/11/21 9:49
 * @version: 1.0
 */
public class test {

    @Test
    public void testConnection() throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        System.out.println(connection);
    }
}
