package www.baidu.com.service;

import www.baidu.com.Controls.control;
import www.baidu.com.daoImpl.GsalesDaoImpl;
import www.baidu.com.domain.Gsales;
import www.baidu.com.tools.ScannerChoices;

import java.util.ArrayList;

/**
 * @create: 2018/11/22 11:22
 * @version: 1.0
 */
public class GalesServiceImpl {
    /**
     * 展示今天销售的商品销售额 已经销售数量
     */
    public static void dailySaleGoodsPage() throws Exception {
        System.out.println("\t正在执行列出当日售出商品列表操作\n");
        ArrayList<Gsales> GsalesList = new GsalesDaoImpl().dailyGsales();
        if (GsalesList.size() <= 0 || GsalesList == null) {
            System.err.println("\t！！今日无商品售出！！");
            control.commodityManagementPage();
        } else {
            System.out.println("\t\t\t\t今日售出商品列表\n");
            System.out.println("\t商品名称\t\t商品价格\t\t商品数量\t\t销量\t\t备注\n");
            //获取售出商品：gname,gprice,gnum, allSum (单种商品的销售总和)
            for (int i = 0, length = GsalesList.size(); i < length; i++) {
                //获取售出商品：gname,gprice,gnum, allSum (单种商品的销售总和)
                Gsales gSales = GsalesList.get(i);
                System.out.print("\t"+gSales.getGName()+"\t\t"+gSales.getGPrice()+" $\t\t"+gSales.getGNum()+"\t\t"+gSales.getAllSnum());
                int gNUm = gSales.getGNum();
                if (gNUm==0)
                {
                    System.out.println("\t\t该商品已售空");
                }else if (gNUm<10)
                {
                    System.out.println("\t\t该商品已不足10件");
                }else
                {
                    System.out.println("\t\t-");
                }
                System.out.println("\t");
            }
            do
            {
                System.out.println("\n\n输入 0 返回上一级菜单");
                String choice = ScannerChoices.ScannerInfoString();
                if ("0".equals(choice))
                {
                    control.salesManManagementPage();
                }
                control.commodityManagementPage();
            } while (true);
        }
    }
}
