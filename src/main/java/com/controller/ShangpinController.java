
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 商品
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/shangpin")
public class ShangpinController {
    private static final Logger logger = LoggerFactory.getLogger(ShangpinController.class);

    private static final String TABLE_NAME = "shangpin";

    @Autowired
    private ShangpinService shangpinService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private ShangpinOrderService shangpinOrderService;
    //级联表非注册的service
    //注册表service
    @Autowired
    private YonghuService yonghuService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        params.put("shangpinDeleteStart",1);params.put("shangpinDeleteEnd",1);
        CommonUtil.checkMap(params);
        PageUtils page = shangpinService.queryPage(params);

        //字典表数据转换
        List<ShangpinView> list =(List<ShangpinView>)page.getList();
        for(ShangpinView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ShangpinEntity shangpin = shangpinService.selectById(id);
        if(shangpin !=null){
            //entity转view
            ShangpinView view = new ShangpinView();
            BeanUtils.copyProperties( shangpin , view );//把实体数据重构到view中
            //级联表 用户
            //级联表
            YonghuEntity yonghu = yonghuService.selectById(shangpin.getYonghuId());
            if(yonghu != null){
            BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setYonghuId(yonghu.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody ShangpinEntity shangpin, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,shangpin:{}",this.getClass().getName(),shangpin.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("用户".equals(role))
            shangpin.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<ShangpinEntity> queryWrapper = new EntityWrapper<ShangpinEntity>()
            .eq("yonghu_id", shangpin.getYonghuId())
            .eq("shangpin_name", shangpin.getShangpinName())
            .eq("shangpin_types", shangpin.getShangpinTypes())
            .eq("shangpin_kucun_number", shangpin.getShangpinKucunNumber())
            .eq("shangpin_clicknum", shangpin.getShangpinClicknum())
            .eq("shangxia_types", shangpin.getShangxiaTypes())
            .eq("shangpin_delete", shangpin.getShangpinDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ShangpinEntity shangpinEntity = shangpinService.selectOne(queryWrapper);
        if(shangpinEntity==null){
            shangpin.setShangpinClicknum(1);
            shangpin.setShangpinYesnoTypes(1);
            shangpin.setShangxiaTypes(1);
            shangpin.setShangpinDelete(1);
            shangpin.setCreateTime(new Date());
            shangpinService.insert(shangpin);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ShangpinEntity shangpin, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,shangpin:{}",this.getClass().getName(),shangpin.toString());
        ShangpinEntity oldShangpinEntity = shangpinService.selectById(shangpin.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("用户".equals(role))
//            shangpin.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<ShangpinEntity> queryWrapper = new EntityWrapper<ShangpinEntity>()
            .notIn("id",shangpin.getId())
            .andNew()
            .eq("yonghu_id", shangpin.getYonghuId())
            .eq("shangpin_name", shangpin.getShangpinName())
            .eq("shangpin_types", shangpin.getShangpinTypes())
            .eq("shangpin_kucun_number", shangpin.getShangpinKucunNumber())
            .eq("shangpin_clicknum", shangpin.getShangpinClicknum())
            .eq("shangxia_types", shangpin.getShangxiaTypes())
            .eq("shangpin_delete", shangpin.getShangpinDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ShangpinEntity shangpinEntity = shangpinService.selectOne(queryWrapper);
        if("".equals(shangpin.getShangpinPhoto()) || "null".equals(shangpin.getShangpinPhoto())){
                shangpin.setShangpinPhoto(null);
        }
        if(shangpinEntity==null){
            shangpinService.updateById(shangpin);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


    /**
    * 审核
    */
    @RequestMapping("/shenhe")
    public R shenhe(@RequestBody ShangpinEntity shangpinEntity, HttpServletRequest request){
        logger.debug("shenhe方法:,,Controller:{},,shangpinEntity:{}",this.getClass().getName(),shangpinEntity.toString());

        ShangpinEntity oldShangpin = shangpinService.selectById(shangpinEntity.getId());//查询原先数据

//        if(shangpinEntity.getShangpinYesnoTypes() == 2){//通过
//            shangpinEntity.setShangpinTypes();
//        }else if(shangpinEntity.getShangpinYesnoTypes() == 3){//拒绝
//            shangpinEntity.setShangpinTypes();
//        }
        shangpinService.updateById(shangpinEntity);//审核

        return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<ShangpinEntity> oldShangpinList =shangpinService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        ArrayList<ShangpinEntity> list = new ArrayList<>();
        for(Integer id:ids){
            ShangpinEntity shangpinEntity = new ShangpinEntity();
            shangpinEntity.setId(id);
            shangpinEntity.setShangpinDelete(2);
            list.add(shangpinEntity);
        }
        if(list != null && list.size() >0){
            shangpinService.updateBatchById(list);
        }

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<ShangpinEntity> shangpinList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("../../upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            ShangpinEntity shangpinEntity = new ShangpinEntity();
//                            shangpinEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            shangpinEntity.setShangpinName(data.get(0));                    //商品名称 要改的
//                            shangpinEntity.setShangpinPhoto("");//详情和图片
//                            shangpinEntity.setShangpinTypes(Integer.valueOf(data.get(0)));   //商品类型 要改的
//                            shangpinEntity.setShangpinKucunNumber(Integer.valueOf(data.get(0)));   //商品库存 要改的
//                            shangpinEntity.setShangpinOldMoney(data.get(0));                    //原价 要改的
//                            shangpinEntity.setShangpinNewMoney(data.get(0));                    //现价 要改的
//                            shangpinEntity.setShangpinClicknum(Integer.valueOf(data.get(0)));   //点击次数 要改的
//                            shangpinEntity.setShangpinYesnoTypes(Integer.valueOf(data.get(0)));   //商品审核 要改的
//                            shangpinEntity.setShangpinYesnoText(data.get(0));                    //审核结果 要改的
//                            shangpinEntity.setShangxiaTypes(Integer.valueOf(data.get(0)));   //是否上架 要改的
//                            shangpinEntity.setShangpinDelete(1);//逻辑删除字段
//                            shangpinEntity.setShangpinContent("");//详情和图片
//                            shangpinEntity.setCreateTime(date);//时间
                            shangpinList.add(shangpinEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        shangpinService.insertBatch(shangpinList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }




    /**
    * 个性推荐
    */
    @IgnoreAuth
    @RequestMapping("/gexingtuijian")
    public R gexingtuijian(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("gexingtuijian方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        CommonUtil.checkMap(params);
        List<ShangpinView> returnShangpinViewList = new ArrayList<>();

        //查询订单
        Map<String, Object> params1 = new HashMap<>(params);params1.put("sort","id");params1.put("yonghuId",request.getSession().getAttribute("userId"));
        PageUtils pageUtils = shangpinOrderService.queryPage(params1);
        List<ShangpinOrderView> orderViewsList =(List<ShangpinOrderView>)pageUtils.getList();
        Map<Integer,Integer> typeMap=new HashMap<>();//购买的类型list
        for(ShangpinOrderView orderView:orderViewsList){
            Integer shangpinTypes = orderView.getShangpinTypes();
            if(typeMap.containsKey(shangpinTypes)){
                typeMap.put(shangpinTypes,typeMap.get(shangpinTypes)+1);
            }else{
                typeMap.put(shangpinTypes,1);
            }
        }
        List<Integer> typeList = new ArrayList<>();//排序后的有序的类型 按最多到最少
        typeMap.entrySet().stream().sorted((o1, o2) -> o2.getValue() - o1.getValue()).forEach(e -> typeList.add(e.getKey()));//排序
        Integer limit = Integer.valueOf(String.valueOf(params.get("limit")));
        for(Integer type:typeList){
            Map<String, Object> params2 = new HashMap<>(params);params2.put("shangpinTypes",type);
            PageUtils pageUtils1 = shangpinService.queryPage(params2);
            List<ShangpinView> shangpinViewList =(List<ShangpinView>)pageUtils1.getList();
            returnShangpinViewList.addAll(shangpinViewList);
            if(returnShangpinViewList.size()>= limit) break;//返回的推荐数量大于要的数量 跳出循环
        }
        //正常查询出来商品,用于补全推荐缺少的数据
        PageUtils page = shangpinService.queryPage(params);
        if(returnShangpinViewList.size()<limit){//返回数量还是小于要求数量
            int toAddNum = limit - returnShangpinViewList.size();//要添加的数量
            List<ShangpinView> shangpinViewList =(List<ShangpinView>)page.getList();
            for(ShangpinView shangpinView:shangpinViewList){
                Boolean addFlag = true;
                for(ShangpinView returnShangpinView:returnShangpinViewList){
                    if(returnShangpinView.getId().intValue() ==shangpinView.getId().intValue()) addFlag=false;//返回的数据中已存在此商品
                }
                if(addFlag){
                    toAddNum=toAddNum-1;
                    returnShangpinViewList.add(shangpinView);
                    if(toAddNum==0) break;//够数量了
                }
            }
        }else {
            returnShangpinViewList = returnShangpinViewList.subList(0, limit);
        }

        for(ShangpinView c:returnShangpinViewList)
            dictionaryService.dictionaryConvert(c, request);
        page.setList(returnShangpinViewList);
        return R.ok().put("data", page);
    }

    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = shangpinService.queryPage(params);

        //字典表数据转换
        List<ShangpinView> list =(List<ShangpinView>)page.getList();
        for(ShangpinView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ShangpinEntity shangpin = shangpinService.selectById(id);
            if(shangpin !=null){

                //点击数量加1
                shangpin.setShangpinClicknum(shangpin.getShangpinClicknum()+1);
                shangpinService.updateById(shangpin);

                //entity转view
                ShangpinView view = new ShangpinView();
                BeanUtils.copyProperties( shangpin , view );//把实体数据重构到view中

                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(shangpin.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody ShangpinEntity shangpin, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,shangpin:{}",this.getClass().getName(),shangpin.toString());
        Wrapper<ShangpinEntity> queryWrapper = new EntityWrapper<ShangpinEntity>()
            .eq("yonghu_id", shangpin.getYonghuId())
            .eq("shangpin_name", shangpin.getShangpinName())
            .eq("shangpin_types", shangpin.getShangpinTypes())
            .eq("shangpin_kucun_number", shangpin.getShangpinKucunNumber())
            .eq("shangpin_clicknum", shangpin.getShangpinClicknum())
            .eq("shangpin_yesno_types", shangpin.getShangpinYesnoTypes())
            .eq("shangpin_yesno_text", shangpin.getShangpinYesnoText())
            .eq("shangxia_types", shangpin.getShangxiaTypes())
            .eq("shangpin_delete", shangpin.getShangpinDelete())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ShangpinEntity shangpinEntity = shangpinService.selectOne(queryWrapper);
        if(shangpinEntity==null){
            shangpin.setShangpinYesnoTypes(1);
            shangpin.setShangpinDelete(1);
            shangpin.setCreateTime(new Date());
        shangpinService.insert(shangpin);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}
