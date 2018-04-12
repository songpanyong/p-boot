//package com.guohuai.tulip.schedule;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponService;
//import com.guohuai.tulip.platform.event.EventService;
//import com.guohuai.tulip.platform.facade.FacadeService;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@Slf4j
//@Transactional
//public class ScheduleManager {
//
//	@Autowired
//	UserCouponService userCouponService;
//	@Autowired
//	EventService eventService;
//	@Autowired
//	FacadeService facadeService;
//
//	/**
//	 * 体验金自动到期
//	 */
//	@Scheduled(cron = "0 5 0 * * ?")
//	public void autoModifyCouponStatus() {
//		log.info("<<-----开始凌晨0点5分自动更新卡券状态----->>");
//		System.out.println("<<-----开始凌晨0点5分自动更新卡券状态----->>");
//		try {
//			this.userCouponService.updateCouponForExpired();
//		} catch (Throwable e) {
//			log.error("<<-----失败凌晨0点5分自动更新卡券状态----->>");
//			e.printStackTrace();
//		}
//		log.info("<<-----成功凌晨0点5分自动更新卡券状态----->>");
//	}
//
//	@Scheduled(cron = "0 15 2 ? * *")
//	public void autoIssuedCoupon() {
//		log.info("<<-----开始凌晨2点15分自动更新卡券状态----->>");
//		System.out.println("<<-----开始凌晨2点15分自动更新卡券状态----->>");
//		try {
//			this.facadeService.issuedCouponBySchedule();
//		} catch (Throwable e) {
//			log.error("<<-----失败凌晨2点15分自动更新卡券状态----->>");
//			e.printStackTrace();
//		}
//		log.info("<<-----成功凌晨2点15分自动更新卡券状态----->>");
//	}
//	@Scheduled(cron = "0 0 9 ? * *")
//	public void autoIssuedBirthDayCoupon() {
//		log.info("<<-----开始早晨9点处理生日活动----->>");
//		System.out.println("<<-----开始早晨9点开始处理生日活动----->>");
//		try {
//			this.facadeService.issuedCouponByBirthDay();
//		} catch (Throwable e) {
//			log.error("<<-----失败早晨9点处理生日活动----->>");
//			e.printStackTrace();
//		}
//		log.info("<<-----成功早晨9点处理生日活动----->>");
//	}
//
//	@Scheduled(cron = "0 15 0 ? * *")
//	public void autoOnEvent() {
//		log.info("<<-----开始凌晨0点15分自动活动上线----->>");
//		try {
//			this.eventService.autoOnEvent();
//		} catch (Throwable e) {
//			log.error("<<-----失败凌晨0点15分自动活动上线----->>");
//			e.printStackTrace();
//		}
//		log.info("<<-----成功凌晨0点15分自动活动上线----->>");
//	}
////	@Scheduled(cron = "0 48 0 ? * *")
////	public void test11() {
////		log.info("<<-----开始凌晨0点15分自动活动上线----->>");
////		try {
////			this.facadeService.generateCoupon();
////		} catch (Throwable e) {
////			log.error("<<-----失败凌晨0点15分自动活动上线----->>");
////			e.printStackTrace();
////		}
////		log.info("<<-----成功凌晨0点15分自动活动上线----->>");
////	}
//
//}