package com.autoupdater.server.commands;

public interface PasswordCommandInterface {
    public String getPassword();

    public void setPassword(String password);

    public String getConfirmPassword();

    public void setConfirmPassword(String confirmPassword);
}
