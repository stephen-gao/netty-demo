package com.gs.netty.common.protocol;

/**
 * ...
 *
 * @author GaoSheng
 * @version 1.0
 * @blame GaoSheng
 * @since 2020/06/24 14:43
 **/
public enum CmdEnum {
    /**
     * 登录消息
     */
    LOGIN_TYPE(CmdEnum.SYSTEM, 1),
    LOGOUT_TYPE(CmdEnum.SYSTEM, 2),
    HEART_TYPE(CmdEnum.SYSTEM, 3),
    EVENT_TYPE(CmdEnum.BIZ, 1),
    ALERT_TYPE(CmdEnum.BIZ, 2),
    C2C_TYPE(CmdEnum.CHAT, 1),
    GROUP_TYPE(CmdEnum.CHAT, 2),
    CUSTOM_TYPE(CmdEnum.CUSTOM, 1),
    ;

    public static final int SYSTEM = 1;

    public static final int BIZ = 2;

    public static final int CHAT = 3;

    public static final int CUSTOM = 3;

    CmdEnum(int cmd, int subcmd) {
        this.cmd = cmd;
        this.subcmd = subcmd;
    }

    private int cmd;

    private int subcmd;

    public int getCmd() {
        return cmd;
    }

    public int getSubcmd() {
        return subcmd;
    }

    public static CmdEnum getCmd(int cmd, int subcmd){
        CmdEnum[] cmdEnums = values();
        for(CmdEnum o : cmdEnums){
            if(o.getCmd() == cmd && o.getSubcmd() == subcmd){
                return o;
            }
        }
        return null;
    }
}
