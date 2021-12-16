package com.kagu.edit.jkagu.conf;

public class IntegerContainer {

    private Integer num;
    public IntegerContainer(Integer num){
        this.num = num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

    public void addToCurrentNum(Integer add)
    {
        num = num + add;
    }
}
