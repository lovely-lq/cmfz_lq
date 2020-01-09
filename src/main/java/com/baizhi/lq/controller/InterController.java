package com.baizhi.lq.controller;

import com.baizhi.lq.dao.*;
import com.baizhi.lq.entity.*;
import com.baizhi.lq.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("inter")
public class InterController {
    @Autowired
    AdminDao adminDao;
    @Autowired
    UserDAO userDAO;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ChapterDao chapterDao;
    @Autowired
    BannerDao bannerDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    CounterDao counterDao;
    @Autowired
    GuruDao guruDao;

    Jedis jedis = new Jedis("192.168.84.112", 7000);

    //登录
    @RequestMapping("login")
    public Map login(String username, String password) {
        HashMap hashMap = new HashMap();
        Admin admin = adminDao.selectByAdminName(username, password);
        if (admin != null) {
            if (admin.getPassword().equals(password)) {
                //登陆成功
                hashMap.put("status", 200);
                hashMap.put("msg", "登陆成功");
            } else {
                hashMap.put("status", 400);
                hashMap.put("msg", "密码错误");
            }
        } else {
            hashMap.put("status", 400);
            hashMap.put("msg", "该用户不存在");
        }

        return hashMap;
    }

    //文章
    @RequestMapping("queryArticle")
    public Map queryArticle(String id) {
        HashMap hashMap = new HashMap();
        Article article = articleDao.selectByOne(id);
        hashMap.put("status", 200);
        hashMap.put("article", article);

        return hashMap;
    }

    //专辑
    @RequestMapping("queryAblum")
    public Map queryAblum(String id) {
        HashMap hashMap = new HashMap();
        Album album = albumDao.selectByIdAlbum(id);
        List<Chapter> chapters = chapterDao.selectAllChapter(id);
        hashMap.put("status", 200);
        hashMap.put("album", album);
        hashMap.put("chapters", chapters);
        return hashMap;
    }

    @RequestMapping("onePage")
    public Map onePage(String type, String sub_type) {
        HashMap hashMap = new HashMap();
        try {
            if (type.equals("all")) {
                List<Banner> banners = bannerDao.selectAllBanner();
                List<Album> albums = albumDao.selectByDate();
                List<Article> articles = articleDao.selectArticleDate();
                hashMap.put("status", 200);
                hashMap.put("head", banners);
                hashMap.put("albums", albums);
                hashMap.put("articles", articles);
            } else if (type.equals("wen")) {
                List<Album> albums = albumDao.selectByDate();
                hashMap.put("status", 200);
                hashMap.put("albums", albums);
            } else {
                if (sub_type.equals("ssyj")) {
                    List<Article> articles = articleDao.selectArticleDate();
                    hashMap.put("status", 200);
                    hashMap.put("articles", articles);
                } else {
                    List<Article> articles = articleDao.selectArticleDate();
                    hashMap.put("status", 200);
                    hashMap.put("articles", articles);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
            hashMap.put("message", "error");
        }

        return hashMap;
    }


    @RequestMapping("sendCode")
    public Map sendCode(String phone) {

        //String s = UUID.randomUUID().toString();
        //String code = s.substring(0, 3);
        String code = "aser";
        jedis.set("code", code);
        SmsUtil.send(phone, code);
        // 将验证码保存值Redis  Key phone_186XXXX Value code 1分钟有效
        HashMap hashMap = new HashMap();
        hashMap.put("status", "200");
        hashMap.put("message", "短信发送成功");
        return hashMap;
    }

    //注册
    @RequestMapping("register")
    public Map register(String clientCode) {
        String code = jedis.get("code");
        HashMap hashMap = new HashMap();
        if (code.equals(clientCode)) {
            hashMap.put("status", "200");
            hashMap.put("msg", "验证码验证成功");
        } else {
            hashMap.put("status", "400");
            hashMap.put("msg", "验证码验证失败");
        }
        return hashMap;
    }

    //展示功课
    @RequestMapping("showAllCourse")
    public Map showAllCourse(String id) {
        List<Course> courses = courseDao.selectAll(id);
        HashMap hashMap = new HashMap();
        hashMap.put("status", "200");
        hashMap.put("course", courses);
        return hashMap;
    }

    //添加功课
    @RequestMapping("insertCourse")
    public Map insertCourse(@RequestBody Course c) {
        HashMap hashMap = new HashMap();
        System.out.println(c);
        courseDao.insert(c);
        hashMap.put("status", "200");
        hashMap.put("course", c);
        hashMap.put("msg", "添加成功");
        return hashMap;
    }

    //删除功课
    @RequestMapping("deleteCourse")
    public Map deleteCourse(String id) {
        HashMap hashMap = new HashMap();
        Course course = courseDao.selectById(id);
        courseDao.delete(id);
        hashMap.put("status", 200);
        hashMap.put("course", course);
        hashMap.put("msg", "删除成功");
        return hashMap;
    }

    //根据功课id查询计数器
    @RequestMapping("selectAllCounter")
    public Map selectAllCounter(String id) {
        HashMap hashMap = new HashMap();
        List<Counter> counters = counterDao.selectByCourseId(id);
        hashMap.put("status", 200);
        hashMap.put("counters", counters);
        return hashMap;
    }

    //添加计数器
    @RequestMapping("insertCounter")
    public Map insertCounter(@RequestBody Counter c) {
        HashMap hashMap = new HashMap();
        counterDao.insertCounter(c);
        hashMap.put("status", 200);
        hashMap.put("counter", c);
        hashMap.put("msg", "添加成功");
        return hashMap;
    }

    //删除
    @RequestMapping("deleteCounter")
    public Map deleteCounter(String id) {
        HashMap hashMap = new HashMap();
        counterDao.deleteCounter(id);
        hashMap.put("status", 200);
        hashMap.put("msg", "删除成功");
        return hashMap;
    }

    @RequestMapping("updateCounter")
    public Map updateCounter(String id, Integer count) {
        HashMap hashMap = new HashMap();
        counterDao.updateCounter(id, count);
        hashMap.put("status", 200);
        hashMap.put("msg", "修改成功");
        return hashMap;
    }


    //修改个人信息
    @RequestMapping("updateUser")
    public Map updateUser(@RequestBody User user) {
        HashMap hashMap = new HashMap();
        userDAO.updateUserMessage(user);
        System.out.println(user);
        hashMap.put("status", 200);
        hashMap.put("user", user);
        hashMap.put("msg", "修改成功");
        return hashMap;
    }

    //展示上师列表
    @RequestMapping("selectAllGuru")
    public Map selectAllGuru() {
        HashMap hashMap = new HashMap();
        List<Guru> gurus = guruDao.queryAll();
        hashMap.put("status", 200);
        hashMap.put("gurus", gurus);
        return hashMap;
    }

    //添加上师
    @RequestMapping("insertGuru")
    public Map insertGuru(@RequestBody Guru guru) {
        HashMap hashMap = new HashMap();
        guruDao.insertGuru(guru);
        hashMap.put("status", 200);
        hashMap.put("guru", guru);
        hashMap.put("msg", "添加成功");
        return hashMap;
    }

    //金刚道友
    @RequestMapping("selectUserNear")
    public Map selectUserNear(String id) {
        HashMap hashMap = new HashMap();
        List<User> users = userDAO.selectByNear(id);
        hashMap.put("status", 200);
        hashMap.put("users", users);
        return hashMap;
    }

    //补充个人信息
    @RequestMapping("updateUserMessage")
    public Map updateUserMessage(@RequestBody User user) {
        HashMap hashMap = new HashMap();
        userDAO.updateUserMessage(user);
        System.out.println(user);
        hashMap.put("status", 200);
        hashMap.put("user", user);
        hashMap.put("msg", "补充个人信息完成");
        return hashMap;
    }
}
