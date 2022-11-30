package com.francisconicolau.prices.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PriceSpecification implements Specification<Prices> {

	private static final Logger log = LoggerFactory.getLogger(PriceSpecification.class);

	private static final long serialVersionUID = 1L;

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT_NO_TIME = "yyyy-MM-dd";

	private PricesSearch filter;
	private Boolean isConjuntion;

	public PriceSpecification(PricesSearch filter, boolean isConjuntion) {
		super();
		this.filter = filter;
		this.isConjuntion = isConjuntion;
	}

	public Predicate toPredicate(Root<Prices> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

		Predicate p;
		if (isConjuntion) {
			p = cb.conjunction();
		} else {
			p = cb.disjunction();
		}

		Expression<Boolean> expression = getChargeDateExpresion(cb, root);
		if (expression != null) {
			p.getExpressions().add(expression);
		}

		return p;
	}
	
	private Expression<Boolean> getChargeDateExpresion(CriteriaBuilder cb, Root<Prices> root) {
		try {
			if (filter.getDateStart() != null && filter.getDateEnd() != null) {

				LocalDateTime start = getDateformatted(filter.getDateStart());
				LocalDateTime end = getDateformatted(filter.getDateEnd());
				
				if (start == null || end == null) {
					return null;
				}
				return cb.between(root.get("startDate"), start, end);
			}
			return null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public LocalDateTime getDateformatted(String date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			return format.parse(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		} catch (ParseException e) {
			try {
				SimpleDateFormat format2 = new SimpleDateFormat(DATE_FORMAT_NO_TIME);
				return format2.parse(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			} catch (ParseException e2) {
				log.error("Invalid Date Format", e);
				return null;
			}
		}
	}

}
