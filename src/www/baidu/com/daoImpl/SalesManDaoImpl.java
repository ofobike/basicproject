package www.baidu.com.daoImpl;

import www.baidu.com.dao.SalesManDao;
import www.baidu.com.domain.SalesMan;
import www.baidu.com.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @create: 2018/11/21 15:50
 * @version: 1.0
 */
public class SalesManDaoImpl implements SalesManDao {


    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet set = null;

    /**
     * 1.添加售货员
     *
     * @param sName 用户名
     * @return
     */
    @Override
    public boolean addSalesMan(SalesMan sName) {
        boolean flag = false;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into salesman(sname,spassword) values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, sName.getSname());
            ps.setString(2, sName.getSpassword());
            int num = ps.executeUpdate();
            if (num > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(ps, conn);
        }
        return flag;
    }

    /**
     * 4.删除售货员
     *
     * @param sName 用户名
     * @return
     */
    @Override
    public boolean deleteSalesMan(String sName) {
        boolean flag = false;
        String sql = "delete from salesman where sname =?";
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, sName);
            int num = ps.executeUpdate();
            if (num > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(ps, conn);
        }
        return flag;
    }

    /**
     * 5.模糊查询售货员
     *
     * @param sName 用户名
     * @return ArrayList<SalesMan>
     */
    @Override
    public ArrayList<SalesMan> querySalesMan(String sName) {
        ArrayList<SalesMan> SalesManList = new ArrayList<SalesMan>();
        sName = "%" + sName + "%";
        String sql = "select * from salesman where sname like ?";
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, sName);
            set = ps.executeQuery();
            while (set.next()) {
                int sid = set.getInt("sid");
                String sname = set.getString(2);
                String sPassWord = set.getString(3);

                SalesMan salesMan = new SalesMan(sid, sname, sPassWord);
                SalesManList.add(salesMan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set, ps, conn);
        }
        return SalesManList;
    }

    /**
     * 6.展示所有的售货员
     *
     * @return
     */
    @Override
    public ArrayList<SalesMan> displaySalesMan() {
        ArrayList<SalesMan> salesManList = new ArrayList<>();
        String sql = "SELECT * FROM salesman";
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            set = ps.executeQuery();
            while (set.next()) {
                int gid = set.getInt("sid");
                String gname = set.getString("sname");
                String gpassword = set.getString("spassword");
                SalesMan salesMan = new SalesMan(gid, gname, gpassword);
                //添加到list集合里面
                salesManList.add(salesMan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set, ps, conn);
        }
        return salesManList;
    }

    /**
     * 2.更改售货员界面
     */
    @Override
    public boolean updateSalesMan(int key, SalesMan sName) {
        boolean flag = false;
        switch (key) {
            case 1:

                try {
                    //根据id修改售货员的姓名
                    String sql = "update salesman set sname = ? where sid =?";
                    conn = JDBCUtils.getConnection();
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, sName.getSname());
                    ps.setInt(2, sName.getSid());
                    int update = ps.executeUpdate();
                    if (update > 0) {
                        flag = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(ps, conn);
                }
                break;
            case 2:
                try {
                    //	3.2 更改售货员密码
                    String sqlPrice = "UPDATE SALESMAN SET SPASSWORD=? WHERE SID=?";
                    conn = JDBCUtils.getConnection();
                    ps = conn.prepareStatement(sqlPrice);
                    ps.setString(1, sName.getSpassword());
                    ps.setInt(2, sName.getSid());
                    int num = ps.executeUpdate();
                    if (num > 0) {
                        flag = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(ps, conn);
                }
                break;
            default:
                break;
        }
        return flag;
    }

    /**登陆用户名
     * @param sName 用户名
     * @return
     */
    @Override
    public ArrayList<SalesMan> checkstandLog(String sName) {
        ArrayList<SalesMan> salesManInfo = new ArrayList<SalesMan>();
        String sql = "select sid,spassword from salesman where sname = ? ";
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, sName);
            //执行查询的方法
            set = ps.executeQuery();
            while (set.next()) {
                int gid = set.getInt("sid");
                String gpassword = set.getString("spassword");
                //实例化对象
                SalesMan salesMan = new SalesMan(gid, gpassword);
                salesManInfo.add(salesMan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set, ps, conn);
        }
        return salesManInfo;
    }
}
