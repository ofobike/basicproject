package www.baidu.com.daoImpl;

import www.baidu.com.dao.GsalesDao;
import www.baidu.com.domain.Gsales;
import www.baidu.com.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @create: 2018/11/22 9:50
 * @version: 1.0
 */
public class GsalesDaoImpl implements GsalesDao {


    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    /**
     * 2.购物结算-向sales表中插入商品数据！
     *
     * @param gSales 售卖商品对象
     * @return boolean
     */
    @Override
    public boolean shoppingSettlement(Gsales gSales) {
        boolean flag = false;
        try {
            String sql = "INSERT INTO GSALES(GID,SID,SNUM) VALUES(?,?,?)";
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, gSales.getGId());
            pstmt.setInt(2, gSales.getSId());
            pstmt.setInt(3, gSales.getSNum());
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(pstmt, conn);
        }
        return flag;
    }


    /**
     * 1.当天卖出的商品
     *
     * @return ArrayList<Gsales> 商品信息,包括 allSum (单种商品的销售总和)
     */
    @Override
    public ArrayList<Gsales> dailyGsales() {
        ArrayList<Gsales> GsalesList = new ArrayList<Gsales>();
        //计算当天每件商品的销售数量
        String sql = "SELECT \n" +
                "  a.gname,\n" +
                "  a.gprice,\n" +
                "  a.gnum,\n" +
                "  b.allSum \n" +
                "FROM\n" +
                "  goods a,\n" +
                "  (SELECT \n" +
                "    gid AS salesid,\n" +
                "    SUM(snum) AS allSum \n" +
                "  FROM\n" +
                "    gsales \n" +
                "  WHERE TO_DAYS(sdate) = TO_DAYS(NOW()) \n" +
                "  GROUP BY gid) AS b\n" +
                "WHERE a.`gid` = b. salesid;";
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String gname = rs.getString("gname");
                double gprice = rs.getDouble("gprice");
                int gnum = rs.getInt("gnum");
                int allsum = rs.getInt("allSum");
                //创建对象
                Gsales gsales = new Gsales(gname, gprice, gnum, allsum);
                GsalesList.add(gsales);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GsalesList;
    }

}
