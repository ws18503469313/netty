package com.itmuch.nio.selecotr.fullexample;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CharsetTest {


    public static void main(String args[]) throws Exception{
        String infile = "charset.txt";
        String outfile = "charsetout.txt";
        RandomAccessFile in = new RandomAccessFile(infile, "r");
        RandomAccessFile out = new RandomAccessFile(outfile, "rw");

        long readLength = new File(infile).length();
        FileChannel inchannel =  in.getChannel();
        FileChannel outchannel = out.getChannel();

        MappedByteBuffer buffer = inchannel.map(FileChannel.MapMode.READ_ONLY, 0, readLength);

        Charset charset = Charset.forName("iso-8859-1");

        CharsetEncoder encoder = charset.newEncoder();

        CharsetDecoder decoder = charset.newDecoder();

        CharBuffer charBuffer = decoder.decode(buffer);

        ByteBuffer outBuffer = encoder.encode(charBuffer);

        outchannel.write(outBuffer);

        inchannel.close();
        outchannel.close();





    }
}
