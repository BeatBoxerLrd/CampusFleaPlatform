package com.derry.controller;

import com.alibaba.fastjson.JSON;
import com.derry.pojo.*;
import com.derry.service.*;
import com.derry.util.DateUtil;
import com.derry.util.MySession;
import com.derry.util.UserStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping(value = "/goods")
public class GoodsController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private GoodImageService goodImageService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CatelogService catelogService;
    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping(value = "/publishGoods")
    //public void puslishGoods(HttpServletRequest request,HttpServletResponse response, @RequestParam(value="userId") String userId,@RequestParam(value = "fileUploads") MultipartFile[] uploadFiles,@RequestParam(value = "goodsName") String goodsName,@RequestParam(value = "price") String price,@RequestParam(value = "realPrice") String realPrice,@RequestParam(value = "catelogId") String catelogId,@RequestParam(value = "describle") String describle){
    public String publish(@RequestParam(value = "files", required = true) MultipartFile[] multipartFiles,HttpServletRequest request, HttpServletResponse response){
        Map<String ,Object> resList = new HashMap<>();
        //String callback = request.getParameter("callback");
        StringBuilder urls = new StringBuilder();
        MultipartFile[] uploadFiles = multipartFiles;
        for(MultipartFile uploadFile:uploadFiles){
            Map result = pictureService.uploadPicture(uploadFile);
            if(result!=null && result.get("error").equals(0)){
                String imgUrl = (String) result.get("url");
                urls.append(imgUrl);
                urls.append(";");
                System.out.println(imgUrl);
                resList.put("code",200);
                resList.put("message","发布成功");
            }else if(result.get("error").equals(1)){
                resList.put("code",500);
                resList.put("message",result.get("message"));
                break;
            }
        }
        //String userId = request.getParameter("userId");
        String sessionId = request.getParameter("sessionId");
        //String phone = (String ) MySession.getInstance().getSession(sessionId).getAttribute("userTelphone");
        //User user = userService.getUserByPhone(phone);
        User user = (User) MySession.getInstance().getSession(sessionId).getAttribute("loginuser");
        Integer userId = user.getId();
        String goodsName = request.getParameter("goodsName");
        String price = request.getParameter("price");
        String realPrice = request.getParameter("realPrice");
        String catelogId = request.getParameter("catelogId");
        String describle = request.getParameter("describle");
        System.out.println(userId+"--"+goodsName+"--"+price+"--"+realPrice+"--"+catelogId+"--"+describle);
        Goods goods = new Goods();
        goods.setUserId(userId);
        goods.setCatelogId(Integer.valueOf(catelogId));
        goods.setName(goodsName);
        goods.setPrice(Float.valueOf(price));
        goods.setRealPrice(Float.valueOf(realPrice));
        goods.setDescrible(describle);
        int id = goodsService.addGood(goods,30);
        System.out.println(goods.getId());
        System.out.println(id);
        Image image = new Image();
        image.setGoodsId(goods.getId());
        image.setImgUrl(urls.toString());
        imageService.insert(image);
        String json = JSON.toJSONString(resList);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/itemDetail")
    public void itemDetail(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "itemId") String itemId,@RequestParam(value = "sessionId")String sessionId){
        Map<String ,Object> resList = new HashMap<>();
        String callback = request.getParameter("callback");
        //String sessionId = request.getParameter("sessionId");
        //String phone = (String ) MySession.getInstance().getSession(sessionId).getAttribute("userTelphone");
        resList=  goodImageService.getGoodsByItemId(Integer.valueOf(itemId));
        String json = JSON.toJSONString(resList);
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            if ((callback!="")&&(callback!=null)){
                json=callback+"("+json+")";
            }
            System.out.println(json);
            pw.println(json);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }











    @ResponseBody
    @RequestMapping(value = "/test")
    public String test(String callback){
        return callback +"('test jsonp');";
    }

    @ResponseBody
    @RequestMapping(value = "/catelog")
    public void goodsList(HttpServletRequest request, HttpServletResponse response,HttpSession session, @RequestParam(value="catalog") String catalog,@RequestParam(value="pageSize")String pageSize,@RequestParam(value="pageNum")String pageNum){
        //System.out.println(UserStatus.getStatus().getPhone());
        //System.out.println("session为："+session.getAttribute("user"));
        System.out.println(Integer.valueOf(catalog)+"____"+Integer.valueOf(pageNum)+"____"+Integer.valueOf(pageSize));
        Map<String,Object> resList = null;
        String callback = request.getParameter("callback");
        if(catalog.equals("0")){
            resList = goodImageService.getAllGoodsByDate(Integer.valueOf(catalog),Integer.valueOf(pageNum),Integer.valueOf(pageSize));
        }else{
            resList = goodImageService.getAllGoodsCatelogByDate(Integer.valueOf(catalog),Integer.valueOf(pageNum),Integer.valueOf(pageSize));
            //System.out.println(goodsService.getGoodsByUserId(1));
        }
        String json = JSON.toJSONString(resList);
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            if ((callback!="")&&(callback!=null)){
                json=callback+"("+json+")";
            }
            System.out.println(json);
            pw.println(json);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 首页显示商品，每一类商品查询6件，根据最新上架排序 key的命名为catelogGoods1、catelogGoods2....
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/homeGoods")
    public ModelAndView homeGoods() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        //商品种类数量
        int catelogSize = 7;
        //每个种类显示商品数量
        int goodsSize = 6;
        for (int i = 1; i <= catelogSize; i++) {
            List<Goods> goodsList = null;
            List<GoodsExtend> goodsAndImage = null;
            goodsList = goodsService.getGoodsByCatelogOrderByDate(i, goodsSize);
            goodsAndImage = new ArrayList<GoodsExtend>();
            for (int j = 0; j < goodsList.size() ; j++) {
                //将用户信息和image信息封装到GoodsExtend类中，传给前台
                GoodsExtend goodsExtend = new GoodsExtend();
                Goods goods = goodsList.get(j);
                List<Image> images = imageService.getImagesByGoodsPrimaryKey(goods.getId());
                goodsExtend.setGoods(goods);
                goodsExtend.setImages(images);
                goodsAndImage.add(j, goodsExtend);
            }
            String key = "catelog" + "Goods" + i;
            modelAndView.addObject(key, goodsAndImage);
        }
        modelAndView.setViewName("goods/homeGoods");
        return modelAndView;
    }

    @RequestMapping(value = "/search")
    public ModelAndView searchGoods(@RequestParam(value = "str",required = false) String str)throws Exception {
        List<Goods> goodsList = goodsService.searchGoods(str,str);
        List<GoodsExtend> goodsExtendList = new ArrayList<GoodsExtend>();
        for(int i = 0;i<goodsList.size();i++) {
            GoodsExtend goodsExtend = new GoodsExtend();
            Goods goods = goodsList.get(i);
            List<Image> imageList = imageService.getImagesByGoodsPrimaryKey(goods.getId());
            goodsExtend.setGoods(goods);
            goodsExtend.setImages(imageList);
            goodsExtendList.add(i,goodsExtend);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("goodsExtendList", goodsExtendList);
        modelAndView.addObject("search",str);
        modelAndView.setViewName("/goods/searchGoods");
        return modelAndView;
    }

    /**
     * 查询该类商品
     * @param id
     * 要求该参数不为空
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/catelog/{id}")
    public ModelAndView catelogGoods(HttpServletRequest request,@PathVariable("id") Integer id,
                                     @RequestParam(value = "str",required = false) String str) throws Exception {
        List<Goods> goodsList = goodsService.getGoodsByCatelog(id,str,str);
        Catelog catelog = catelogService.selectByPrimaryKey(id);
        List<GoodsExtend> goodsExtendList = new ArrayList<GoodsExtend>();
        for(int i = 0;i<goodsList.size();i++) {
            GoodsExtend goodsExtend = new GoodsExtend();
            Goods goods = goodsList.get(i);
            List<Image> imageList = imageService.getImagesByGoodsPrimaryKey(goods.getId());
            goodsExtend.setGoods(goods);
            goodsExtend.setImages(imageList);
            goodsExtendList.add(i,goodsExtend);
            for (GoodsExtend extend : goodsExtendList) {
                System.out.println(extend.toString());
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("goodsExtendList", goodsExtendList);
        modelAndView.addObject("catelog", catelog);
        modelAndView.addObject("search",str);
        modelAndView.setViewName("/goods/catelogGoods");
        return modelAndView;
    }

    /**
     * 根据商品id查询该商品详细信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goodsId/{id}")
    public ModelAndView getGoodsById(@PathVariable("id") Integer id,@RequestParam(value = "str",required = false) String str) throws Exception {
        Goods goods = goodsService.getGoodsByPrimaryKey(id);
        User seller = userService.selectByPrimaryKey(goods.getUserId());
        Catelog catelog = catelogService.selectByPrimaryKey(goods.getCatelogId());
        GoodsExtend goodsExtend = new GoodsExtend();
        List<Image> imageList = imageService.getImagesByGoodsPrimaryKey(id);
        goodsExtend.setGoods(goods);
        goodsExtend.setImages(imageList);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("goodsExtend", goodsExtend);
        modelAndView.addObject("seller", seller);
        modelAndView.addObject("search",str);
        modelAndView.addObject("catelog", catelog);
        modelAndView.setViewName("/goods/detailGoods");
        return modelAndView;
    }

    /**
     * 修改商品信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editGoods/{id}")
    public ModelAndView editGoods(@PathVariable("id") Integer id) throws Exception {

        Goods goods = goodsService.getGoodsByPrimaryKey(id);
        List<Image> imageList = imageService.getImagesByGoodsPrimaryKey(id);
        GoodsExtend goodsExtend = new GoodsExtend();
        goodsExtend.setGoods(goods);
        goodsExtend.setImages(imageList);
        ModelAndView modelAndView = new ModelAndView();
        // 将商品信息添加到model
        modelAndView.addObject("goodsExtend", goodsExtend);
        modelAndView.setViewName("/goods/editGoods");
        return modelAndView;
    }

    /**
     * 提交商品更改信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editGoodsSubmit")
    public String editGoodsSubmit(HttpServletRequest request,Goods goods) throws Exception {
        User cur_user = (User)request.getSession().getAttribute("cur_user");
        goods.setUserId(cur_user.getId());
        String polish_time = DateUtil.getNowDay();
        goods.setPolishTime(polish_time);
        goodsService.updateGoodsByPrimaryKeyWithBLOBs(goods.getId(), goods);
        return "redirect:/user/allGoods";
    }

    /**
     * 商品下架
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/offGoods")
    public ModelAndView offGoods() throws Exception {

        return null;
    }

    /**
     * 用户删除商品
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteGoods/{id}")
    public String deleteGoods(HttpServletRequest request,@PathVariable("id") Integer id) throws Exception {
        Goods goods = goodsService.getGoodsByPrimaryKey(id);
        //删除商品后，catlog的number-1，user表的goods_num-1，image删除,更新session的值
        User cur_user = (User)request.getSession().getAttribute("cur_user");
        goods.setUserId(cur_user.getId());
        int number = cur_user.getGoodsNum();
        Integer calelog_id = goods.getCatelogId();
        Catelog catelog = catelogService.selectByPrimaryKey(calelog_id);
        catelogService.updateCatelogNum(calelog_id,catelog.getNumber()-1);
        userService.updateGoodsNum(cur_user.getId(),number-1);
        cur_user.setGoodsNum(number-1);
        request.getSession().setAttribute("cur_user",cur_user);//修改session值
        imageService.deleteImagesByGoodsPrimaryKey(id);
        goodsService.deleteGoodsByPrimaryKey(id);
        return "redirect:/user/allGoods";
    }
    /**
     * 发布商品
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/publish")
    public String publishGoods(HttpServletRequest request) {
        //可以校验用户是否登录
        User cur_user = (User)request.getSession().getAttribute("cur_user");
        if(cur_user == null) {
            return "/goods/homeGoods";
        } else {
            return "/goods/pubGoods";
        }
    }
    /**
     * 提交发布的商品信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/publishGoodsSubmit")
    public String publishGoodsSubmit(HttpServletRequest request,Image ima,Goods goods,MultipartFile image)
            throws Exception {
        //查询出当前用户cur_user对象，便于使用id
        User cur_user = (User)request.getSession().getAttribute("cur_user");

        goods.setUserId(cur_user.getId());
        int i = goodsService.addGood(goods,10);//在goods表中插入物品
        //返回插入的该物品的id
        int goodsId = goods.getId();
        ima.setGoodsId(goodsId);
        imageService.insert(ima);//在image表中插入商品图片
        //发布商品后，catlog的number+1，user表的goods_num+1，更新session的值
        int number = cur_user.getGoodsNum();
        Integer calelog_id = goods.getCatelogId();
        Catelog catelog = catelogService.selectByPrimaryKey(calelog_id);
        catelogService.updateCatelogNum(calelog_id,catelog.getNumber()+1);
        userService.updateGoodsNum(cur_user.getId(),number+1);
        cur_user.setGoodsNum(number+1);
        request.getSession().setAttribute("cur_user",cur_user);//修改session值
        return "redirect:/user/allGoods";
    }

    /**
     * 上传物品
     * @param session
     * @param myfile
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/uploadFile")
    public  Map<String,Object> uploadFile(HttpSession session,MultipartFile myfile) throws IllegalStateException, IOException{
        //原始名称
        String oldFileName = myfile.getOriginalFilename(); //获取上传文件的原名
        //存储图片的物理路径
        String file_path = session.getServletContext().getRealPath("upload");
        //上传图片
        if(myfile!=null && oldFileName!=null && oldFileName.length()>0){
            //新的图片名称
            String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));
            //新图片
            File newFile = new File(file_path+"/"+newFileName);
            //将内存中的数据写入磁盘
            myfile.transferTo(newFile);
            //将新图片名称返回到前端
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("success", "成功啦");
            map.put("imgUrl",newFileName);
            return  map;
        }else{
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("error","图片不合法");
            return map;
        }
    }
}