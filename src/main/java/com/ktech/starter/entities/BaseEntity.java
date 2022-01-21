package com.ktech.starter.entities;

import com.google.gson.Gson;
import com.ktech.starter.annotations.CreateTime;
import com.ktech.starter.annotations.EditTime;
import com.ktech.starter.dao.PersistenceListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners({ PersistenceListener.class})
@Getter
@Setter
public class BaseEntity {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="created_at")
    @CreateTime
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @EditTime
    private LocalDateTime updatedAt;

    public String asJson() {

        return new Gson().toJson(this);


    }

    public static <T> T asEntity(Class<T> clazz, String json) {

        return (T) new Gson().fromJson(json, clazz.getClass());
    }

    public <T> T copy() throws Exception {

        T ret = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {

            out.writeObject(this);

            try (ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
                 ObjectInputStream in = new ObjectInputStream(bin)) {
                ret = (T) in.readObject();
            }

        }
        catch (IOException | ClassNotFoundException e) {
            throw new Exception("Problem copying target to source: " + this.getClass().getSimpleName(), e);
        }

        return ret;



    }

}
