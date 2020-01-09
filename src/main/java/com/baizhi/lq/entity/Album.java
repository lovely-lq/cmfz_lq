package com.baizhi.lq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

//专辑
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable {
    @Id
    private String id;
    private String title;
    private String score;
    private String author;
    private String broadcast;
    private Integer acount;
    private String description;
    private String status;
    private String headSrc;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;


}
