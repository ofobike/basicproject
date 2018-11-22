package www.baidu.com.Controls;

import www.baidu.com.daoImpl.GoodsDaoImpl;
import www.baidu.com.daoImpl.GsalesDaoImpl;
import www.baidu.com.daoImpl.SalesManDaoImpl;
import www.baidu.com.domain.Goods;
import www.baidu.com.domain.Gsales;
import www.baidu.com.domain.SalesMan;
import www.baidu.com.service.GalesServiceImpl;
import www.baidu.com.service.GoodsServiceImpl;
import www.baidu.com.service.SalesManServiceImpl;
import www.baidu.com.tools.Arith;
import www.baidu.com.tools.QueryPrint;
import www.baidu.com.tools.ScannerChoices;

import java.util.ArrayList;

/**
 * @create: 2018/11/21 10:31
 * @version: 1.0
 */
public class control extends ScannerChoices {
    /**
     * 入口函数
     */
    public static void main(String[] args) throws Exception {
        control.mainPage();
    }

    private static void mainPage() throws Exception {
        System.out.println("***************************\n");
        System.out.println("\t 1.商品维护\n");
        System.out.println("\t 2.前台收银\n");
        System.out.println("\t 3.商品管理\n");
        System.out.println("***************************");

        System.out.println("\n请输入选项,或者按0退出.");
        do {
            String choice = ScannerInfoString();
            String regex = "[0-3]";//正则表达式
            if (choice.matches(regex)) {
                //把字符串转为int类型
                int info = Integer.parseInt(choice);
                switch (info) {
                    case 0:
                        System.out.println("------------------");
                        System.out.println("您已经退出系统!");
                        System.exit(1);//退出程序，返回值随便设置
                        break;
                    case 1:
                        //商品的维护页面
                        MaintenancePage();
                        break;
                    case 2:
                        checkstandLogPage();
                        break;
                    case 3:
                        commodityManagementPage();
                        break;
                    default:
                        break;
                }
            }
            System.err.println("!输入有误!");
            System.out.println("重新选择或者按0退出.");
        } while (true);
    }

    /**
     * 2.前台收银登陆界面
     */
    private static void checkstandLogPage() throws Exception {
        System.out.println("\n*******欢迎使用商超购物管理系统*******\n");
        System.out.println("\t 1.登录系统\n");
        System.out.println("\t 2.退出\n");
        System.out.println("-----------------------------");
        System.out.println("请输入选项,或者按 0 返回上一级菜单.");
        do {
            String choice = ScannerInfoString();
            String regex = "[0-2]";
            if (choice.matches(regex)) {
                int info = Integer.parseInt(choice);
                switch (info) {
                    case 0:
                        mainPage();
                        break;
                    case 1:
                        int loginTimes = 3;//三次登陆机会
                        while (loginTimes != 0) {
                            loginTimes--;
                            System.out.println("---用户名---");
                            String sName = ScannerInfoString();
                            System.out.println("---密码---");
                            String sPssWord = ScannerInfoString();
                            //以用户名从数据库中获取用户密码.
                            ArrayList<SalesMan> salesManInfo = new SalesManDaoImpl().checkstandLog(sName);
                            if (salesManInfo == null || salesManInfo.size() <= 0) {
                                //用户不存在
                                System.err.println("\t!!用户名输入有误!!\n");
                                System.out.println("\n剩余登陆次数：" + loginTimes);
                            } else {
                                SalesMan salesMan = salesManInfo.get(0);
                                if (sPssWord.equals(salesMan.getSpassword())) {
                                    System.out.println("\t  ---账户成功登陆---");
                                    shoppingSettlementPage(salesMan.getSid());//参数为营业员编号sId

                                } else {
                                    System.err.println("\t!!密码错误!!\n");
                                    System.out.println("\n剩余登陆次数：" + loginTimes);
                                }
                            }

                        }
                        //loginTimes = 0
                        System.out.println("------------------");
                        System.err.println("\t！！您已被强制退出系统！！");
                        System.exit(1);
                        break;
                    case 2:
                        System.out.println("------------------");
                        System.out.println("您已经退出系统!");
                        System.exit(-1);
                        break;
                    default:
                        break;
                }
            }
            System.err.println("!输入有误!");
            System.out.println("重新输入或按 0 返回上一级菜单");
        } while (true);
    }

    /**
     * 购物结算界面
     */
    private static void shoppingSettlementPage(int salesManSid) throws Exception {
        System.out.println("\n\t*******购物结算*******\n");
        do {
            System.out.println("按 S 开始购物结算.按 0 返回账户登录界面");
            String choNext = ScannerInfoString();
            if ("0".equals(choNext)) {
                //跳转到登陆也页面
                checkstandLogPage();
            } else if ("s".equals(choNext) || "S".equals(choNext)) {
                System.out.println("\n--请输入商品关键字--");
                //当商品件数有且只有一件时返回商品gid号，商品已售空时返回 -1. >1件时返回-2 . 查无此商品时返回-3
                int gid = new QueryPrint().querySettlement();
                switch (gid) {
                    case -3:
                        //查询不到商品
                        break;
                    case -1:
                        System.err.println("\t--抱歉，该商品已售空--");
                        break;
                    default:
                        System.out.println("--按商品编号选择商品--");
                        //传参gid，调用精确查询商品
                        int shoppingGid = ScannerNum();
                        ArrayList<Goods> goodsList = new QueryPrint().queryGoodsKey(shoppingGid, null);
                        if (goodsList.size() <= 0 || goodsList == null) {
                            System.err.println("\t！！查无此商品 ！！\n");
                        } else {
                            //查询到商品并且只能查询到一条商品
                            Goods goods = goodsList.get(0);
                            //获取数量
                            int gnum = goods.getGnum();
                            //获取价格
                            double gprice = goods.getGprice();
                            System.out.println("\t请输入购买的数量\t");
                            do {
                                //调用输入的数量方法 要购买的数量
                                int choicegoodsNum = ScannerChoices.ScannerNum();
                                //如果输入的数量大于库存那么输出信息
                                if (choicegoodsNum > gnum) {
                                    System.err.println("\t！！仓库储备不足！！");
                                    System.out.println("--请重新输入购买数量--");
                                } else {
                                    ////利用BigDecimal作乘法运算 商品数量*价格
                                    double allPrice = Arith.mul(choicegoodsNum, gprice);
                                    System.out.println("\t\t\t  购物车结算\n");
                                    System.out.println("\t\t商品名称\t商品单价\t购买数量\t总价\n");
                                    System.out.println("\t\t" + goods.getGname() + "\t" + gprice + " $\t" + choicegoodsNum + "\t" + allPrice + " $\n");
                                    //是否购买做出判断
                                    do {
                                        System.out.println("确认购买：Y/N");
                                        String choShopping = ScannerInfoString();
                                        if ("y".equals(choShopping) || "Y".equals(choShopping)) {
                                            System.out.println("\n总价：" + allPrice + " $");
                                            System.out.println("\n实际缴费金额");
                                            do {
                                                //键盘获取用户输入的钱,小数点后两位
                                                double amount = ScannerChoices.ScannerInfo();
                                                //用户交钱与购买物品总价间的差额 调用之间的减法
                                                double balance = Arith.sub(amount, allPrice);
                                                if (balance < 0) {
                                                    System.err.println("\t！！缴纳金额不足！！");
                                                    System.out.println("\n请重新输入缴纳金额($)");
                                                } else {
                                                    //有钱购买
                                                    /**这里是购物结算操作数据库！！！！！！----------------------
                                                     1.更改goods表数量

                                                     2.增加sales表数量
                                                     原商品数量gNum。 结算员Id  salesManSid */
                                                    Gsales gsales = new Gsales(goods.getGid(), salesManSid, choicegoodsNum);
                                                    //调用GalesDao方法
                                                    boolean insert = new GsalesDaoImpl().shoppingSettlement(gsales);
                                                    //对goods表操作
                                                    int goodsNewNum = gnum - choicegoodsNum;
                                                    //调用构造方法 要修改表的数据
                                                    Goods newGood = new Goods(goods.getGid(), goodsNewNum);
                                                    boolean update = new GoodsDaoImpl().updateGoods(3, newGood);

                                                    //同时成功
                                                    if (insert && update) {
                                                        System.out.println("找零：" + balance);
                                                        System.out.println("\n谢谢光临，欢迎下次惠顾");
                                                    } else {
                                                        System.err.println("！支付失败！"); //出现这个错误一定是数据库操作有问题！
                                                    }
                                                    shoppingSettlementPage(salesManSid);
                                                }
                                            } while (true);
                                        } else if ("N".equals(choShopping) || "n".equals(choShopping)) {
                                            shoppingSettlementPage(salesManSid);
                                        }
                                        System.err.println("\t！！请确认购物意向！！");
                                    } while (true);
                                }
                            } while (true);

                        }
                        break;
                }
            }else {
                System.err.println("\t!!请输入合法字符!!\n");
            }
        } while (true);
    }

    /**
     * 3.商品管理界面
     */
    public static void commodityManagementPage() throws Exception {
        System.out.println("***************************\n");
        System.out.println("\t 1.售货员管理\n");
        System.out.println("\t 2.列出当日卖出列表\n");
        System.out.println("***************************");

        System.out.println("\n请输入选项,或者按 0 返回上一级菜单.");
        do {
            String choice = ScannerInfoString();
            String regex = "[0-2]";
            if (choice.matches(regex)) {
                int info = Integer.parseInt(choice);
                switch (info) {
                    case 0:
                        mainPage();
                        break;
                    case 1:
                        salesManManagementPage();
                        break;
                    case 2:
                        GalesServiceImpl.dailySaleGoodsPage();
                    default:
                        break;
                }
            }
            System.err.println("!输入有误!");
            System.out.println("重新输入或按 0 返回上一级菜单.");
        } while (true);
    }

    /**
     * 商品的维护页面
     */
    public static void MaintenancePage() throws Exception {
        System.out.println("***************************\n");
        System.out.println("\t 1.添加商品\n");
        System.out.println("\t 2.更改商品\n");
        System.out.println("\t 3.删除商品\n");
        System.out.println("\t 4.查询商品\n");
        System.out.println("\t 5.显示所有商品\n");
        System.out.println("***************************");

        System.out.println("\n请输入选项,或者按 0 返回上一级菜单.");

        do {
            String choice = ScannerInfoString();
            //正则匹配
            String regex = "[0-5]";
            if (choice.matches(regex)) {
                //解析为integer类型
                int info = Integer.parseInt(choice);
                switch (info) {
                    case 0:
                        mainPage();
                        break;
                    case 1:
                        //调用添加商品的方法
                        GoodsServiceImpl.addGoodsPage();
                        break;
                    case 2:
                        GoodsServiceImpl.upateGoodsPage();
                        break;
                    case 3:
                        GoodsServiceImpl.deleteGoodsPage();
                        break;
                    case 4:
                        //查询商品界面
                        GoodsServiceImpl.queryGoodsPage();
                        break;
                    case 5:
                        //显示所有商品
                        GoodsServiceImpl.displayGoodsPage();
                        break;
                    default:
                        break;
                }
            }
            System.err.println("!输入有误!");
            System.out.println("重新输入或按 0 返回上一级菜单.");
        } while (true);
    }

    /**
     * 售货员管理界面
     */
    public static void salesManManagementPage() throws Exception {
        System.out.println("***************************\n");
        System.out.println("\t 1.添加售货员\n");
        System.out.println("\t 2.更改售货员\n");
        System.out.println("\t 3.删除售货员\n");
        System.out.println("\t 4.查询售货员\n");
        System.out.println("\t 5.显示所有售货员\n");
        System.out.println("***************************");

        System.out.println("\n请输入选项,或者按 0 返回上一级菜单.");
        do {
            String choice = ScannerInfoString();
            String regex = "[0-5]";
            if (choice.matches(regex)) {
                int info = Integer.parseInt(choice);
                switch (info) {
                    case 0:
                        commodityManagementPage();
                        break;
                    case 1:
                        SalesManServiceImpl.addSalesManPage();
                        break;
                    case 2:
                        //修改
                        SalesManServiceImpl.updateSalesManPage();
                        break;
                    case 3:
                        SalesManServiceImpl.deleteSalesManPage();
                        break;
                    case 4:
                        SalesManServiceImpl.querySalesManPage();
                        break;
                    case 5:
                        SalesManServiceImpl.displaySalesManPage();
                        break;
                    default:
                        break;
                }
            }
            System.err.println("\t!输入有误!");
            System.out.println("重新输入或按 0 返回上一级菜单.");
        } while (true);
    }
}
