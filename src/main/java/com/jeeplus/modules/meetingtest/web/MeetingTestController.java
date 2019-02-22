/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meetingtest.web;

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
import com.jeeplus.modules.meetingtest.entity.MeetingTest;
import com.jeeplus.modules.meetingtest.service.MeetingTestService;

/**
 * 会议规划练习Controller
 * @author LiuHao
 * @version 2019-02-18
 */
@Controller
@RequestMapping(value = "${adminPath}/meetingtest/meetingTest")
public class MeetingTestController extends BaseController {

	@Autowired
	private MeetingTestService meetingTestService;
	
	@ModelAttribute
	public MeetingTest get(@RequestParam(required=false) String id) {
		MeetingTest entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = meetingTestService.get(id);
		}
		if (entity == null){
			entity = new MeetingTest();
		}
		return entity;
	}
	
	/**
	 * 会议规划练习列表页面
	 */
	@RequiresPermissions("meetingtest:meetingTest:list")
	@RequestMapping(value = {"list", ""})
	public String list(MeetingTest meetingTest, Model model) {
		model.addAttribute("meetingTest", meetingTest);
		return "modules/meetingtest/meetingTestList";
	}
	
		/**
	 * 会议规划练习列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meetingtest:meetingTest:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MeetingTest meetingTest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MeetingTest> page = meetingTestService.findPage(new Page<MeetingTest>(request, response), meetingTest); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑会议规划练习表单页面
	 */
	@RequiresPermissions(value={"meetingtest:meetingTest:view","meetingtest:meetingTest:add","meetingtest:meetingTest:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, MeetingTest meetingTest, Model model) {
		model.addAttribute("meetingTest", meetingTest);
		model.addAttribute("mode", mode);
		return "modules/meetingtest/meetingTestForm";
	}

	/**
	 * 保存会议规划练习
	 */
	@ResponseBody
	@RequiresPermissions(value={"meetingtest:meetingTest:add","meetingtest:meetingTest:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(MeetingTest meetingTest, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(meetingTest);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		meetingTestService.save(meetingTest);//保存
		j.setSuccess(true);
		j.setMsg("保存会议规划练习成功");
		return j;
	}
	
	/**
	 * 删除会议规划练习
	 */
	@ResponseBody
	@RequiresPermissions("meetingtest:meetingTest:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MeetingTest meetingTest) {
		AjaxJson j = new AjaxJson();
		meetingTestService.delete(meetingTest);
		j.setMsg("删除会议规划练习成功");
		return j;
	}
	
	/**
	 * 批量删除会议规划练习
	 */
	@ResponseBody
	@RequiresPermissions("meetingtest:meetingTest:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			meetingTestService.delete(meetingTestService.get(id));
		}
		j.setMsg("删除会议规划练习成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meetingtest:meetingTest:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(MeetingTest meetingTest, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会议规划练习"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MeetingTest> page = meetingTestService.findPage(new Page<MeetingTest>(request, response, -1), meetingTest);
    		new ExportExcel("会议规划练习", MeetingTest.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会议规划练习记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("meetingtest:meetingTest:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MeetingTest> list = ei.getDataList(MeetingTest.class);
			for (MeetingTest meetingTest : list){
				try{
					meetingTestService.save(meetingTest);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会议规划练习记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条会议规划练习记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入会议规划练习失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入会议规划练习数据模板
	 */
	@ResponseBody
	@RequiresPermissions("meetingtest:meetingTest:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会议规划练习数据导入模板.xlsx";
    		List<MeetingTest> list = Lists.newArrayList(); 
    		new ExportExcel("会议规划练习数据", MeetingTest.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}