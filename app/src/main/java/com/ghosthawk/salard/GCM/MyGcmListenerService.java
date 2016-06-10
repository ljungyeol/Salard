/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ghosthawk.salard.GCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;


import com.ghosthawk.salard.Data.Member;
import com.ghosthawk.salard.Data.Message;
import com.ghosthawk.salard.Data.MessageResult;
import com.ghosthawk.salard.Login.LoginActivity;
import com.ghosthawk.salard.MainActivity;
import com.ghosthawk.salard.Manager.DataConstant;
import com.ghosthawk.salard.Manager.DataManager;
import com.ghosthawk.salard.Manager.NetworkManager;
import com.ghosthawk.salard.Message.ChattingActivity;
import com.ghosthawk.salard.R;
import com.google.android.gms.gcm.GcmListenerService;

import java.io.IOError;
import java.io.IOException;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    public static final String CHAT = "salard";
    public static final String EXTRA_SENDER_ID = "senderid";
    public static final String EXTRA_RESULT = "result";
    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String type = data.getString("type");
        String senderid = data.getString("sender");
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            if(type.equals("chat")){
                //--TODO 채팅
                Log.d(TAG,"채팅");
                String lastDate = DataManager.getInstance().getLastDate(senderid);
                Log.d(lastDate,"aaaaaaaaaaaaaaaaaaaa");
                try{
                    MessageResult result  = NetworkManager.getInstance().getMessageSync(lastDate);
                    String notiMessage = null;
                    Message u = null;
                    for (Message m : result.message){
                        String id = DataManager.getInstance().getUserTableId(m);
                        DataManager.getInstance().addChatMessage(id, m.member, DataConstant.ChatTable.TYPE_RECEIVE, m.msg_content, m.msg_date);



                        notiMessage = m.member.mem_name + ":" + m.msg_content;
                        u=m;
                    }
                    Intent intent = new Intent(CHAT);
                    intent.putExtra(EXTRA_SENDER_ID,senderid);
                    LocalBroadcastManager.getInstance(this).sendBroadcastSync(intent);
                    boolean isProcessed = intent.getBooleanExtra(EXTRA_RESULT,false);
                    if(!isProcessed){
                        sendMessageNotification(notiMessage,u);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            else if(type.equals("follow")){
                //-TODO 팔로우
                Log.d(TAG,"팔로우");
                sendNotification(message);
            }
            else if(type.equals("like")){
                //-TODO 찜하기
                Log.d(TAG,"찜하기");
                sendNotification(message);
            }
            else{
                Log.d(TAG,"실패");
            }
            // normal downstream message.
        }


    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */


    private void sendMessageNotification(String message, Message m) {
        Intent intent = new Intent(this, ChattingActivity.class);
        intent.putExtra(ChattingActivity.EXTRA_MESSAGE, m);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setTicker("GCM message")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("GCM ChatMessage")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }




    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setTicker("Salard 알림")
                .setSmallIcon(R.drawable.rating_bar_full)
                .setContentTitle("Salard로부터 알림이 도착했습니다.")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
