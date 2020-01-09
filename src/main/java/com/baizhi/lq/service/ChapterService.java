package com.baizhi.lq.service;

import com.baizhi.lq.entity.Chapter;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public interface ChapterService {
    //查单个
    public List<Chapter> queryById(String id);

    //添加
    public void addChapter(Chapter chapter);

    //修改
    public void updateChapter(Chapter chapter);

    //删除
    public void deleteChapter(String id);

    //文件上传
    public void ChapterUpload(MultipartFile url, String id, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException;

    //文件下载
    public void dowloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException;
}
