package test;

import java.io.Serializable;

/**
 * @author ZhangShaowei on 2017/3/24 10:43
 */

public class Customer implements Serializable {

    private String name;

    /**  */
    public String getName() {
        return name;
    }

    /**  */
    public void setName(String name) {
        this.name = name;
    }
}
