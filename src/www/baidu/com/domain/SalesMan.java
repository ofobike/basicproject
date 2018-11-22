package www.baidu.com.domain;

/**
 * 营业员表
 */

public final class SalesMan {
    private int sid;//营业员编号，自动生成
    private String sname;// 营业员密码
    private String spassword;//

    /**
     * 验证用户登陆
     *
     * @param sid,spassWord
     */
    public SalesMan(int sid, String spassword) {
        this.sid = sid;
        this.spassword = spassword;
    }

    /**
     * 查询用户、更改用户密码
     *
     * @param sid,sname,spassword
     */
    public SalesMan(int sid, String sname, String spassword) {
        this.sid = sid;
        this.sname = sname;
        this.spassword = spassword;
    }

    /**
     * 添加用户
     *
     * @param sname,sPassWord
     */
    public SalesMan(String sname, String spassword) {
        this.sname = sname;
        this.spassword = spassword;
    }


    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSpassword() {
        return spassword;
    }

    public void setSpassword(String spassword) {
        this.spassword = spassword;
    }
}
