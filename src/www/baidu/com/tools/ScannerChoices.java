package www.baidu.com.tools;

import www.baidu.com.Controls.control;
import www.baidu.com.service.GoodsServiceImpl;
import www.baidu.com.service.SalesManServiceImpl;


import java.util.Scanner;

/**
 * @create: 2018/11/21 10:26
 * @version: 1.0
 */
public class ScannerChoices {
    /**
     * @return String 获取的键盘输入
     */
    public static String ScannerInfoString() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入:");
        return scanner.next();

    }

    /**
     * @return double 键盘获取商品价格,小数点后两位
     */
    public static double ScannerInfo() {
        double num = 0.00;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.print("保留小数点后两位,请输入：");
            String info = sc.next();

            String regex = "(([1-9][0-9]*)\\.([0-9]{2}))|[0]\\.([0-9]{2})";//保留小数点后2位小数
            if (info.matches(regex)) {
                num = Double.parseDouble(info);
            } else {
                System.err.println("！输入有误！");
                continue;
            }
            break;
        } while (true);

        return num;
    }

    /**
     * @return int 键盘获取商品数量
     */
    public static int ScannerNum() {
        int num = 0;
        String regex = "([1-9])|([1-9][0-9]+)";//商品数量
        do {
            Scanner sc = new Scanner(System.in);
            System.out.print("请输入：");
            String nums = sc.next();

            if (nums.matches(regex)) {
                num = Integer.parseInt(nums);
            } else {
                System.err.println("！输入有误！");
                continue;
            }
            break;
        } while (true);
        return num;
    }

    /**
     * 获取用户-更改完商品-下一步
     * 获取用户-删除完商品-下一步
     * 获取用户-添加完商品-下一步
     *
     * @param oper
     */
    public static void changedInfoNext(String oper) throws Exception {
        do {
            System.out.println("是否继续进行-当前操作:(Y/N)");
            String choice = ScannerChoices.ScannerInfoString();
            if ("Y".equals(choice) || "y".equals(choice)) {
                //下面的嵌套if-else 是让用户选择继续操作当前步骤所跳转到指定页面。（因为不同函数调用，跳转的指定函数不同）
                if ("addGoodsPage".equals(oper)) {
                    //继续添加商品
                    GoodsServiceImpl.addGoodsPage();
                    //break;
                } else if ("deleteGoodsPage".equals(oper)) {
                    GoodsServiceImpl.deleteGoodsPage();
                } else if ("upateGoodsPage".equals(oper)) {
                    GoodsServiceImpl.upateGoodsPage();
                }
            } else if ("N".equals(choice) || "n".equals(choice)) {
                System.out.println("选择的是No");
                //返回控制页面
                control.MaintenancePage();
                //break;
            }
            System.out.println("\n输入有误！请重新输入.");
        } while (true);
    }


    /**
     * 获取用户-更改-完售货员-下一步
     * 获取用户-添加-完售货员-下一步
     * 获取用户-查询-完售货员-下一步
     * 获取用户-删除-完售货员-下一步
     *
     * @param oper
     */
    public static void choiceSalesManNext(String oper) throws Exception {
        do {
            System.out.println("是否继续进行-当前操作:(Y/N)");
            String choice = ScannerChoices.ScannerInfoString();
            if ("y".equals(choice) || "Y".equals(choice)) {
                if ("addSalesMan".equals(oper)) {
                    SalesManServiceImpl.addSalesManPage();
                } else if ("querySalesMan".equals(oper)) {
                    SalesManServiceImpl.querySalesManPage();
                } else if ("deleteSalesMan".equals(oper)) {
                    SalesManServiceImpl.deleteSalesManPage();
                } else if ("updateSalesMan".equals(oper)) {
                    SalesManServiceImpl.updateSalesManPage();
                }
            } else if ("N".equals(choice) || "n".equals(choice)) {
                control.salesManManagementPage();
            }
            System.err.println("\t输入有误！");
        } while (true);
    }

}
