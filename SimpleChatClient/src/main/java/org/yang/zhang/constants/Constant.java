package org.yang.zhang.constants;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 13 17:23
 */

public class Constant {
//    public static String serverHost="http://47.52.164.19:9888";
    public static String serverHost="http://127.0.0.1:9888";
    public static String serverHost1="http://192.168.0.111:9888";
    public static final String LoginUrl=serverHost+"/login";
    public static final String QrCodeLoginUrl=serverHost1+"/loginByQrCode";
    public static final String registerQrCode=serverHost1+"/registerQrCode";
    public static final String FindPassword=serverHost+"/findPassWord?userId={userId}";
    public static final String ContractUrl=serverHost+"/chat/contract";
    public static final String ONEDAYCHATLOGURL=serverHost+"/chat/oneDayChatLog";
    public static final String ONEMONTHCONTRACT=serverHost+"/chat/oneMonthContract";
    public static final String RECOMMONDCONTRACT=serverHost+"/chat/recommondContract";
    public static final String SEARCHCONTRACT=serverHost+"/chat/searchContract";
    public static final String NEWGROUP=serverHost+"/chat/newGroup";
    public static final String UPDATECONTRACTGROUP=serverHost+"/chat/updateContractGroup";
    public static final String UPDATEGROUP=serverHost+"/chat/updateGroup";
    public static final String DELETEGROUP=serverHost+"/chat/deleteGroup";
    public static final String DELETEFRIEND=serverHost+"/chat/deleteFriend";
    public static final String ADDFRIEND=serverHost+"/chat/addFriend";
    public static final String GETCHATROOMDETAIL=serverHost+"/chat/getRoomDetail";
    public static final String USERREGISTER=serverHost+"/user/register";
    public static final String USERCHATROOMS=serverHost+"/chat/getUerChatRooms";
    public static final String REGEIST="REGEIST";

    public static String fileRoot="D:\\simpleChatFiles";
    public static String historyUserFileName="historyUser.txt";
    public static String SystemConfigFileName="systemConfig.txt";
    public static String QrCodeFile="qrcode.jpg";
    public static String trayFile="QQ.png";
    public static String STATUS_ONLINE_ICON="images/icon/online_status.png";
    public static String STATUS_BUSY_ICON="images/icon/busy_status.png";
    public static String STATUS_INVISIBLE_ICON="images/icon/invisiable_status.png";
    public static String DEFAULT_FILE_ICON="images/icon/file.png";

    public static String GROUP_PREFIX="GROUP_";
    public static String FILE_PROTOTAL="file:";



}
