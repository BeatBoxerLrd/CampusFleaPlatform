package com.derry.service.impl;

import com.github.pagehelper.PageHelper;
import com.derry.dao.UserMapper;
import com.derry.pojo.User;
import com.derry.service.UserService;
import com.derry.util.WriteExcel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getUserByPhone(String phone) {
        User user  = userMapper.getUserByPhone(phone);
        return  user;
    }
    @Override
    public void updateUserName(User  user) {
        userMapper.updateByPrimaryKey(user);
    }
    @Override
    public int updateGoodsNum(Integer id,Integer goodsNum) {
        return userMapper.updateGoodsNum(id,goodsNum);
    }
    @Override
    public User selectByPrimaryKey(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
    @Override
    //获取出当前页用户
    public List<User> getPageUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);//分页核心代码
        List<User> data= userMapper.getUserList();
        return data;
    }
    @Override
    //获取出用户的数量
    public int getUserNum() {
        List<User> users = userMapper.getUserList();
        return users.size();
    }
    @Override
    public InputStream getInputStream() throws Exception {
        String[] title=new String[]{"序号","手机号","姓名","QQ","开通时间","商品数量","用户权限"};
        List<User> list=userMapper.getUserList();
        List<Object[]>  dataList = new ArrayList<Object[]>();
        for(int i=0;i<list.size();i++){
            Object[] obj=new Object[7];
            obj[0]=list.get(i).getId();
            obj[1]=list.get(i).getPhone();
            obj[2]=list.get(i).getUsername();
            obj[3]=list.get(i).getQq();
            obj[4]=list.get(i).getCreateAt();
            obj[5]=list.get(i).getGoodsNum();
            obj[6]=list.get(i).getPower();
            dataList.add(obj);
        }
        WriteExcel ex = new WriteExcel(title, dataList);
        InputStream in;
        in = ex.export();
        return in;
    }

    public static HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {}
        return session;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

}