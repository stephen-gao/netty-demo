package com.gs.netty.common.protocol;

import java.nio.ByteBuffer;

/**
 * 协议定义
 *
 * @author GaoSheng
 * @version 1.0
 * @blame GaoSheng
 * @since 2020/06/24 10:37
 **/
public class Protocol {

    public static final byte FIRST_BYTE = 0X01;
    public static final byte SECOND_BYTE = 0X09;
    public static final byte THIRD_BYTE = 0X09;
    public static final byte FOUR_BYTE = 0X01;

    public static final byte PROTOCOL_V_BYTE = 0X01;

    public static final int PROTOCOL_VERSION_LENGTH = 1;

    public static final int SERIALIZE_TYPE_LENGTH = 1;

    public static final int ENCRYPTION_TYPE_LENGTH = 1;

    public static final int MESSAGE_TYPE_LENGTH = 1;

    public static final int PROTOCOL_CMD_LENGTH = 4;

    public static final int WHITE_LENGTH = 16;

    public static final int DATA_LENGTH_LENGTH = 4;

    public static final int MESSAGE_ID_LENGTH = 32;

    public static final int FROM_LENGTH = 32;

    public static final int TO_LENGTH = 32;


    public static void main(String[] args) {
        byte[] b = {FIRST_BYTE, SECOND_BYTE, THIRD_BYTE, FOUR_BYTE};
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put(FIRST_BYTE);
        byteBuffer.put(SECOND_BYTE);
        byteBuffer.put(THIRD_BYTE);
        byteBuffer.put(FOUR_BYTE);
        byteBuffer.putInt(1991);

        System.out.println(byteBuffer.get());
        int j = byteBuffer.getInt();
        System.out.println(j);
    }

}
