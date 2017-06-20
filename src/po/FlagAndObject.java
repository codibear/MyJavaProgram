package po;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 29185 on 2017/6/16.
 */
public class FlagAndObject implements Serializable {
    private int flag;
    private List arrayList;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List getArrayList() {
        return arrayList;
    }

    public void setArrayList(List arrayList) {
        this.arrayList = arrayList;
    }
}
