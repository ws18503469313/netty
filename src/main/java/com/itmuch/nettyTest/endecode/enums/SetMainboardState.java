package com.itmuch.nettyTest.endecode.enums;

/**
 * 设置主板状态
 * ---------------------------------------------
 * 功能	        |   STYPE  |   SDATA（字节）
 * ---------------------------------------------
 * 主板查找	    |   0x05   |	U8:0关1开。（1）
 * ---------------------------------------------
 * 设置单重	    |   0x07   |	U32:物品单重。（4）
 * ---------------------------------------------
 * 设置物理量程	|   0x08   |	U32:物理量程。（4）
 * ---------------------------------------------
 * 设置业务量程	|   0x09   |	U32:业务量程。（4）
 * ---------------------------------------------
 * 更改主板状态	|   0x0a   |	U8:主板状态。（1）
 * ---------------------------------------------
 */
public enum  SetMainboardState {

    SEEKING(5, "主板查找"),
    SET_SINGLE_WEIGHT(7, "设置单重"),
    SET_PHYSICAL_RANGE(8,"设置物理量程"),
    SET_BUSINESS_RANGE(9, "设置业务量程"),
    SET_MAINBOARD_STATE(10, "更改主板状态");
    private String remark;

    private int value;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    SetMainboardState(int value, String remark) {
        this.value =  value;
        this.remark = remark;
    }

    public static SetMainboardState getByValue(int value) {
        for (SetMainboardState o : SetMainboardState.values()) {
            if (o.getValue() == value) {
                return o;
            }
        }
        return null;
    }

}
