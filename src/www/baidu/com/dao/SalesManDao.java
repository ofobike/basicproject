package www.baidu.com.dao;

import www.baidu.com.domain.SalesMan;

import java.util.ArrayList;

/**
 * @create: 2018/11/21 15:49
 * @version: 1.0
 */
public interface SalesManDao {
    /**
     * 1.前台收银登陆
     *
     * @param sName 用户名
     * @return ArrayList<SalesMan> sPassWord,sId
     */
    public ArrayList<SalesMan> checkstandLog(String sName);

    /**
     * 4.删除售货员
     *
     * @param sName 用户名
     * @return boolean
     */
    public boolean deleteSalesMan(String sName);

    /**
     * 3.更改售货员信息
     *
     * @param key   要更改项
     * @param sName 用户名
     * @return boolean
     */
    public boolean updateSalesMan(int key, SalesMan sName);

    /**
     * 2.添加售货员
     *
     * @param sName 用户名
     * @return boolean
     */

    public boolean addSalesMan(SalesMan sName);

    /**
     * 6.显示所有售货员
     *
     * @return ArrayList<SalesMan>
     */
    public ArrayList<SalesMan> displaySalesMan();

    /**
     * 5.模糊查询售货员
     *
     * @param sName 用户名
     * @return ArrayList<SalesMan>
     */
    public ArrayList<SalesMan> querySalesMan(String sName);
}
