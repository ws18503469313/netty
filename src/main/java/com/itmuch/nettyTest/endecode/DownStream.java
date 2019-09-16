package com.itmuch.nettyTest.endecode;

import com.itmuch.nettyTest.endecode.enums.DownStreamType;
import com.itmuch.nettyTest.endecode.enums.SetMainboardState;

import java.util.List;

/**
 * 下发数据 的传参封装对象
 */
public class DownStream {
    /**
     * 网关SN
     */
    private int serrialID;
    /**
     * 要设置状态的主板SN号
     * 目前是只能设置单个的
     * 因为EDP协议的命令长度有限制
     * 好像最大是1000字节，也算是协议端尽量控制防止超限吧
     */
    private Integer mainBoardSN;
    /**
     * 下发
     * 告知网关主板个数和主板SN号
     */
    private List<Integer> setMainBoardSN;
    /**
     * 下发类型
     */
    private DownStreamType downStreamType;
    /**
     * 设置主板类型
     */
    private SetMainboardState setMainboardState;
    /**
     * 设置多久查看一次是否有更新值
     */
    private Integer frequency;
    /**
     * 升级包版本名称
     */
    private String packageName;
    /**
     * 版本号原始数组
     */
    private byte[] versionBytes;
    /**
     * 开始获取数据在数据包中的位置
     */
    private Integer dataPosition;
    /**
     * 申请的长度
     */
    private Integer applyLen;

    public int getSerrialID() {
        return serrialID;
    }

    public void setSerrialID(int serrialID) {
        this.serrialID = serrialID;
    }

    public Integer getMainBoardSN() {
        return mainBoardSN;
    }

    public void setMainBoardSN(Integer mainBoardSN) {
        this.mainBoardSN = mainBoardSN;
    }

    public DownStreamType getDownStreamType() {
        return downStreamType;
    }

    public void setDownStreamType(DownStreamType downStreamType) {
        this.downStreamType = downStreamType;
    }

    public SetMainboardState getSetMainboardState() {
        return setMainboardState;
    }

    public void setSetMainboardState(SetMainboardState setMainboardState) {
        this.setMainboardState = setMainboardState;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public List<Integer> getSetMainBoardSN() {
        return setMainBoardSN;
    }

    public void setSetMainBoardSN(List<Integer> setMainBoardSN) {
        this.setMainBoardSN = setMainBoardSN;
    }

    public Integer getDataPosition() {
        return dataPosition;
    }

    public void setDataPosition(Integer dataPosition) {
        this.dataPosition = dataPosition;
    }

    public Integer getApplyLen() {
        return applyLen;
    }

    public void setApplyLen(Integer applyLen) {
        this.applyLen = applyLen;
    }

    public byte[] getVersionBytes() {
        return versionBytes;
    }

    public void setVersionBytes(byte[] versionBytes) {
        this.versionBytes = versionBytes;
    }
}
