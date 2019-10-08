package com.itmuch.nettyTest.endecode;


import com.itmuch.nettyTest.endecode.enums.SNType;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by apple on 2018/7/5.
 */
public class TcpSocketUtils {
	/**
	 *	把int转为指定个字节的byte数组
	 * @param iSource
	 * @param iArrayLen
	 * @return
	 */
    public static byte[] toByteArray(int iSource, int iArrayLen) {
        byte[] bLocalArr = new byte[iArrayLen];
        //一个int 有 4 个字节
        for (int i = 0; (i < 4) && (i < iArrayLen); i++) {
        	//先算8 * i
        	//再算 isSource >> 
        	//然后 再跟 oxff 按位与(转为int)
        	//最后再强转为byte
            bLocalArr[i] = (byte) (iSource >> 8 * i & 0xFF);
        }
        return bLocalArr;
    }

    public static String binary(byte[] bytes, int radix) {
        return new BigInteger(bytes).toString(radix);// 这里的1代表正数
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        // 由高位到低位
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    // 从byte数组的index处的连续4个字节获得一个float
    public static float getFloat(byte[] arr, int index) {
        return Float.intBitsToFloat(getFloatInt(arr, index));
    }

    // 从byte数组的index处的连续4个字节获得一个int
    public static int getFloatInt(byte[] arr, int index) {
        return (0xff000000 & (arr[index + 3] << 24)) |
                (0x00ff0000 & (arr[index + 2] << 16)) |
                (0x0000ff00 & (arr[index + 1] << 8)) |
                (0x000000ff & arr[index + 0]);
    }

    public static short[] bytesToShort(byte[] bytes) {
        if(bytes==null){
            return null;
        }
        short[] shorts = new short[bytes.length/2];
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
        return shorts;
    }

    private static final short PLOY= 4129;
    /**+
     *
     *  计算数据长度的crc
     * @param data  需要解析的业务数据
     * @param crc   不需要分布计算,传入的初始值为0,
     *              如果cpu计算能力不够,数据长度过长,需要分步计算crc,
     *              需要将上一次计算的crc作为参数传入下一次计算
     * @return
     */
    public static int crc(byte [] data, int crc){
        int i;
//        byte [] data = value.getBytes();
        for(int num = 0, len = data.length; num < len; num++ ){
            crc = (crc ^ (data[num] << 8 ));

            for(i = 0; i < 8; i++){
                if((crc & 0x8000) != 0)
                    crc = ((crc << 1) ^ PLOY);
                else
                    crc <<= 1;
            }
            crc &= 0xffff;
        }
        return crc;
    }

    /**
     * 构造包的时候,往包里写入的位置
     * 重写系统的 byte 数组拷贝方法,维护一个指针position,
     * 从 position 开始拷贝,拷贝完之后,将 position 先后移动 拷贝长度个位置
     * @param src
     * @param srcPo
     * @param dest
     * @param length
     * @param position
     */
    public static int arraycopy(byte [] src, int srcPo, byte [] dest, int position, int length){
        System.arraycopy(src, srcPo, dest, position, length);
        return position += length;
    }

    /**
     * 解析包的时候,解析到的位置
     * @param src
     * @param position
     * @param dest
     * @param destp
     * @param length
     * @return
     */
    public static int arraycopy0(byte [] src, int position, byte [] dest, int destp, int length){
        System.arraycopy(src, position, dest, destp, length);
        return position += length;
    }

    /**
     * 获取SN号的byte
     * @param SNId
     * @param type
     * @return
     */
    public static byte [] getSNByte(int SNId, SNType type){
        byte [] result = new byte[4];
        if(type.equals(SNType.MAIN_BOARD)){
            result[0] = 0;
        }else{
            result[0] = 1;
        }
        byte [] valueBytes = TcpSocketUtils.toByteArray(SNId, 3);
        System.arraycopy(valueBytes, 0, result, 1, 3);
        return result;
    }
}