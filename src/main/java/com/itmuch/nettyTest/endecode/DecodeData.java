package com.itmuch.nettyTest.endecode;

import com.google.common.collect.Lists;
import com.itmuch.nettyTest.endecode.enums.DownStreamType;
import com.itmuch.nettyTest.endecode.enums.SNType;
import com.itmuch.nettyTest.endecode.enums.UpstreamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * 解析数据包
 * 而这里,知道源,所以可以借助于<@{@link java.nio.ByteBuffer }buffer来操作
 */
public class DecodeData {

    private static final Logger log = LoggerFactory.getLogger(DecodeData.class);

    /**
     *
     * @param data
     */
    public static void decodeData(byte[] data)  throws Exception  {
        //指针位置
        int position = 0;
        //开始校验包头
        while(true){
            byte[] head = new byte[PackageInfo.PACKAGE_HEAD_LENGTH];
            position = TcpSocketUtils.arraycopy0(data, position, head, 0, PackageInfo.PACKAGE_HEAD_LENGTH);
            position += PackageInfo.PACKAGE_HEAD_LENGTH;
            if (Arrays.equals(head, PackageHead())) {
                log.info("======================包头校验成功========================");
                break;
            }
        }

        //开始获取 网关号
        //首字节表示设备标志：0为网关，1为主板；其他字节表示生产号，两者组合表示SN号。
        //例如：生产号为32的网关，其SN号为0x00000020）
        int SNSign = data[position++];
        byte[] serialIdBytes = new byte[3];
        position = TcpSocketUtils.arraycopy0(data, position, serialIdBytes, 0, 3);
        Integer serialId = Integer.valueOf(new BigInteger(serialIdBytes).toString(10));

        //获取数据长度
        byte [] dateLenBytes = new byte[2];
        position = TcpSocketUtils.arraycopy0(data, position, dateLenBytes, 0, PackageInfo.DATA_LENGTH);
        int dataLen = TcpSocketUtils.bytesToShort(dateLenBytes)[0];

        //校验crc
        byte [] crcBytes = new byte[2];
        position = TcpSocketUtils.arraycopy0(data, position, crcBytes, 0, PackageInfo.CRC_LENGTH);
        int crc = TcpSocketUtils.bytesToShort(dateLenBytes)[0];
        //发送过来的数据类型
        int type = data[position++];
        //发送过来的业务数据
        byte [] dataBytes = new byte[dataLen];
        position = TcpSocketUtils.arraycopy0(data, position, dataBytes, 0, dataLen);
        int caculateCRC = TcpSocketUtils.crc(dataBytes, 0);

        if(crc != caculateCRC){
            log.info("校验完成,解析到的CRC:{},计算出的CRC:{}, 校验失败", crc, caculateCRC);
            return;
        }
        else log.info("校验完成,解析到的CRC:{},计算出的CRC:{}, 校验成功", crc, caculateCRC);
        DownStream downStream = null;
        byte [] reoponseByte = null;
        switch (UpstreamType.getByValue(type)){
            case DATAUPSTREAM:
                List<Scale> scaleList = resolvingData(dataBytes, serialId);
                ScaleDatas resolvedDatas = new ScaleDatas();
                resolvedDatas.setSerialId(serialId.toString());
                //TODO 将数据缓存到redis
                return;
            case STARTUPDATE:
                //开始升级表示网关接收网关升级包并校验通过后，开始自动升级时，会发送开始升级通知给平台。
                String version = TcpSocketUtils.binary(dataBytes, 10);
                downStream = new DownStream();
                reoponseByte= EncodeData.getApplyBytes(downStream);
                return;
            case HEARTBEAT:
                //data 里存放的是网关SN号,不需要解析数据
                log.info("===========this is the [{}] heartbeat", serialId);
                //TODO 将心跳时间记录下来定时检测
                return;
            case APPLYDATA:
                downStream = resolvingApplyInfo(dataBytes);
                downStream.setSerrialID(serialId);
                downStream.setDownStreamType(DownStreamType.SEND_UPDATE_DATA);
                reoponseByte = EncodeData.getApplyBytes(downStream);
                return;
        }


    }

    /**
     * 包头,先定义使用小端
     *
     * @return
     */
    public static byte[] PackageHead() {
        int head = 0x1acf;
        byte[] Bytes = TcpSocketUtils.toByteArray(head, 2);
        return Bytes;
       /*
        这是大端模式
        byte[] headBytes = new byte[2];
        headBytes[0] = Bytes[1];
        headBytes[1] = Bytes[0];
        return headBytes;
        */
    }

//    /**
//     *  获取deviceId, 网关号/主板号
//     * @param deviceId
//     * @return
//     */
//    private static long deviceSign(String deviceId){
//        char deviceSign = deviceId.charAt(0);
//        if( deviceSign == 1){
//            deviceId = deviceId.replaceFirst("1", "0");
//           log.info("这是主板:"+ deviceId);
//        }else{
//            log.info("这是网关:"+ deviceId);
//        }
//
//        deviceId = new BigInteger(deviceId, 16).toString(10);
//        return Long.valueOf(deviceId);
//    }

    /**
     * 解析获取上传数据
     * 每个主板上送的数据长度为19字节：4字节主板SN号，4字节重量，4字节单重，
     * 4字节最大业务量程,2字节数量，1字节状态（状态定义见主板状态表）。
     * @param data
     */
    private static List<Scale> resolvingData(byte [] data, Integer deviceId){
        List<Scale> scaleList = Lists.newArrayList();
        int position = 0;
        int scaleNum = data[position ++];

        for (int i = 0; i < scaleNum; i++) {//每个主板上送的数据长度为19字节
//            if (position % 19 != 0) {
//                continue;
//            }
//            if (data.length >= position + 19) {
                //主板SN号
            byte[] mainBordSN = new byte[4];
            position = TcpSocketUtils.arraycopy0(data, position, mainBordSN, 0, mainBordSN.length);
            //总重
            byte[] weight = new byte[4];
            position = TcpSocketUtils.arraycopy0(data, position, weight, 0, weight.length);
            //单重
            byte[] singleWeight = new byte[4];
            position = TcpSocketUtils.arraycopy0(data, position , singleWeight, 0, singleWeight.length);
            //最大业务量程
            byte[] maxWeightRange = new byte[4];
            position = TcpSocketUtils.arraycopy0(data, position , maxWeightRange, 0, maxWeightRange.length);
            //数量
            byte[] num = new byte[2];
            position = TcpSocketUtils.arraycopy0(data, position , num, 0, num.length);
            //状态
            int state = data[position++];

            Scale scale = new Scale();
            scale.setAddress485(Integer.valueOf(TcpSocketUtils.binary(mainBordSN ,10)));
            scale.setMaxWeightRange(TcpSocketUtils.binary(maxWeightRange, 10));
            scale.setSingleWeight(String.valueOf(TcpSocketUtils.getFloat(singleWeight, 0)));//TcpSocketUtils.binary(SingleWeight, 10)
            scale.setWeight(TcpSocketUtils.binary(weight, 10));
            scale.setSerialId(deviceId.toString());
            scale.setState(state);
            scaleList.add(scale);
//            }
        }
        return scaleList;
    }

    /**
     *  解析下发升级包分包数据请求
     * @param data
     */
    private static DownStream resolvingApplyInfo(byte [] data){

        //升级版本号
        StringBuilder version = new StringBuilder(PackageInfo.UPDATE_PACKAGE_NAME_PREFIX);
        int snType = data[0];
        version.append(SNType.getByValue(snType).getRemark());
        version.append(data[1]);
        version.append(".");
        version.append(data[2]);
        version.append(".");
        version.append(data[3]);
        int position = 0;
        byte [] versionBytes = new byte[4];
        position = TcpSocketUtils.arraycopy0(data, 0, versionBytes, 0, PackageInfo.PACKAGE_VERSION_LENGTH);
        //获取的数据位置
        byte [] dataPositionBytes = new byte[4];
        position = TcpSocketUtils.arraycopy0(data, position, dataPositionBytes, 0, dataPositionBytes.length);
        int dataPosition = Integer.valueOf(TcpSocketUtils.binary(dataPositionBytes, 10));
        //申请的长度
        byte [] applyLenBytes = new byte[2];
        position = TcpSocketUtils.arraycopy0(data, position, applyLenBytes, 0, applyLenBytes.length);
        int applyLen = Integer.valueOf(TcpSocketUtils.binary(applyLenBytes, 10));

        int applyPackageNum = data[position];
        DownStream downStream = new DownStream();
        downStream.setPackageName(version.toString());
        downStream.setVersionBytes(versionBytes);
        downStream.setDataPosition(dataPosition);
        downStream.setApplyLen(applyLen);
        return downStream;
    }


}
