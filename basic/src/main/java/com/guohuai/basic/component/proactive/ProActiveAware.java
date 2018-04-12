package com.guohuai.basic.component.proactive;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ProActiveAware {

	@Autowired
	private ApplicationContext context;

	public <T extends ProActive> boolean achieved(Class<T> clazz) {
		final Map<String, T> map = this.context.getBeansOfType(clazz);
		if (map.size() <= 0) {
			return false;
		}
		if (clazz.isAnnotationPresent(SingleProActive.class)) {
			if (map.size() > 1) {
				Class<?>[] impls = new Class<?>[map.size()];
				int i = 0;
				for (Map.Entry<String, T> entry : map.entrySet()) {
					impls[i] = entry.getValue().getClass();
					i++;
				}
				throw new MultiImplProActiveException(clazz, impls);
			}
		}
		return true;

	}

	public <T extends ProActive> Map<String, T> extract(Class<T> clazz) {

		final Map<String, T> map = this.context.getBeansOfType(clazz);

		if (clazz.isAnnotationPresent(SingleProActive.class)) {
			if (map.size() > 1) {
				Class<?>[] impls = new Class<?>[map.size()];
				int i = 0;
				for (Map.Entry<String, T> entry : map.entrySet()) {
					impls[i] = entry.getValue().getClass();
					i++;
				}
				throw new MultiImplProActiveException(clazz, impls);
			} else {
				return map;
			}
		}

		TreeMap<String, T> tmap = new TreeMap<String, T>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				T o1t = map.get(o1);
				T o2t = map.get(o2);
				int x = o1t.priority().compareTo(o2t.priority());
				return x == 0 ? o1.compareTo(o2) : x;
			}
		});
		if (null != map && map.size() > 0) {
			for (Map.Entry<String, T> entry : map.entrySet()) {
				tmap.put(entry.getKey(), entry.getValue());
			}
		}
		return tmap;
	}

	public <T extends ProActive, R extends Object> List<ProActive.Result<R>> invoke(ProActive.Execution<T, R> execution, Class<T> clazz) {

		Map<String, T> proactives = this.extract(clazz);

		List<ProActive.Result<R>> result = new ArrayList<ProActive.Result<R>>();

		if (null != proactives && proactives.size() > 0) {
			for (Entry<String, T> entry : proactives.entrySet()) {
				ProActive.Result<R> er = new ProActive.Result<R>();
				er.setName(entry.getKey());
				er.setClazz(entry.getValue().getClass());
				R r = execution.execute(entry.getValue());
				er.setResult(r);
				result.add(er);
			}
		}

		return result;
	}

}
