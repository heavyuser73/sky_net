package com.syaku.security.model.domain;

import javax.persistence.*;

/**
 * User: jhkim
 * Date: 16. 4. 8
 * Time: 오후 1:21
 */
@Entity
@Table(name = "user", schema = "", catalog = "sky_net")
public class UserEntity {
    private String email;
    private String session_id;


    @Id
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "session_id")
    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (session_id != null ? !session_id.equals(that.session_id) : that.session_id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result =  (email != null ? email.hashCode() : 0) + (session_id != null ? session_id.hashCode() : 0);
        return result;
    }
}
