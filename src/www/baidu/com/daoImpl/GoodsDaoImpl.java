package www.baidu.com.daoImpl;

import www.baidu.com.dao.GoodsDao;
import www.baidu.com.domain.Goods;
import www.baidu.com.tools.ScannerChoices;
import www.baidu.com.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * @create: 2018/11/21 9:54
 * @version: 1.0
 */
public class GoodsDaoImpl implements GoodsDao {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet set = null;

    @Override
    public boolean addGoods(Goods goods) {
        boolean flag = false;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "INSERT INTO GOODS(GNAME,GPRICE,GNUM) VALUES(?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, goods.getGname());
            ps.setDouble(2, goods.getGprice());
            ps.setInt(3, goods.getGnum());
            int num = ps.executeUpdate();
            if (num > 0) {
                //插入数据成功
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(ps, connection);
        }
        return flag;
    }

    /**
     * 2.更改商品信息到数据库goods表
     *
     * @param key   选择要更改商品信息
     * @param goods 商品对象
     * @return boolean
     */
    @Override
    public boolean updateGoods(int key, Goods goods) {
        boolean flag = false;
        switch (key) {
            case 1:
                try {
                    //更修商品的名称
                    String sqlName = "UPDATE GOODS SET GNAME=? WHERE GID=?";
                    connection = JDBCUtils.getConnection();
                    ps = connection.prepareStatement(sqlName);
                    ps.setString(1, goods.getGname());
                    ps.setInt(2, goods.getGid());
                    int num = ps.executeUpdate();
                    if (num > 0) {
                        flag = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(ps, connection);
                }
                break;
            case 2:
                try{
                    //	key=2,更改商品价格
                    String sqlPrice = "UPDATE GOODS SET GPRICE=? WHERE GID=?";
                    connection = JDBCUtils.getConnection();
                    ps = connection.prepareStatement(sqlPrice);
                    ps.setDouble(1,goods.getGprice());
                    ps.setInt(2,goods.getGid());
                    int num = ps.executeUpdate();
                    if (num >0){
                        flag = true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    JDBCUtils.close(ps,connection);
                }
                break;
            case 3:
                try {
                    //	key=3,更改商品数量
                    String sqlNum = "update  goods set gnum = ? where gid = ?";
                    connection = JDBCUtils.getConnection();
                    ps = connection.prepareStatement(sqlNum);
                    ps.setInt(1, goods.getGnum());
                    ps.setInt(2, goods.getGid());
                    int num = ps.executeUpdate();
                    if (num > 0) {
                        flag = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(ps, connection);
                }
                break;
            default:
                break;
        }

        return flag;
    }

    /**
     * 从数据库goods表中-刪除商品
     *
     * @param gid 商品编号
     * @return boolean
     */
    @Override
    public boolean deleteGoods(int gid) {
        boolean flag = false;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "delete from goods where gid = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, gid);
            int num = ps.executeUpdate();
            if (num > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set, ps, connection);
        }
        return flag;
    }

    /**
     * 4.查询商品信息
     *
     * @param key 查询方式
     * @return ArrayList<Goods>
     */
    @Override
    public ArrayList<Goods> queryGoods(int key) throws SQLException {

        ArrayList<Goods> goodsList = new ArrayList<>();

        switch (key) {
            case 1:

                try {
                    connection = JDBCUtils.getConnection();
                    //	key=1商品 数量 升序查询
                    String sqlGnum = "SELECT * FROM goods ORDER BY gnum desc";
                    ps = connection.prepareStatement(sqlGnum);
                    set = ps.executeQuery();
                    while (set.next()) {
                        int gid = set.getInt("gid");
                        String gname = set.getString("gname");
                        double gprice = set.getDouble("gprice");
                        int gnum = set.getInt("gnum");

                        Goods goods = new Goods(gid, gname, gprice, gnum);
                        goodsList.add(goods);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(set, ps, connection);
                }
                break;
            case 2:

                try {
                    connection = JDBCUtils.getConnection();
                    //	key=2商品 价格 升序查询
                    String sqlGprice = "SELECT * FROM goods order BY gprice DESC ";
                    ps = connection.prepareStatement(sqlGprice);
                    set = ps.executeQuery();
                    while (set.next()) {
                        int gid = set.getInt("gid");
                        String gname = set.getString(2);
                        double gprice = set.getDouble(3);
                        int gnum = set.getInt(4);

                        Goods goods = new Goods(gid, gname, gprice, gnum);
                        goodsList.add(goods);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(set, ps, connection);
                }
                break;
            case 3:
                try {
                    connection = JDBCUtils.getConnection();
                    //	key=3商品 关键字 查询
                    String nameGet = ScannerChoices.ScannerInfoString();
                    nameGet = "%" + nameGet + "%";
                    String sqlGname = "SELECT * FROM GOODS WHERE GNAME LIKE ?";
                    ps = connection.prepareStatement(sqlGname);
                    ps.setString(1, nameGet);
                    set = ps.executeQuery();
                    while (set.next()) {
                        int gid = set.getInt("gid");
                        String gname = set.getString("gname");
                        double gprice = set.getDouble("gprice");
                        int gnum = set.getInt("gnum");

                        Goods goods = new Goods(gid, gname, gprice, gnum);
                        goodsList.add(goods);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(set, ps, connection);
                }
                break;
            default:
                break;
        }

        return goodsList;
    }

    /**
     * 5.显示所有商品信息
     *
     * @return ArrayList<Goods>
     */
    @Override
    public ArrayList<Goods> displayGoods() {
        ArrayList<Goods> goodsList = new ArrayList<Goods>();
        try {
            connection = JDBCUtils.getConnection();
            String sql = "SELECT * FROM GOODS";
            ps = connection.prepareStatement(sql);
            set = ps.executeQuery();

            while (set.next()) {
                int gid = set.getInt(1);
                String gname = set.getString(2);
                double gprice = set.getDouble("gprice");        //双引号+主键名,也可用数字表示.
                int gnum = set.getInt(4);

                Goods goods = new Goods(gid, gname, gprice, gnum);    //创建Goods对象，并赋值.
                goodsList.add(goods);                            //添加信息到动态数组中.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set, ps, connection);
        }
        return goodsList;

    }


}
