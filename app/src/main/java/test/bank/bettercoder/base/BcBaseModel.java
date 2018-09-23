package test.bank.bettercoder.base;

/**
 * Created by heng on 2018/9/13
 *
 */
public class BcBaseModel {
    public String returnCode;
    public String returnMsg;
    public String apiName;

    public boolean isOk() {
        return returnCode.equals("0");
    }

    public boolean isExpired(){
        // TODO: 2018/1/20 还不知道返回码是什么
        return returnCode.equals("0");
    }
}
