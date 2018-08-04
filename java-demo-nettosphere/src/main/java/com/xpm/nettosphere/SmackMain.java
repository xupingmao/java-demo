package com.xpm.nettosphere;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.chat.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.DNSUtil;
import org.jivesoftware.smack.util.dns.DNSResolver;
import org.jivesoftware.smack.util.dns.HostAddress;
import org.jivesoftware.smack.util.dns.SRVRecord;
import org.jivesoftware.smack.util.stringencoder.Base64;
import org.jivesoftware.smack.util.stringencoder.Base64UrlSafeEncoder;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xupingmao on 2017/8/30.
 */
public class SmackMain {

    public static void main(String[] args) throws IOException, InterruptedException, XMPPException, SmackException {
        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        InetAddress address = Inet4Address.getByName("192.168.0.113");
        builder.setHost("192.168.0.113")
                .setHostAddress(address)
                .setUsernameAndPassword("mark", "123456")
                .setXmppDomain("localhost")
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setDnssecMode(ConnectionConfiguration.DnssecMode.disabled);
        Base64.setEncoder(new Base64.Encoder() {
            @Override
            public byte[] decode(String string) {
                return java.util.Base64.getDecoder().decode(string);
            }

            @Override
            public byte[] decode(byte[] input, int offset, int len) {
                byte[] newBytes = new byte[len];
                System.arraycopy(input, offset, newBytes, 0, len);
                return java.util.Base64.getDecoder().decode(newBytes);
            }

            @Override
            public String encodeToString(byte[] input, int offset, int len) {
                byte[] newBytes = new byte[len];
                System.arraycopy(input, offset, newBytes, 0, len);
                return java.util.Base64.getEncoder().encodeToString(newBytes);
            }

            @Override
            public byte[] encode(byte[] input, int offset, int len) {
                byte[] newBytes = new byte[len];
                System.arraycopy(input, offset, newBytes, 0, len);
                return java.util.Base64.getEncoder().encode(newBytes);
            }
        });
        AbstractXMPPConnection connection = new XMPPTCPConnection(builder.build());

        try {
            connection.connect().login();
//            org.jivesoftware.smack.chat.ChatManager instanceFor = org.jivesoftware.smack.chat.ChatManager.getInstanceFor(connection);
            Message message = new Message("mark02",
                    "Howdy! How are you?");
            connection.setFromMode(XMPPConnection.FromMode.USER);
            connection.sendStanza(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }
}
