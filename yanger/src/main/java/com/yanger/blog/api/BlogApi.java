package com.yanger.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanger.blog.service.facade.BlogService;
import com.yanger.blog.vo.ArticleVo;
import com.yanger.blog.vo.BlogUserVo;
import com.yanger.blog.vo.EssayDataVo;
import com.yanger.blog.vo.HomeDataVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.blog.vo.StudyDataVo;
import com.yanger.common.util.RedisMagger;
import com.yanger.common.vo.ApiResponse;
import com.yanger.mybatis.util.ResultPage;

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
			RedisMagger.set("r", "123测试缓存");
			
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
	
	/**
	 * <p>Description: 学习笔记页面数据初始化 </p>  
	 * @author YangHao  
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "心情随笔页面数据初始化", notes = "")
	@GetMapping("/essayInit")
	public ApiResponse<EssayDataVo> essayInit(){
		ApiResponse<EssayDataVo> api = new ApiResponse<>();
		try {
			EssayDataVo essayDataVo = blogService.getEssayData();
			api.setData(essayDataVo);
		} catch (Exception e) {
			api.error("加载心情随笔数据失败");
			e.printStackTrace();
		}
		return api;
	}
	
	/**
	 * <p>Description: 查询文章分页数据 </p>  
	 * @author YangHao  
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "查询文章分页数据", notes = "")
	@PostMapping("/articlePage")
	public ApiResponse<ResultPage<ArticleVo>> articlePage(@RequestBody PageQueryVo pageQueryVo){
		ApiResponse<ResultPage<ArticleVo>> api = new ApiResponse<>();
		try {
			ResultPage<ArticleVo> page = blogService.getPageData(pageQueryVo);
			api.setData(page);
		} catch (Exception e) {
			api.error("加载文章分页数据失败");
			e.printStackTrace();
		}
		return api;
	}
	
	/**
	 * <p>Description: 用户注册 </p>  
	 * @author YangHao  
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "用户注册", notes = "")
	@PostMapping("/register")
	public ApiResponse<String> register(@RequestBody BlogUserVo blogUserVo){
		ApiResponse<String> api = new ApiResponse<>();
		try {
			blogService.userRegister(blogUserVo);
			api.setData("注册成功");
		} catch (Exception e) {
			api.error("用户注册失败");
			e.printStackTrace();
		}
		return api;
	}
	
	/**
	 * <p>Description: 用户登录 </p>  
	 * @author YangHao  
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "用户登录", notes = "")
	@PostMapping("/login")
	public ApiResponse<BlogUserVo> login(@RequestBody BlogUserVo blogUserVo){
		ApiResponse<BlogUserVo> api = new ApiResponse<>();
		try {
			BlogUserVo user = blogService.userLogin(blogUserVo);
			if(user != null){
				api.setData(user);
			}else {
				api.error("输入的账号不存在或密码错误");
			}
		} catch (Exception e) {
			api.error("用户登录失败");
			e.printStackTrace();
		}
		return api;
	}
	
	
	/**
	 * <p>Description: 校验用户名是否被使用 </p>  
	 * @author YangHao  
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "校验用户名是否被使用 ", notes = "")
	@GetMapping("/checkCode/{code}")
	public ApiResponse<String> checkCode(@PathVariable(value="code") String code){
		ApiResponse<String> api = new ApiResponse<>();
		try {
			Boolean exist = blogService.checkUserCode(code);
			if(exist){
				api.error("已经被使用");
			}
		} catch (Exception e) {
			api.error("校验用户名失败");
			e.printStackTrace();
		}
		return api;
	}
}
