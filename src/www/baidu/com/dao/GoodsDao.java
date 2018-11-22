package www.baidu.com.dao;

import www.baidu.com.domain.Goods;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @create: 2018/11/21 9:47
 * @version: 1.0
 */
public interface GoodsDao {
    /**
     * 1.添加商品到数据库goods表
     * @param goods 商品对象
     * @return boolean
     */
    public boolean addGoods(Goods goods);
    /**
     * 2.更改商品信息到数据库goods表
     * @param key   选择要更改商品信息
     * @param goods 商品对象
     * @return boolean
     */
    public boolean updateGoods(int key,Goods goods);

    /**
     * 3.从数据库goods表中-刪除商品
     * @param gid 商品编号
     * @return boolean
     */
    public boolean deleteGoods(int gid);


    /**
     *4.查询商品信息
     * @param key 查询方式
     * @return ArrayList<Goods>
     */
    public ArrayList<Goods> queryGoods(int key) throws SQLException;

    /**
     *5.显示所有商品信息
     * @return ArrayList<Goods>
     */
    public ArrayList<Goods> displayGoods();
}
