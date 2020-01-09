package com.baizhi.lq.service;

import com.baizhi.lq.aspects.Log;
import com.baizhi.lq.dao.ChapterDao;
import com.baizhi.lq.entity.Chapter;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    @Override
    public List<Chapter> queryById(String id) {
        List<Chapter> chapters = chapterDao.queryById(id);
        return chapters;
    }

    @Log(name = "章节的添加")
    @Override
    public void addChapter(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        System.out.println("业务层-----------" + chapter);
        chapterDao.addChapter(chapter);
    }

    @Log(name = "章节的修改")
    @Override
    public void updateChapter(Chapter chapter) {
        chapterDao.updateChapter(chapter);
    }

    @Log(name = "章节的删除")
    @Override
    public void deleteChapter(String id) {
        chapterDao.deleteChapter(id);
    }

    @Log(name = "章节的上传")
    @Override
    public void ChapterUpload(MultipartFile url, String id, HttpServletRequest request)
            throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        //获取http
        String scheme = request.getScheme();
        //获取IP
        String localhost = null;
        try {
            localhost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取端口号
        int serverPort = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();


        //根据相对路径获取绝对路径
        String realPath = request.getServletContext().getRealPath("/upload/photo/");
        File file = new File(realPath);
        //创建文件
        if (!file.exists()) {
            file.mkdir();
        }
        //获取文件名
        String filname = url.getOriginalFilename();
        //防止图片发生覆盖，重新给图片命名
        String newName = new Date().getTime() + "-" + filname;

        String uri = scheme + "://" + localhost.split("/")[1] + ":" + serverPort + contextPath + "/upload/photo/" + newName;
        //文件上传
        try {
            url.transferTo(new File(realPath, newName));
            //修改轮播图得信息
            Chapter c = new Chapter();
            c.setId(id);
            c.setUrl(uri);
            Double size = Double.valueOf(url.getSize() / 1024 / 1024);
            c.setCsize(size);
            String[] split = uri.split("/");
            String name = split[split.length - 1];
            // 通过文件获取AudioFile对象 音频解析对象
            AudioFile read = AudioFileIO.read(new File(realPath, name));
            // 通过音频解析对象 获取 头部信息 为了信息更准确 需要将AudioHeader转换为MP3AudioHeader
            MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
            // 获取音频时长 秒
            int trackLength = audioHeader.getTrackLength();
            String time = trackLength / 60 + "分" + trackLength % 60 + "秒";
            System.out.println(time);
            c.setTime(time);
            //调用修改方法
            chapterDao.updateChapter(c);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void dowloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        // 处理url路径 找到文件
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/upload/photo/");
        String name = split[split.length - 1];
        File file = new File(realPath, name);
        //设置响应头
        response.setHeader("Content-Disposition", "attachment; filename=" + name);
        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file, outputStream);

    }
}
