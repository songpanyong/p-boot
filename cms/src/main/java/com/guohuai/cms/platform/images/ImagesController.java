package com.guohuai.cms.platform.images;

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

import com.guohuai.cms.component.util.DateUtil;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;

import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/images", produces = "application/json")
public class ImagesController {
	
	@Autowired
	private ImagesService imagesService;
	
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<ImagesQueryRep>> imagesQuery(HttpServletRequest request,
			@And({  @Spec(params = "imgName", path = "imgName", spec = Like.class),
				@Spec(params = "reqTimeBegin", path = "createTime", spec = DateAfterInclusive.class, config = DateUtil.fullDatePattern),
				@Spec(params = "reqTimeEnd", path = "createTime", spec = DateBeforeInclusive.class, config = DateUtil.fullDatePattern)}) 
         		Specification<ImagesEntity> spec,
         	@RequestParam int page, 
			@RequestParam int rows) {		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));		
		PagesRep<ImagesQueryRep> rep = this.imagesService.imagesQuery(spec, pageable);
		
		return new ResponseEntity<PagesRep<ImagesQueryRep>>(rep, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> add(@Valid ImagesAddReq req) {		
		BaseRep rep = this.imagesService.addImages(req);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> delete(@RequestParam String oid) {
		BaseRep rep =  this.imagesService.delImages(oid);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
}
