package com.itmuch.nettyTest.endecode.enums;

/**
 *  主板状态按bit表示如下：
 *  主板状态表（bit位，0：无此状态，1：存在此状态）
 * ----------------------------------------------------------------------------------
 * |    通讯不通   |   主板故障   |	查找     |	超载 |	呆滞  |	混料	 |  缺料  |	预留 |
 * ----------------------------------------------------------------------------------
 * |    Bit7      |   Bit6     |	Bit5    |	Bit4 |	Bit3 |	Bit2  |	Bit1  |	Bit0|
 * ----------------------------------------------------------------------------------
 */
public enum  MainBordStates {

    RESERVE(0, "预留"),
    DEFICIENCY(1, "缺料"),
    MIXING(2, "混料"),
    OVER_DUE(3, "呆滞"),
    OVER_LOAD(4, "超载"),
    SEEK(5, "查找"),
    MALFUNCTION(6, "主板故障"),
    NET_FAILD(7, "通讯不通");

    private String remark;

    private int value;

    MainBordStates(int value, String remark) {
        this.remark = remark;
        this.value = (int)(1 * Math.pow(2, value));
    }

    public int getValue() {
        return value;
    }
    public String getRemark() {
        return remark;
    }

    public static MainBordStates getByValue(int value) {
        for (MainBordStates o : MainBordStates.values()) {
            if (o.getValue() == value) {
                return o;
            }
        }
        return null;
    }
}
