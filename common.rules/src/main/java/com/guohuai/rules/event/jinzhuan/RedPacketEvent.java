package com.guohuai.rules.event.jinzhuan;

import org.springframework.stereotype.Component;

import com.guohuai.rules.event.BaseEvent;
import com.guohuai.rules.event.EventAnno;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author xueyunlong
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@EventAnno("红包")
@Component
public class RedPacketEvent extends BaseEvent {
	
	@EventAnno("用户OID")
	String userOid;
	
	@EventAnno("红包金额")
	String redAmount;
	
	
	
}

