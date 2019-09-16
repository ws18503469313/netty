package com.itmuch.nettyTest.endecode.enums;

/**
 * 硬件类型
 */
public enum SNType {
    MAIN_BOARD(1, "main_board_V"),//主板
    ZUUL(0, "zuul_V");//网关

    private int value;

    private String remark;

    SNType(int value, String remark) {
        this.value = value;
        this.remark = remark;
    }
    public static SNType getByValue(int value) {
        for (SNType o : SNType.values()) {
            if (o.getValue() == value) {
                return o;
            }
        }
        return null;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
