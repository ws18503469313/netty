package com.itmuch.nettyTest.endecode.enums;

/**
 * 上传数据类型:
 * ------------------------------------------------------------------------
 * 功能	               TYPE	            DATA（字节）
 * ------------------------------------------------------------------------
 * 数据上送	            0x01	        U8:上送数据的主板数量。
 *                                      （1）+U8[N*19]:N个主板的数据。（N*19）
 * -------------------------------------------------------------------------
 * 心跳上送	            0x02	        U8[4]:网关IP。（4）
 * ---------------------------------------------------------------------------
 * 开始升级	            0x03	        U8[4]:升级的版本号。（4）
 * ---------------------------------------------------------------------------
 * 申请下发升级包分包数据	0x04	        U8[4]:申请升级包的版本号。
                                        （4）+U32:申请数据在升级包的位置。
                                        （4）+U16:申请的数据长度。
                                        （2）+U8：申请升级包的分包号。（1）
 */
public enum UpstreamType {
    DATAUPSTREAM("01", "数据上传"),
    HEARTBEAT("02", "心跳"),
    STARTUPDATE("03", "开始升级"),
    APPLYDATA("04", "申请下发数据");

    private String remark;

    private int value;

    UpstreamType(String value, String remark) {
        this.remark = remark;
        this.value =  (int)Long.parseLong(value, 16);
    }

    public int getValue() {
        return value;
    }
    public String getRemark() {
        return remark;
    }

    public static UpstreamType getByValue(int value) {
        for (UpstreamType o : UpstreamType.values()) {
            if (o.getValue() == value) {
                return o;
            }
        }
        return null;
    }

}
