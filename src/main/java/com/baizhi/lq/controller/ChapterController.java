package com.baizhi.lq.controller;

import com.baizhi.lq.entity.Chapter;
import com.baizhi.lq.service.ChapterService;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @RequestMapping("queryById")
    public List<Chapter> queryById(String id) {
        List<Chapter> chapters = chapterService.queryById(id);
        return chapters;

    }

    @RequestMapping("save")
    public String saveChapter(Chapter c, String oper, String row_id) {
        if ("add".equals(oper)) {
            c.setAlbumId(row_id);

            chapterService.addChapter(c);
        } else if ("edit".equals(oper)) {
            if (c.getUrl() == "") {
                c.setUrl(null);
                c.setAlbumId(row_id);
                chapterService.updateChapter(c);

            } else {
                c.setAlbumId(row_id);
                chapterService.updateChapter(c);
            }
        } else {
            chapterService.deleteChapter(c.getId());
        }
        return c.getId();
    }

    @RequestMapping("/uploadChapter")
    public void uploadChapter(MultipartFile url, String id, HttpServletRequest request) throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException {
        chapterService.ChapterUpload(url, id, request);
    }

    @RequestMapping("dowloadChapter")
    public void dowloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        chapterService.dowloadChapter(url, response, session);
    }


}
