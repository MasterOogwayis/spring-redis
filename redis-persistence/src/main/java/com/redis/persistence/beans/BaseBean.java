package com.redis.persistence.beans;

import java.io.Serializable;

public abstract class BaseBean implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -2241849673738530058L;


    public abstract Long getId();


}
