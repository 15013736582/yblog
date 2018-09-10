package com.yanger.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanger.blog.service.facade.BlogService;
import com.yanger.blog.vo.HomeDataVo;
import com.yanger.blog.vo.StudyDataVo;
import com.yanger.common.util.RedisMagger;
import com.yanger.common.vo.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/blog")
public class BlogApi {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private RedisMagger RedisMagger;
	
	@GetMapping("t")
	public void t(){
		if(RedisMagger.hasKey("r")){
			String v = (String) RedisMagger.get("r");
			System.out.println(v);
		}else {
			RedisMagger.set("r", "123");
			
		}
	}
	
	/**
	 * <p>Description: 博客首页数据初始化 </p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午10:51:50
	 * @return
	 */
	@ApiOperation(value = "博客首页数据初始化", notes = "")
	@GetMapping("/homeInit")
	public ApiResponse<HomeDataVo> homeInit(){
		ApiResponse<HomeDataVo> api = new ApiResponse<>();
		try {
			HomeDataVo homeData = blogService.getHomeData();
			api.setData(homeData);
		} catch (Exception e) {
			api.error("加载博客首页数据失败");
			e.printStackTrace();
		}
		return api;
		
	}

	/**
	 * <p>Description: 学习笔记页面数据初始化 </p>  
	 * @author YangHao  
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "学习笔记页面数据初始化", notes = "")
	@GetMapping("/studyInit")
	public ApiResponse<StudyDataVo> studyInit(){
		ApiResponse<StudyDataVo> api = new ApiResponse<>();
		try {
			StudyDataVo studyDataVo = blogService.getStudyData();
			api.setData(studyDataVo);
		} catch (Exception e) {
			api.error("加载学习笔记数据失败");
			e.printStackTrace();
		}
		return api;
		
	}
	
}
