package com.itmuch.nettyTest.endecode.enums;

/**
 * 下发类型
 * ---------------------------------------------------------------------------------------
 * 功能	                    |   TYPE	|DATA（字节）
 * ---------------------------------------------------------------------------------------
 * 主动获取                  |	0x00    |	U8:1为主动获取所有主板信息。（1）
 * ---------------------------------------------------------------------------------------
 * 设置频率                  |	0x01    |	U8:0~255s查看一次是否有更新值。（1）
 * ---------------------------------------------------------------------------------------
 * 服务器IP设置               |	0x04    |	U8[4]:服务器IP。（4）
 * ---------------------------------------------------------------------------------------
 * 升级包开始下发通知          |	0x05    |	U8[4]:升级包版本号。
 *                          |           |   (4）+U32升级包总长度（包头+数据+包尾）。（4）
 * ---------------------------------------------------------------------------------------
 * 升级包分包内容下发          |	0x06    |	U8[变长]：如升级包分包内容表所示。（变长）
 * ---------------------------------------------------------------------------------------
 * 设置主板信息               |	0x07    |	U8[变长]：如设置主板内容表所示。（变长）
 * ---------------------------------------------------------------------------------------
 * 告知网关主板个数和主板SN号   |	0x08    |	U8：主板个数。（1）+ U8[N*4]：N个主板SN号。（N*4）
 * ---------------------------------------------------------------------------------------
 */
public enum  DownStreamType {
    ACTIVE_APPLY(0, "主动获取"),
    SET_FREQUENCY(1, "设置频率"),
    SET_IP(4, "设置IP"),
    INFO_START_SEND(5, "升级包下发通知下发通知"),
    SEND_UPDATE_DATA(6, "升级包分包内容下发"),
    SET_MAIN_BOARD(7, "设置主板"),
    TELL_SN_NUM(8, "高柱网关主板个数和ID");

    private int value;

    private String remark;

    DownStreamType(int value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public static DownStreamType getByValue(int value) {
        for (DownStreamType o : DownStreamType.values()) {
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
