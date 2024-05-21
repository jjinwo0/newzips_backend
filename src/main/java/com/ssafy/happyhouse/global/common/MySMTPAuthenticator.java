package com.ssafy.happyhouse.global.common;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MySMTPAuthenticator extends Authenticator {

    @Override
    public PasswordAuthentication getPasswordAuthentication() {

        return new PasswordAuthentication("ajsjdlwj0123","scdkvifclnxkmkcu");

    }

}
