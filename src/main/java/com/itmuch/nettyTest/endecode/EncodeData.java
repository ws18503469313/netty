package com.itmuch.nettyTest.endecode;

import com.itmuch.nettyTest.endecode.enums.DownStreamType;
import com.itmuch.nettyTest.endecode.enums.SNType;
import com.itmuch.nettyTest.endecode.enums.SetMainboardState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 打包数据包
 *
 * 设置主板内容表（DATA部分内容）
 * --------------------------------------------------------------------------------------------
 * 包头（SHEAD） |	网关SN号（SSNID）	|长度（SLEN）|	校验（SCRC）|	类型（STYPE）|	数据（SDATA）
 * ---------------------------------------------------------------------------------------------
 * 0x1ACF	    |   U8[4]	        |U16	    |U16	       |         U8     |	U8[SLEN]
 * --------------------------------------------------------------------------------------------
 * 因为有时候不知道要声明多大的<@{@link java.nio.ByteBuffer>}buffer,否则这里完全借助于buffer来操作,让buffer来维护position,
 */
public class EncodeData {

    private static final Logger log = LoggerFactory.getLogger(EncodeData.class);

    /**
     * U8：主板个数。（1）+ U8[N*4]：N个主板SN号。（N*4）
     * [-49, 26, 102, 39, 0, 0, 12, 0, 74, -66, 7, -49, 26, 1, 0, 0, 0, 1, 0, -91, 80, 5, 5]
     * @param downStream 下发数据
     */
    public static byte [] getApplyBytes(DownStream downStream )  throws Exception {
        //包头
        byte[] packageHead = TcpSocketUtils.toByteArray(0x1acf, PackageInfo.PACKAGE_HEAD_LENGTH);
        //网关id
        byte[] serrialIDBytes = DecodeUtils.getSNByte(downStream.getSerrialID(), SNType.ZUUL);
        serrialIDBytes[0] = 0;
        //data长度
        int len = 0;
        //具体数据
        byte [] data = null;
        switch (downStream.getDownStreamType()){
            case SET_MAIN_BOARD:
                int position = 0;
                len = PackageInfo.BASIC_PACKAGE_LENGTH + 1;
                data = new byte[len];
                //包头
                position = DecodeUtils.arraycopy(packageHead, 0, data, position, PackageInfo.PACKAGE_HEAD_LENGTH);
                //主板SN号SSNID
//                byte [] mainBoardSNbytes = TcpSocketUtils.toByteArray(downStream.getMainBoardSN(), PackageInfo.SN_LENGTH);
                byte [] mainBoardSNbytes = DecodeUtils.getSNByte(downStream.getMainBoardSN(), SNType.MAIN_BOARD);
                position = DecodeUtils.arraycopy(mainBoardSNbytes, 0, data, position, PackageInfo.SN_LENGTH);
                //SDATA 的长度 SLEN
                byte [] lenBytes = TcpSocketUtils.toByteArray(1, PackageInfo.DATA_LENGTH);
                position = DecodeUtils.arraycopy(lenBytes, 0, data, position, PackageInfo.DATA_LENGTH);
                //数据SDATA
                byte [] sdata = TcpSocketUtils.toByteArray(downStream.getSetMainboardState().getValue(), PackageInfo.SET_MAIN_BOARD_STATE_LENGTH);

                //SCRC
                int crc = DecodeUtils.crc(sdata,0);
                byte [] scrc = TcpSocketUtils.toByteArray(crc, PackageInfo.CRC_LENGTH);
                position = DecodeUtils.arraycopy(scrc, 0, data, position, PackageInfo.CRC_LENGTH);
                //STYPE
                byte [] stype = TcpSocketUtils.toByteArray(downStream.getSetMainboardState().getValue(), PackageInfo.TYPE_LENGTH);
                position = DecodeUtils.arraycopy(stype, 0, data, position, PackageInfo.TYPE_LENGTH);
                //最后设置数据sdata
                position = DecodeUtils.arraycopy(sdata, 0, data, position, PackageInfo.SET_MAIN_BOARD_STATE_LENGTH);
                break;
            case ACTIVE_APPLY:
                len = 1;
                data = new byte[1];
                //U8:1为主动获取所有主板信息。
                sdata = TcpSocketUtils.toByteArray(1, 1);
                System.arraycopy(sdata, 0, data, 0, sdata.length);
                break;
            case SET_FREQUENCY:
                len = 1;
                data = new byte[1];
                //0~255s查看一次是否有更新值。（1）
                sdata = TcpSocketUtils.toByteArray(downStream.getFrequency(), 1);
                System.arraycopy(sdata, 0, data, 0, sdata.length);
                break;
            case INFO_START_SEND://U8[4]:升级包版本号。（4）+U32升级包总长度（包头+数据+包尾）。（4）
                len = 8;
                data = new byte[8];
                System.arraycopy(downStream.getVersionBytes(), 0, data, 0, PackageInfo.PACKAGE_VERSION_LENGTH);
                //我们提供给你的升级包应该已经包含这三部分，所以对于平台来说，提供的升级包总长度应该就是长度值
                File file = new File(PackageInfo.packagePATH + downStream.getPackageName());
                int totalPackageLength = (int)file.length() * 1024;
                byte [] totalPackageLengthBytes = TcpSocketUtils.toByteArray(totalPackageLength, 4);
                System.arraycopy(totalPackageLengthBytes, 0, data, 4, 4);
                break;
            case TELL_SN_NUM:
                len = 1 + downStream.getSetMainBoardSN().size() * PackageInfo.SN_LENGTH;
                data = new byte[len];
                position = 0;
                //主板个数
                System.arraycopy(TcpSocketUtils.toByteArray(downStream.getSetMainBoardSN().size(), 1), 0, data, position ++, 1);
                //主板SN号
                for(Integer sn: downStream.getSetMainBoardSN()){
//                    byte [] mainBoardSNBytes = TcpSocketUtils.toByteArray(sn, PackageInfo.SN_LENGTH);
                    byte [] mainBoardSNBytes = DecodeUtils.getSNByte(sn, SNType.MAIN_BOARD);
                    mainBoardSNBytes[0] = 1;
                    System.arraycopy(mainBoardSNBytes, 0, data, position, PackageInfo.SN_LENGTH);
                    position += PackageInfo.SN_LENGTH;
                }
                break;
            case SEND_UPDATE_DATA:
               data = constructorSubPackageContent(downStream);
               len = data.length;
               break;
            case SET_IP:
                //暂时用不到,保留接口
                break;


        }
        //表示数据区长度的byte
        byte [] lenBytes = TcpSocketUtils.toByteArray(len, PackageInfo.DATA_LENGTH);
        //CRC
        int crc = DecodeUtils.crc(data, 0);
        byte [] CRCBytes = TcpSocketUtils.toByteArray(crc, 2);
        //type
        byte [] typeBytes = TcpSocketUtils.toByteArray(downStream.getDownStreamType().getValue(), 1);
        //数据包
        byte [] packageBytes = new byte[PackageInfo.BASIC_PACKAGE_LENGTH + data.length];
        //数据包指针
        int packagePosition  = 0;
        packagePosition = DecodeUtils.arraycopy(packageHead, 0, packageBytes, packagePosition, PackageInfo.PACKAGE_HEAD_LENGTH);
        packagePosition = DecodeUtils.arraycopy(serrialIDBytes, 0, packageBytes, packagePosition, PackageInfo.SN_LENGTH);
        packagePosition = DecodeUtils.arraycopy(lenBytes, 0, packageBytes, packagePosition, PackageInfo.DATA_LENGTH);
        packagePosition = DecodeUtils.arraycopy(CRCBytes, 0, packageBytes, packagePosition, PackageInfo.CRC_LENGTH);
        packagePosition = DecodeUtils.arraycopy(typeBytes, 0, packageBytes, packagePosition, PackageInfo.TYPE_LENGTH);
        DecodeUtils.arraycopy(data, 0, packageBytes, packagePosition, data.length);
        return packageBytes;
    }


    public static void main(String args[]) throws Exception{
        DownStream downStream = new DownStream();
        downStream.setSerrialID(10086);
        downStream.setDownStreamType(DownStreamType.SET_MAIN_BOARD);
        downStream.setMainBoardSN(1);
        downStream.setSetMainboardState(SetMainboardState.SEEKING);
        getApplyBytes(downStream);
//        File file = new File("D:/settings.zip");
//        System.out.println(file.length());
    }

    /**
     * 构造分包发送数据的具体数据信息
     * @param downStream
     * @return
     * @throws Exception
     */
    private static byte [] constructorDetailUpdatePackage(DownStream downStream)  throws Exception {
        byte [] result = new byte[2 + 4 + 4 + 2 + downStream.getApplyLen() + 2];
        int position = 0;
        //包头
        byte [] headbytes = TcpSocketUtils.toByteArray(0X3AFD, PackageInfo.PACKAGE_HEAD_LENGTH);
        position = DecodeUtils.arraycopy(headbytes, 0, result, position, PackageInfo.PACKAGE_HEAD_LENGTH);
        //版本号
        position = DecodeUtils.arraycopy(downStream.getVersionBytes(), 0, result, position, PackageInfo.PACKAGE_VERSION_LENGTH);
        //数据区长度的bytes
        byte [] lenBytes = TcpSocketUtils.toByteArray(downStream.getApplyLen(), 4);
        //f分包内容长度为4
        position = DecodeUtils.arraycopy(lenBytes, 0, result, position, 4);
        //NIO方式
        RandomAccessFile file = new RandomAccessFile(PackageInfo.packagePATH + downStream.getPackageName(), "r");
        FileChannel channel = file.getChannel();
        MappedByteBuffer buffer  =  channel.map(FileChannel.MapMode.READ_ONLY,downStream.getDataPosition(), downStream.getApplyLen() );
        byte [] dataBytes = buffer.array();
        if(log.isInfoEnabled())
            log.info("==========分包发送的具体数据: {}", dataBytes);
        //CRC
        byte [] CRCBytes = TcpSocketUtils.toByteArray(DecodeUtils.crc(dataBytes,0), PackageInfo.CRC_LENGTH);
        position = DecodeUtils.arraycopy(CRCBytes, 0, result, position, PackageInfo.CRC_LENGTH);
        //DATA
        position = DecodeUtils.arraycopy(dataBytes, 0, result, position, dataBytes.length);
        //包尾
        byte [] tailBytes = TcpSocketUtils.toByteArray(0XFD3A, PackageInfo.PACKAGE_HEAD_LENGTH);
        position = DecodeUtils.arraycopy(tailBytes, 0, result, position, PackageInfo.PACKAGE_HEAD_LENGTH);
        return result;

    }

    /**
     * 构造分包内容表
     * @param downStream
     * @return
     * @throws Exception
     */
    private static byte [] constructorSubPackageContent(DownStream downStream)  throws Exception {
        byte [] detailUpdatePackageBytes = constructorDetailUpdatePackage(downStream);
        byte []  result = new byte [2 + 4 + 2 + 2 + 1 + detailUpdatePackageBytes.length];
        int position = 0;
        //包头
        byte [] headBytes = TcpSocketUtils.toByteArray(0X1ACF, PackageInfo.PACKAGE_HEAD_LENGTH);
        position = DecodeUtils.arraycopy(headBytes, 0, result, position, PackageInfo.PACKAGE_HEAD_LENGTH);
        //版本号
        position = DecodeUtils.arraycopy(downStream.getVersionBytes(), 0, result, position, PackageInfo.PACKAGE_VERSION_LENGTH);
        //数据区长度
        position = DecodeUtils.arraycopy(TcpSocketUtils.toByteArray(detailUpdatePackageBytes.length, 2),
                0,  result, position, PackageInfo.DATA_LENGTH);
        //CRC
        byte [] CRCBytes = TcpSocketUtils.toByteArray(DecodeUtils.crc(detailUpdatePackageBytes, 0), PackageInfo.CRC_LENGTH);
        position = DecodeUtils.arraycopy(CRCBytes, 0 , result, position, PackageInfo.CRC_LENGTH);
        //包编号 U8
        byte [] packageNumBytes = TcpSocketUtils.toByteArray(downStream.getDataPosition() / 1024, 1);
        position = DecodeUtils.arraycopy(packageNumBytes,0 , result, position, 1);
        //data
        DecodeUtils.arraycopy(detailUpdatePackageBytes, 0 , result, position, detailUpdatePackageBytes.length);
        return result;
    }

//    /**
//     * 构造返回给onennet的分包数据
//     * @param downStream
//     * @return
//     * @throws Exception
//     */
//    private static byte [] getFullSubUpdatePackage(DownStream downStream)  throws Exception {
//        byte [] subPackageContent = constructorSubPackageContent(downStream);
//        byte [] result = new byte [PackageInfo.BASIC_PACKAGE_LENGTH + subPackageContent.length];
//        int position = 0;
//        //包头
//        byte[] packageHead = TcpSocketUtils.toByteArray(0x1acf, PackageInfo.PACKAGE_HEAD_LENGTH);
//        position = DecodeUtils.arraycopy(packageHead, 0, result, position, PackageInfo.PACKAGE_HEAD_LENGTH);
//        //网关号
//        position = DecodeUtils.arraycopy(TcpSocketUtils.toByteArray(downStream.getSerrialID(), PackageInfo.SN_LENGTH),
//                0, result, position, PackageInfo.SN_LENGTH);
//        //数据长度
//        position = DecodeUtils.arraycopy(TcpSocketUtils.toByteArray(subPackageContent.length, PackageInfo.DATA_LENGTH),
//                0, result, position, PackageInfo.DATA_LENGTH);
//        //CRC
//        position = DecodeUtils.arraycopy(TcpSocketUtils.toByteArray(DecodeUtils.crc(subPackageContent, 0), PackageInfo.CRC_LENGTH),
//                0, result, position, PackageInfo.CRC_LENGTH);
//        //type
//        position = DecodeUtils.arraycopy(TcpSocketUtils.toByteArray(downStream.getDownStreamType().getValue(), PackageInfo.TYPE_LENGTH),
//                0, result, position , PackageInfo.TYPE_LENGTH);
//        DecodeUtils.arraycopy(subPackageContent, 0, result, position, subPackageContent.length);
//
//        return result;
//    }

}
