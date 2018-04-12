package com.guohuai.tulip.platform.banner;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.util.StringUtil;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/tulip/boot/banner", produces = "application/json")
public class BannerController extends BaseController {

	@Autowired
	private BannerService bannerService;

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<BannerQueryRep>> query(HttpServletRequest request,
														  @And({@Spec(params = "title", path = "title", spec = Like.class),
																  @Spec(params = "approveStatus", path = "approveStatus", spec = In.class),
																  @Spec(params = "releaseStatus", path = "releaseStatus", spec = In.class)})
																  Specification<BannerEntity> spec,
														  @RequestParam int page,
														  @RequestParam int rows) {

		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));
		PageResp<BannerQueryRep> rep = this.bannerService.bannerQuery(spec, pageable);

		return new ResponseEntity<PageResp<BannerQueryRep>>(rep, HttpStatus.OK);
	}

	/**
	 * 获取排序好的Banner
	 *
	 * @param request
	 * @param spec
	 * @return
	 */
	@RequestMapping(value = "/sortQuery", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<BannerQueryRep>> sortQuery(HttpServletRequest request,
															  @And({@Spec(params = "approveStatus", path = "approveStatus", spec = In.class)
															  })
																	  Specification<BannerEntity> spec) {
		PageResp<BannerQueryRep> rep = this.bannerService.sortQuery(spec);

		return new ResponseEntity<PageResp<BannerQueryRep>>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> add(@Valid BannerAddReq req) {
		String operator = super.getLoginUser();
		BaseResp rep = this.bannerService.addBanner(req, operator);

		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> update(@Valid BannerAddReq req) {
		String operator = super.getLoginUser();
		BaseResp rep = this.bannerService.addBanner(req, operator);

		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> delete(@RequestParam String oid) {
		BaseResp rep = new BaseResp();
		this.bannerService.delBanner(oid);

		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}

	/**
	 * 设置上下架
	 *
	 * @param oids
	 * @param channelOid
	 * @return
	 */
	@RequestMapping(value = "/active", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> active(@RequestParam String oids,
										   @RequestParam(required = true) String channelOid) {
		String operator = super.getLoginUser();
		BaseResp rep = null;
		if (StringUtil.isEmpty(oids)) {
			rep = this.bannerService.setActive(null, channelOid, operator);
		} else {
			rep = this.bannerService.setActive(oids.split(","), channelOid, operator);
		}

		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}


	/**
	 * 审核Banner 通过/驳回
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/dealapprove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> dealApprove(@Valid BannerApproveReq req) {
		String operator = super.getLoginUser();
		BaseResp rep = this.bannerService.dealApprove(req, operator);

		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
}
