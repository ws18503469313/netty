package com.itmuch.nettyTest.endecode;


import java.math.BigInteger;

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





}