package com.ghosthawk.salard.Manager;

import android.provider.BaseColumns;

/**
 * Created by dongja94 on 2016-05-17.
 */
public class DataConstant {
//    public interface ChatUserTable extends BaseColumns {
//        public static final String TABLE_NAME = "chat_user_table";
//        public static final String COLUMN_SERVER_USER_ID = "sid";
//        public static final String COLUMN_NAME = "name";
//        public static final String COLUMN_EMAIL = "email";
//        public static final String COLUMN_LAST_MESSAGE_ID = "last_message";
//
//    }


    public interface ChatUserTable extends BaseColumns{
        public static final String TABLE_NAME = "CHAT_USER_TABLE";
        public static final String COLUMN_MEM_ID = "id";
        public static final String COLUMN_MEM_NAME = "mem_name";
        public static final String COLUMN_MEM_PICTURE = "mem_picture";
        public static final String COLUMN_MEM_SELL_COUNT = "mem_sellcount";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_LAST_MESSAGE = "last_message";

    }





    public interface ChatTable extends BaseColumns {
        public static final int TYPE_SEND = 1;
        public static final int TYPE_RECEIVE = 2;
//        상대닉네임
//        수신 발신 타입
//        메세지
//                시간
//        사진
//                        등급
//        읽음표시
        public static final String TABL_NAME = "CHAT_TABLE";
        public static final String COLUMN_USER_ID = "uid";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_DATE = "date_added";
        public static final String COLUMN_MEM_PICTURE = "mem_picture";
        public static final String COLUMN_MEM_SELL_COUNT = "mem_sellcount";
        public static final String COLUMN_READ = "read";
    }
}
