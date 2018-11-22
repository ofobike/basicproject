package www.baidu.com.tools;

import www.baidu.com.daoImpl.GoodsDaoImpl;
import www.baidu.com.domain.Goods;
import www.baidu.com.domain.SalesMan;
import www.baidu.com.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @create: 2018/11/21 14:22
 * @version: 1.0
 */
public class QueryPrint {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet set = null;

    /**
     * 模糊查询并陈列查询信息函数小工具
     *
     * @param oper 调用者
     * @return 查询到的信息的gid, 如果返回值等于-1，则代表查询异常。
     */
    public static int query(String oper) throws Exception {
        int gid = -1;
        //键盘获取商品的名称
        String shopping = ScannerChoices.ScannerInfoString();
        //根据键盘获取的商品名字調用 精确查询函数，確定用戶所要操作的数据
        ArrayList<Goods> goodsList = new QueryPrint().queryGoodsKey(-1, shopping);
        if (goodsList == null || goodsList.size() <= 0) {
            System.err.println("\t！！查无此商品 ！！");
            //調用选择下一步函数
            ScannerChoices.changedInfoNext(oper);
        } else {
            //获取商品
            Goods goods = goodsList.get(0);
            System.out.println("\t\t\t\t\t商品列表\n\n");
            System.out.println("\t商品编号\t\t商品名称\t\t商品价格\t\t商品数量\t\t备注\n");
            System.out.print("\t" + goods.getGid() + "\t\t" + goods.getGname() + "\t\t" + goods.getGprice() + "\t\t" + goods.getGnum());
            if (goods.getGnum() == 0) {
                System.out.println("\t\t该商品已售空");
            } else if (goods.getGnum() < 10) {
                System.out.println("\t\t该商品已不足10件");
            } else {
                System.out.println("\t\t-");
            }
            gid = goods.getGid(); //将商品编号返回给调用者
        }
        return gid;
    }

    /**
     * 根据商品 gid or gName查询商品
     *
     * @param id,商品名称
     * @return 商品信息
     */
    public ArrayList<Goods> queryGoodsKey(int id, String gName) {
        ArrayList<Goods> goodsList = new ArrayList<Goods>();
        try {
            connection = JDBCUtils.getConnection();
            String sql = "SELECT * FROM goods WHERE gid=? OR gname=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, gName);
            set = ps.executeQuery();
            while (set.next()) {
                int gid = set.getInt("gid");
                String gname = set.getString(2);
                double gprice = set.getDouble(3);
                int gnum = set.getInt(4);

                Goods goods = new Goods(gid, gname, gprice, gnum);
                goodsList.add(goods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(set, ps, connection);
        }
        return goodsList;
    }

    /**
     * 精确查询售货员信息
     *
     * @param sName
     * @return
     */
    public ArrayList<SalesMan> querySalesMan(String sName) {
        ArrayList<SalesMan> SalesManList = new ArrayList<SalesMan>();
        String sql = "select * from salesman where sname = ?";
        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
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
            JDBCUtils.close(set, ps, connection);
        }
        return SalesManList;
    }

    /**
     * 模糊查询函数小工具
     *
     * @return int 当商品件数有且只有一件时返回商品gid号，商品已售空时返回 -1. >1件时返回-2 . 查无此商品时返回-3
     */
    public int querySettlement() throws SQLException {
        //商品已售空时返回 -1
        int gid = -1;
        //調用 关键字查询函数
        ArrayList<Goods> goodsArrayList = new GoodsDaoImpl().queryGoods(3);
        if (goodsArrayList == null || goodsArrayList.size() <= 0) {
            System.err.println("\t！！查无此商品 ！！\n");
            gid = -3;
        } else {
            //查询到商品 修改Goods里面的信息
            System.out.println("\t\t\t\t\t商品列表\n\n");
            System.out.println("\t商品编号\t\t商品名称\t\t商品价格\t\t商品数量\t\t备注\n");
            for (int i = 0, length = goodsArrayList.size(); i < length; i++) {
                //获取商品信息
                Goods goods = goodsArrayList.get(i);
                if (goods.getGnum() > 0) {
                    //向用户展示信息
                    System.out.print("\t" + goods.getGid() + "\t\t" + goods.getGname() + "\t\t" + goods.getGprice() + "\t\t" + goods.getGnum());
                    if (goods.getGnum() == 0) {
                        System.out.println("\t\t该商品已售空");
                    } else if (goods.getGnum() <= 10) {
                        System.out.println("\t\t该商品已不足10件");
                    } else {
                        System.out.println("\t\t商品数量还有很多可以购买");
                    }

                    //当查询的商品数量是1把查询到id返回
                    if (goodsArrayList.size() == 1) {
                        gid = goods.getGid();
                    } else {
                        gid = -2;
                    }
                }

            }
        }

        return gid;
    }
}
