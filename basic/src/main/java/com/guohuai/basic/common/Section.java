package com.guohuai.basic.common;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public final class Section<T extends Serializable> {

	private boolean hasMin;
	private boolean minEqual;
	private Number min;
	private Class<? extends Number> minType;
	private boolean hasMax;
	private boolean maxEqual;
	private Number max;
	private Class<? extends Number> maxType;

	public Section(String spec) {
		// :12-24:
		if (spec.equals("-")) {
			throw new RuntimeException("Unsupported param: '" + spec + "'");
		}
		boolean check = spec.matches("^(:?\\d+(\\.\\d+)?)?\\-(\\d+(\\.\\d+)?:?)?$");
		if (!check) {
			throw new RuntimeException("Unsupported param: :'" + spec + "'");
		}
		String[] x = spec.split("-");
		this.buildMinPart(spec, x[0]);
		if (x.length == 2) {
			this.buildMaxPart(spec, x[1]);
		}
	}

	private void buildMinPart(String spec, String part) {
		if (part.equals("")) {
			this.hasMin = false;
		} else {
			this.hasMin = true;
			String mine = null;
			if (part.charAt(0) == ':') {
				this.minEqual = true;
				mine = part.substring(1);
			} else {
				this.minEqual = false;
				mine = part;
			}
			if (mine.matches("^\\d+$")) {
				this.minType = Integer.class;
				this.min = Integer.parseInt(mine);
			} else if (mine.matches("^\\d+\\.\\d+$")) {
				this.minType = Double.class;
				this.min = Double.parseDouble(mine);
			} else {
				throw new RuntimeException("Unsupported param: :'" + spec + "'");
			}
		}

	}

	private void buildMaxPart(String spec, String part) {
		if (part.equals("")) {
			this.hasMax = false;
		} else {
			this.hasMax = true;
			String maxe = null;
			if (part.charAt(part.length() - 1) == ':') {
				this.maxEqual = true;
				maxe = part.substring(0, part.length() - 1);
			} else {
				this.maxEqual = false;
				maxe = part;
			}
			if (maxe.matches("^\\d+$")) {
				this.maxType = Integer.class;
				this.max = Integer.parseInt(maxe);
			} else if (maxe.matches("^\\d+\\.\\d+$")) {
				this.maxType = Double.class;
				this.max = Double.parseDouble(maxe);
			} else {
				throw new RuntimeException("Unsupported param: :'" + spec + "'");
			}
		}
	}

	public boolean hasMin() {
		return this.hasMin;
	}

	public boolean minEqual() {
		return this.minEqual;
	}

	public Number min() {
		return this.min;
	}

	public boolean hasMax() {
		return this.hasMax;
	}

	public boolean maxEqual() {
		return this.maxEqual;
	}

	public Number max() {
		return this.max;
	}

	public Predicate build(Root<T> root, CriteriaBuilder cb, String attributeName) {
		Predicate minp = null, maxp = null;

		if (this.hasMin) {
			if (this.minEqual) {
				minp = cb.ge(root.get(attributeName).as(Number.class), this.min);
			} else {
				minp = cb.gt(root.get(attributeName).as(Number.class), this.min);
			}
		}

		if (this.hasMax) {
			if (this.maxEqual) {
				maxp = cb.le(root.get(attributeName).as(Number.class), this.max);
			} else {
				maxp = cb.lt(root.get(attributeName).as(Number.class), this.max);
			}
		}

		Predicate p = (minp != null && maxp != null) ? (cb.and(minp, maxp)) : (minp != null ? minp : maxp);

		return p;

	}

	@Override
	public String toString() {
		return String.format("{\"hasMin\":%s, \"minEqual\":%s, \"min\":%s, \"minType\":%s, \"hasMax\":%s, \"maxEqual\":%s, \"max\":%s, \"maxType\":%s}", this.hasMin, this.minEqual, this.min,
				this.minType, this.hasMax, this.maxEqual, this.max, this.maxType);
	}

	public final static <T extends Serializable> Predicate or(Root<T> root, CriteriaBuilder cb, String attributeName, String... specs) {
		if (null == specs || specs.length == 0)
			return null;
		Predicate[] predicates = build(root, cb, attributeName, specs);
		if (predicates.length == 1) {
			return predicates[0];
		}
		Predicate predicate = cb.or(predicates);
		return predicate;
	}

	public final static <T extends Serializable> Predicate and(Root<T> root, CriteriaBuilder cb, String attributeName, String... specs) {
		if (null == specs || specs.length == 0)
			return null;
		Predicate[] predicates = build(root, cb, attributeName, specs);
		if (predicates.length == 1) {
			return predicates[0];
		}
		Predicate predicate = cb.and(predicates);
		return predicate;
	}

	private final static <T extends Serializable> Predicate[] build(Root<T> root, CriteriaBuilder cb, String attributeName, String... specs) {
		if (null == specs || specs.length == 0)
			return null;
		Predicate[] predicates = new Predicate[specs.length];
		for (int i = 0; i < specs.length; i++) {
			Predicate predicate = new Section<T>(specs[i]).build(root, cb, attributeName);
			predicates[i] = predicate;
		}
		return predicates;
	}

}