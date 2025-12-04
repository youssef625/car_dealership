package com.swe2.model.dto;

import jakarta.validation.constraints.Size;
import org.checkerframework.checker.units.qual.min;

public class changePasswordDTO {

    @Size(min = 8, max = 24, message = "Old password must be between 8 and 24 characters")
    private String oldPassword;
    @Size(min = 8, max = 24, message = "Old password must be between 8 and 24 characters")
    private String newPassword;

    public changePasswordDTO() {}

    public changePasswordDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
