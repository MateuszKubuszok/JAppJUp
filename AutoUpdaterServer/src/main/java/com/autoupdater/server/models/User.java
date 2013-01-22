package com.autoupdater.server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.autoupdater.server.commands.PasswordCommandInterface;
import com.autoupdater.server.constraints.Password;
import com.autoupdater.server.constraints.PasswordConfirmation;
import com.autoupdater.server.constraints.UniqueUsername;
import com.autoupdater.server.utils.encryption.BCrypt;

/**
 * Model of User, used to log in and store authorities/roles.
 * 
 * @see com.autoupdater.server.services.UserService
 */
@Entity
@Table(name = "users")
@UniqueUsername
@PasswordConfirmation
public class User implements PasswordCommandInterface {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "username", unique = true)
    @NotNull
    @Length(min = 4, max = 20)
    private String username;

    @Column(name = "password")
    private String hashedPassword;

    @Transient
    @Password
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "name")
    @NotNull
    private String name = "";

    @Column(name = "admin")
    private boolean admin;

    @Column(name = "packageAdmin")
    boolean packageAdmin;

    public User() {
        // for the sake of saving edition
        password = "filler";
        confirmPassword = "filler";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
        // prevent @Password and @PasswordConfirmation from validating to false
        this.password = hashedPassword;
        this.confirmPassword = hashedPassword;
    }

    @Override
    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isPackageAdmin() {
        return packageAdmin;
    }

    public void setPackageAdmin(boolean packageAdmin) {
        this.packageAdmin = packageAdmin;
    }

    @Transient
    public EUserType getUserType() {
        return EUserType.parse(admin, packageAdmin);
    }

    public void setUserType(EUserType userType) {
        admin = userType.isAdmin();
        packageAdmin = userType.isPackageAdmin();
    }

    @Override
    public String toString() {
        return username;
    }
}
