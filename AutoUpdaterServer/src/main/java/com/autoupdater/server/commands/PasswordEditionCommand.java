package com.autoupdater.server.commands;

import com.autoupdater.server.constraints.Password;
import com.autoupdater.server.constraints.PasswordConfirmation;
import com.autoupdater.server.constraints.PasswordCorrect;

/**
 * Command objects used for changing user's password.
 */
@PasswordCorrect
@PasswordConfirmation
public class PasswordEditionCommand implements PasswordCommandInterface {
    private int userId;

    private String currentPassword;

    @Password
    private String password;

    private String confirmPassword;

    public PasswordEditionCommand() {
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPw) {
        this.currentPassword = currentPw;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
