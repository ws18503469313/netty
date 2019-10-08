package com.itmuch.nettyTest.endecode;

/**
 * 数据包信息
 */
final class PackageInfo {

    /**
     * 包头长度
     */
    static final int PACKAGE_HEAD_LENGTH = 2;
    /**
     * 网关/主板id长度
     */
    static final int SN_LENGTH = 4;

    /**
     * 表示实际数据长度
     */
    static final int DATA_LENGTH = 2;
    /**
     * 校验crc
     */
    static final int CRC_LENGTH = 2;
    /**
     * 数据类型长度
     */
    static final int TYPE_LENGTH = 1;

    static final int SET_MAIN_BOARD_STATE_LENGTH = 1;
    /**
     * 数据包的基础长度
     */
    static final int BASIC_PACKAGE_LENGTH = 11;
    /**
     * 升级包在系统中的路径
     */
    static final String packagePATH = "/data/updatepackage/";
    /**
     * 升级包版本长度
     */
    static final int PACKAGE_VERSION_LENGTH = 4;
    /**
     * U32升级包总长度（包头+数据+包尾）。（4）
     */
    static final int TOTAL_PACKAGE_LENGTH = 4;
    /**
     * 升级包名称前缀
     */
    static final String UPDATE_PACKAGE_NAME_PREFIX = "sykj_hardware";
}
