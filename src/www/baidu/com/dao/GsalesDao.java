package www.baidu.com.dao;

import www.baidu.com.domain.Gsales;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @create: 2018/11/22 9:48
 * @version: 1.0
 */
public interface GsalesDao {
    /**
     * 1.当天卖出的商品
     * @return ArrayList<Gsales> 商品信息,包括 allSum (单种商品的销售总和)
     */
    public ArrayList<Gsales> dailyGsales();
    /**
     *2.购物结算-向sales表中插入商品数据！
     *@param gSales 售卖商品对象
     *@return boolean
     */
    public boolean shoppingSettlement(Gsales gSales);
}
