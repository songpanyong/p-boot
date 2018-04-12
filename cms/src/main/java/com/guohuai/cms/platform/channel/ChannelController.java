package com.guohuai.cms.platform.channel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/channel", produces = "application/json")
public class ChannelController extends BaseController {

	@Autowired
	private ChannelService channelService;
	
	/**
	 * 获取渠道，为<select>使用
	 * @return
	 */
	@RequestMapping(value = "/getSelect", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, List<ChannelSelectRep>>> getSelect() {
		Map<String, List<ChannelSelectRep>> rep = this.channelService.allSelectChannels();
		
		return new ResponseEntity<Map<String, List<ChannelSelectRep>>>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<ChannelEntity>> imagesQuery(HttpServletRequest request,
			@And({  @Spec(params = "code", path = "code", spec = Like.class),
				@Spec(params = "name", path = "name", spec = Like.class)})
         		Specification<ChannelEntity> spec,
         	@RequestParam int page, 
			@RequestParam int rows) {		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));		
		PagesRep<ChannelEntity> rep = this.channelService.channelQuery(spec, pageable);
		
		return new ResponseEntity<PagesRep<ChannelEntity>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 新增/修改渠道
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> add(@Valid ChannelAddReq req) {		
		BaseRep rep = this.channelService.addChannel(req);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> delete(@RequestParam String oid) {
		BaseRep rep =  this.channelService.delChannel(oid);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
}
