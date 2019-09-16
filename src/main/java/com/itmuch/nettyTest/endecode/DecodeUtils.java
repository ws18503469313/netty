package com.itmuch.nettyTest.endecode;

import com.itmuch.nettyTest.endecode.enums.SNType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DecodeUtils  {


    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
        }
        return sb.toString().toUpperCase().trim();
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
     * 将ip转换为16进制
     * @param ipString
     * @return
     */
    public static String ipToLong(String ipString) {
        if(StringUtils.isBlank(ipString)){
            return null;
        }
        String[] ip=ipString.split("\\.");
        StringBuffer sb=new StringBuffer();
        for (String str : ip) {
            sb.append(Integer.toHexString(Integer.parseInt(str)));
        }
        return sb.toString();
    }

    private static Logger logger = LoggerFactory.getLogger(DecodeUtils.class);

    public static short[] bytesToShort(byte[] bytes) {
        if(bytes==null){
            return null;
        }
        short[] shorts = new short[bytes.length/2];
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
        return shorts;
    }
    public static byte[] shortToBytes(short[] shorts) {
        if(shorts==null){
            return null;
        }
        byte[] bytes = new byte[shorts.length * 2];
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(shorts);

        return bytes;
    }


    public static void main(String args[]) throws Exception{
        System.out.println(ipToLong("255.254.253.252"));
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
