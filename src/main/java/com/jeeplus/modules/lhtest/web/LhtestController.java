/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.lhtest.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.lhtest.entity.Lhtest;
import com.jeeplus.modules.lhtest.service.LhtestService;

/**
 * 测试练习Controller
 * @author Liuhao
 * @version 2019-02-15
 */
@Controller
@RequestMapping(value = "${adminPath}/lhtest/lhtest")
public class LhtestController extends BaseController {

	@Autowired
	private LhtestService lhtestService;

	//当我们每次访问Contrller时都会执行该注解下的方法【@ModelAttribute作用】
	@ModelAttribute
	public Lhtest get(@RequestParam(required=false) String id) {
		Lhtest entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = lhtestService.get(id);
		}
		if (entity == null){
			entity = new Lhtest();
		}
		return entity;
	}
	
	/**
	 * 测试练习列表页面
	 */
//	@RequiresPermissions("lhtest:lhtest:list")
	@RequestMapping(value = {"list", ""})
	public String list(Lhtest lhtest, Model model) {
		model.addAttribute("lhtest", lhtest);
		return "modules/lhtest/lhtestList";
	}
	
		/**
	 * 测试练习列表数据
	 */
	@ResponseBody
//	@RequiresPermissions("lhtest:lhtest:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Lhtest lhtest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Lhtest> page = lhtestService.findPage(new Page<Lhtest>(request, response), lhtest); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑测试练习表单页面
	 */
//	@RequiresPermissions(value={"lhtest:lhtest:view","lhtest:lhtest:add","lhtest:lhtest:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Lhtest lhtest, Model model) {
		model.addAttribute("lhtest", lhtest);
		model.addAttribute("mode", mode);
		return "modules/lhtest/lhtestForm";
	}

	/**
	 * 保存测试练习
	 */
	@ResponseBody
//	@RequiresPermissions(value={"lhtest:lhtest:add","lhtest:lhtest:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Lhtest lhtest, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(lhtest);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		lhtestService.save(lhtest);//保存
		j.setSuccess(true);
		j.setMsg("保存测试练习成功");
		return j;
	}
	
	/**
	 * 删除测试练习
	 */
	@ResponseBody
//	@RequiresPermissions("lhtest:lhtest:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Lhtest lhtest) {
		AjaxJson j = new AjaxJson();
		lhtestService.delete(lhtest);
		j.setMsg("删除测试练习成功");
		return j;
	}
	
	/**
	 * 批量删除测试练习
	 */
	@ResponseBody
    //权限注解
//	@RequiresPermissions("lhtest:lhtest:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			lhtestService.delete(lhtestService.get(id));
		}
		j.setMsg("删除测试练习成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
//	@RequiresPermissions("lhtest:lhtest:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Lhtest lhtest, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "测试练习"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Lhtest> page = lhtestService.findPage(new Page<Lhtest>(request, response, -1), lhtest);
    		new ExportExcel("测试练习", Lhtest.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出测试练习记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
//	@RequiresPermissions("lhtest:lhtest:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Lhtest> list = ei.getDataList(Lhtest.class);
			for (Lhtest lhtest : list){
				try{
					lhtestService.save(lhtest);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条测试练习记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条测试练习记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入测试练习失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入测试练习数据模板
	 */
	@ResponseBody
//	@RequiresPermissions("lhtest:lhtest:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "测试练习数据导入模板.xlsx";
    		List<Lhtest> list = Lists.newArrayList(); 
    		new ExportExcel("测试练习数据", Lhtest.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}