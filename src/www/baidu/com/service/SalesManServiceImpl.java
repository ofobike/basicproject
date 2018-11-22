package www.baidu.com.service;

import www.baidu.com.Controls.control;
import www.baidu.com.dao.SalesManDao;
import www.baidu.com.daoImpl.SalesManDaoImpl;
import www.baidu.com.domain.SalesMan;
import www.baidu.com.tools.QueryPrint;
import www.baidu.com.tools.ScannerChoices;

import java.util.ArrayList;

import static www.baidu.com.tools.ScannerChoices.ScannerInfoString;

/**
 * @create: 2018/11/21 16:01
 * @version: 1.0
 */
public class SalesManServiceImpl {
    /**
     * 1.添加售货员界面 已实现！
     */
    public static void addSalesManPage() throws Exception {
        System.out.println("\t正在执行添加售货员操作\n");

        System.out.println("\n添加售货员-姓名");
        String sName = ScannerInfoString();

        System.out.println("\n添加售货员-密码");
        String sPssswd = ScannerInfoString();

        SalesMan salesMan = new SalesMan(sName, sPssswd);
        //调用登陆方法
        boolean bool = new SalesManDaoImpl().addSalesMan(salesMan);
        if (bool) {
            System.out.println("\n\t!您已成功添加售货员到数据库!");
        } else {
            System.out.println("添加售货员失败");
        }
        ScannerChoices.choiceSalesManNext("addSalesMan");
    }

    /**
     * 2.更改售货员界面
     */
    public static void updateSalesManPage() throws Exception {
        System.out.println("\t正在执行更改售货员操作\n");
        System.out.println("请输入想要更改的售货员名字");
        String sName = ScannerInfoString();
        //调用仔细查询的用户名的函数
        ArrayList<SalesMan> salesMenLists = new QueryPrint().querySalesMan(sName);
        if (salesMenLists == null || salesMenLists.size() <= 0) {
            System.err.println("\t！！查无此人！！");
            ScannerChoices.choiceSalesManNext("updateSalesMan");
        } else {
            //显示将要更改的售货员信息
            System.out.println("\t\t\t售货员信息\n\n");
            System.out.println("\t售货员编号\t\t售货员姓名\t\t售货员密码");
            SalesMan salesMan = salesMenLists.get(0);
            //上面的精确查找中，只能返回一组数值。无需遍历！
            System.out.println("\t" + salesMan.getSid() + "\t\t\t" + salesMan.getSname()
                    + "\t\t\t" + salesMan.getSpassword());
            System.out.println();
            //选择更改售货员内容
            System.out.println("\n--------请选择您要更改的内容\n");
            System.out.println("\t1.更改售货员-姓名");
            System.out.println("\t2.更改售货员-密码");
            do {
                String choice = ScannerInfoString();
                String regex = "[0-2]";
                if (choice.matches(regex)) {
                    //把字符串转为int
                    int info = Integer.parseInt(choice);
                    switch (info) {
                        case 0:
                            control.salesManManagementPage();
                            break;
                        case 1:
                            System.out.println("更改售货员-新姓名");
                            String sNewName = ScannerInfoString();
                            SalesMan salesManName = new SalesMan(salesMan.getSid(), sNewName, null);
                            //调用修改方法
                            boolean bool = new SalesManDaoImpl().updateSalesMan(1, salesManName);
                            if (bool) {
                                System.out.println("\\n\\t！！成功更新售货员名字至数据库！！\\n");
                            } else {
                                System.err.println("\n\t！！更新售货员名字失敗！！");
                            }
                            ScannerChoices.choiceSalesManNext("updateSalesMan");
                            break;
                        case 2:
                            System.out.println("更改售货员-新密码");
                            String sNewPasswd = ScannerInfoString();
                            SalesMan salesManPass = new SalesMan(salesMan.getSid(), null, sNewPasswd);
                            boolean boolSaleMan = new SalesManDaoImpl().updateSalesMan(2, salesManPass);
                            if (boolSaleMan) {
                                System.out.println("\n\t！！成功更新售货员密码至数据库！！\n");
                            } else {
                                System.err.println("\n\t！！更新售货员密码失敗！！");
                            }
                            ScannerChoices.choiceSalesManNext("updateSalesMan");
                            break;
                        default:
                            break;
                    }
                }
            } while (true);
        }
    }


    /**
     * 3.删除售货员界面
     */
    public static void deleteSalesManPage() throws Exception {
        System.out.println("\t正在执行 删除售货员 操作\n");
        System.out.println("请输入想要删除的售货员名字");
        String sName = ScannerInfoString();
        //调用精确查找售货员函数
        ArrayList<SalesMan> salesManList = new QueryPrint().querySalesMan(sName);
        if (salesManList == null || salesManList.size() <= 0) {
            System.err.println("\t！！查无此人！！");
            ScannerChoices.choiceSalesManNext("deleteSalesMan");
        } else {
            //显示要删除的信息
            //显示将要删除的售货员信息
            System.out.println("\t\t\t删除售货员信息\n\n");
            System.out.println("\t售货员编号\t\t售货员姓名\t\t售货员密码");
            for (int i = 0, length = salesManList.size(); i < length; i++) {
                SalesMan salesMan = salesManList.get(i);
                System.out.println("\t" + salesMan.getSid() + "\t\t\t" + salesMan.getSname() + "\t\t\t" + salesMan.getSpassword());
                System.out.println();
            }
            do {
                System.out.println("\n确认删除该售货员：Y/N");
                String choice = ScannerInfoString();
                if ("y".equals(choice) || "Y".equals(choice)) {
                    //这里进行数据库的删除工作
                    boolean bool = new SalesManDaoImpl().deleteSalesMan(sName);
                    if (bool) {
                        System.err.println("\t已经删除该售货员因为偷懒");
                    } else {
                        System.err.println("\t删除售货员出错误!!!");
                    }
                    ScannerChoices.choiceSalesManNext("deleteSalesMan");
                } else if ("N".equals(choice) || "n".equals(choice)) {
                    control.salesManManagementPage();
                }
                System.err.println("\t!!输入有误,请重新输入!!");
            } while (true);
        }
    }

    /**
     * 4.查询售货员界面 已实现！
     */
    public static void querySalesManPage() throws Exception {
        System.out.println("\t\t  正在执行查询售货员操作\n");
        System.out.println("要查询的售货员关键字");
        String sName = ScannerInfoString();
        //调用Dao里面的方法
        ArrayList<SalesMan> salesManList = new SalesManDaoImpl().querySalesMan(sName);
        if (salesManList == null || salesManList.size() <= 0) {
            System.err.println("\t！没有人员符合查询条件！");
        } else {
            System.out.println("\t\t\t所有售货员列表\n\n");
            System.out.println("\t售货员编号\t\t\t售货员姓名\t\t\t售货员密码");
            for (int i = 0, length = salesManList.size(); i < length; i++) {
                SalesMan salesMan = salesManList.get(i);
                System.out.println("\t" + salesMan.getSid() + "\t\t\t" + salesMan.getSname() + "\t\t\t" + salesMan.getSpassword());
                System.out.println();
            }
        }
        //回调函数
        ScannerChoices.choiceSalesManNext("querySalesMan");
    }


    /**
     * 5.显示所有售货员界面
     */
    public static void displaySalesManPage() throws Exception {
        //调用Dao里面的方法
        ArrayList<SalesMan> salesManList = new SalesManDaoImpl().displaySalesMan();
        if (salesManList == null || salesManList.size() <= 0) {
            System.err.println("\\t！！售货员列表为空！！");
            //跳转到住也买你
            control.salesManManagementPage();
        } else {
            System.out.println("\t\t\t所有售货员列表\n\n");
            System.out.println("\t售货员编号\t\t售货员姓名\t\t售货员密码");
            for (int i = 0, length = salesManList.size(); i < length; i++) {
                SalesMan salesMan = salesManList.get(i);
                System.out.println("\t" + salesMan.getSid() + "\t\t\t" + salesMan.getSname() + "\t\t\t" + salesMan.getSpassword());
                System.out.println();
            }
            do {
                System.out.println("\n\n输入 0 返回上一级菜单");
                String choice = ScannerInfoString();

                if ("0".equals(choice)) {
                    control.salesManManagementPage();
                }
                System.err.print("\t输入有误！");
            } while (true);
        }
    }
}
