package www.baidu.com.domain;

/**
 * 商品表
 */
public class Goods {
    private Integer gid; // 主键 商品编号 自动生成
    private String gname;// 商品名称 唯一约束 unique
    private Double gprice;//商品价格
    private Integer gnum;//商品数量

    /**
     * 添加商品信息
     *
     * @param gname,gprice,gum
     */
    public Goods(String gname, double gprice, int gum) {
        this.gname = gname;
        this.gprice = gprice;
        this.gnum = gum;
    }

    /**
     * 展示所有商品
     *
     * @param gid,gname,gprice,gum
     */
    public Goods(int gid, String gname, Double gprice, int gum) {
        this.gid = gid;
        this.gname = gname;
        this.gprice = gprice;
        this.gnum = gum;
    }

    /**
     * 根据编号更改商品信息
     *
     * @param gid,gum
     */
    public Goods(int gid, int gnum) {
        this.gid = gid;
        this.gnum = gnum;
    }

    /**
     * 根据编号更改商品信息
     *
     * @param gid,gprice
     */
    public Goods(int gid, double gprice) {
        this.gid = gid;
        this.gprice = gprice;
    }

    /**
     * 根据编号更改商品信息
     *
     * @param gid,gname
     */
    public Goods(int gid, String gname) {
        this.gid = gid;
        this.gname = gname;
    }

    //共有-get、set-方法。
    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public double getGprice() {
        return gprice;
    }

    public void setGprice(double gprice) {
        this.gprice = gprice;
    }

    public int getGnum() {
        return gnum;
    }

    public void setGnum(int gnum) {
        this.gnum = gnum;
    }
}
