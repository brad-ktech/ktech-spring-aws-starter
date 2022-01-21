package com.ktech.starter.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.ktech.starter.enums.BaseEnum;
import com.ktech.starter.enums.QueryComparatorEnum;
import com.ktech.starter.enums.QueryEnum;
import com.ktech.starter.utilities.Reflectotron;
import org.apache.commons.collections4.ListUtils;
import org.apache.logging.log4j.Logger;


public abstract class BaseDaoService<T> implements Dao<T> { 

	private static final String RESULT_NAME = "result";
	

	public Optional<T> find(Class<T> clazz, Object id) {

		return Optional.ofNullable(getEntityManager().find(clazz, id));

				
	}


	public Optional<T> find(Class<T> clazz, QueryParameters qp) {

		T t = null;
		Query q = null;
		try {
			q = buildQuery(clazz, qp);
			t = (T) q.getSingleResult();

		} catch (NoResultException e) {
			getLogger().debug("No result found for query: {}");
		}

		return Optional.ofNullable(t);
	}

	public Optional<T> find(Class<T> clazz, String query, QueryParameters qp) {
		T t = null;
		Query q = null;
		try {
			q = bind(query, qp);
			t = (T) q.getSingleResult();
		} catch (NoResultException e) {
			getLogger().debug("No result found for query: {}", q);
		}
		return Optional.ofNullable(t);
	}
	
	public  T findAlll(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		
		return clazz.newInstance();
	}

	public Optional<T> find(Class<T> clazz, QueryEnum hql, QueryParameters qp) {

		return find(clazz, hql.getQuery(), qp);
	}

	public Optional<List<T>> findAll(Class<T> clazz) {
		StringBuilder sb = new StringBuilder("select object(o) from ");
		sb.append(clazz.getName());
		sb.append(" o");

		return findByQuery(clazz, sb.toString(), null);
	}

	public Optional<List<T>> findAll(Class<T> clazz, Comparator<T> comparator) {

		Optional<List<T>> results = findAll(clazz);
		if(results.isPresent())
		Collections.sort(results.get(), comparator);

		return results;
	}

	public Optional<List<T>> findByQuery(Class<T> clazz, String query, QueryParameters qp) {

		Optional<List<T>> opt;
		
		Optional<List<?>> raw = findByQuery(query, qp);
		if(raw.isPresent()) {
			List<T> results = new ArrayList<T>();
			List<?> rawz = raw.get();
			results = rawz.stream().map(r->clazz.cast(r)).collect(Collectors.toList());
			opt = Optional.ofNullable(results);
		}else {
			opt = Optional.empty();
		}
		
		return opt;
	}

	public Optional<List<?>> findByQuery(String query, QueryParameters qp) {
		Query q = bind(query, qp);
		setResultSetLimits(q, qp);
		return Optional.ofNullable(q.getResultList());
	}

	public Optional<List<T>> findByQuery(Class<T> clazz, QueryEnum hql, QueryParameters qp) {
		return findByQuery(clazz, hql.getQuery(), qp);
	}

	public Optional<List<T>> findByNativeQuery(Class<T> clazz, String query, QueryParameters qp) {

		Query q = bind(getEntityManager().createNativeQuery(query, clazz), qp);
		setResultSetLimits(q, qp);
		return Optional.ofNullable(q.getResultList());

	}

	public Optional<List<T>> findByNativeQuery(Class<T> clazz, QueryEnum num, QueryParameters qp) {
		return findByNativeQuery(clazz, num.getQuery(), qp);
	}



	public Optional<List<T>> findByParameters(Class<T> clazz, QueryParameters qp) {
		Query q = buildQuery(clazz, qp);
		setResultSetLimits(q, qp);
		return Optional.ofNullable(q.getResultList());
	}

	public <T> int count(Class<T> clazz, QueryParameters qp) {

		Query countQuery = buildCountQuery(clazz, qp);
		Long count = (Long) countQuery.getSingleResult();

		return count.intValue();
	}

	public <T> T save(T entity) {

		T merged = null;
		if (entity != null) {

			merged = getEntityManager().merge(entity);
		}

		return merged;
	}
	
	

	public int executeNativeUpdate(String query) {
		Query q = getEntityManager().createNativeQuery(query);
		return q.executeUpdate();

	}

	public <T> void remove(T entity) {

		getEntityManager().remove(getEntityManager().contains(entity) ? entity : getEntityManager().merge(entity));
	}




	/*
	 * ALL PRIVATE METHODS BELOW
	 */

	private Query buildQuery(Class<?> clazz, QueryParameters qp) {

		StringBuilder hql = new StringBuilder();
		hql.append("select ");

		if (qp.isSelectDistinct() || isSelectDistinct(qp)) {
			hql.append("distinct ");
		}

		hql.append(RESULT_NAME + " from ");

		hql.append(clazz.getName());
		hql.append(" " + RESULT_NAME);
		QueryParameters newQp = buildHQLBody(clazz, qp, hql, RESULT_NAME);

		return bind(hql.toString(), newQp);
	}

	private QueryParameters buildHQLBody(Class<?> clazz, QueryParameters qp, StringBuilder hql, String resultName) {

		buildJoinElements(clazz, qp, hql, RESULT_NAME);
		hql.append(" where 1=1");

		QueryParameters newQp = new QueryParameters();

		buildWhereClause(qp, hql, newQp, resultName);

		// Append the order by clause (will be empty string if no order by
		// parameters were set)
		hql.append(getOrderByClause(RESULT_NAME, qp.getOrderByColumns(), clazz));
		hql.append(getGroupByClause(RESULT_NAME, qp.getGroupByColumns()));

		return newQp;
	}

	private void buildWhereClause(QueryParameters qp, StringBuilder hql, QueryParameters newQp, String resultName) {
		buildWhereClause(qp, hql, newQp, resultName, 0);
	}

	private int buildWhereClause(QueryParameters qp, StringBuilder hql, QueryParameters newQp, String resultName,
			Integer count) {

		int qpCount = count;

		newQp.setFirstRecord(qp.getFirstRecord());
		if(qp.getMaxResults() != null) {
			newQp.setMaxResults(qp.getMaxResults());
		}

		if (qp.getInCollection() != null && !qp.getInCollection().isEmpty()) {

			buildInCollectionClause(qp, hql, newQp, resultName);
		}

		if (qp.getNotInCollection() != null && !qp.getNotInCollection().isEmpty()) {

			buildNotInCollectionClause(qp, hql, newQp, resultName);
		}

		for (String field : qp.keySet()) {

			List<IQueryParameter> RdbmsQueryParameters = qp.get(field);

			for (IQueryParameter queryParameter : RdbmsQueryParameters) {

				qpCount++;
				queryParameter.buildParameterClause(hql, resultName, field, qpCount, newQp);
			}
		}

		if (!qp.getOrClauses().isEmpty()) {
			hql.append(" and ( ");
			for (QueryParameters orRdbmsQueryParameters : qp.getOrClauses()) {
				hql.append(" ( 1 = 1 ");
				qpCount = buildWhereClause(orRdbmsQueryParameters, hql, newQp, resultName, qpCount);

				hql.append(" ) or ");
			}
			hql.delete(hql.length() - 3, hql.length());
			hql.append(" ) ");
		}

		return qpCount;
	}

	private String getOrderByClause(String resultName, List<String> orderByColumns, Class<?> clazz) {

		if (orderByColumns == null || orderByColumns.isEmpty()) {

			return "";
		} else {

			StringBuilder orderBy = new StringBuilder();
			orderBy.append(" order by ");

			for (int i = 0; i < orderByColumns.size(); i++) {

				if (i != 0) {
					orderBy.append(", ");
				}

				orderBy.append(resultName + "." + orderByColumns.get(i));
			}

			Field idField = Reflectotron.getIdField(clazz);
			orderBy.append(", ");
			orderBy.append(resultName + "." + idField.getName());

			return orderBy.toString();
		}
	}

	private String getGroupByClause(String resultName, List<String> groupByColumns) {

		if (groupByColumns == null || groupByColumns.isEmpty()) {

			return "";
		} else {

			StringBuilder groupBy = new StringBuilder();
			groupBy.append(" group by ");

			for (int i = 0; i < groupByColumns.size(); i++) {

				if (i != 0) {
					groupBy.append(", ");
				}

				groupBy.append(resultName + "." + groupByColumns.get(i));
			}

			return groupBy.toString();
		}
	}

	private void buildJoinElements(Class<?> clazz, QueryParameters qp, StringBuilder hql, String resultName) {

		for (String key : qp.keySet()) {
			List<IQueryParameter> RdbmsQueryParameters = qp.get(key);

			for (IQueryParameter queryParameter : RdbmsQueryParameters) {

				queryParameter.buildJoinClause(hql, resultName, key);

				if (QueryComparatorEnum.MEMBER_OF == queryParameter.getComparator()) {

					qp.setSelectDistinct(true);
				}
			}
		}
	}

	private Query buildCountQuery(Class<?> clazz, QueryParameters qp) {

		StringBuilder hql = new StringBuilder();
		hql.append("select ");

		if (qp.isSelectDistinct() || isSelectDistinct(qp)) {
			hql.append("count(distinct result) from ");
		} else {
			hql.append("count(*) from ");
		}

		hql.append(clazz.getName());
		hql.append(" " + RESULT_NAME);
		QueryParameters newQp = buildHQLBody(clazz, qp, hql, RESULT_NAME);

		return bind(hql.toString(), newQp);
	}

	private Query bind(String query, QueryParameters qp) {
		Query q = getEntityManager().createQuery(query);
		return bind(q, qp);
	}

	@SuppressWarnings("rawtypes")
	private Query bind(Query q, QueryParameters qp) {

		if (qp != null && !qp.isEmpty()) {

			for (String key : qp.keySet()) {

				List<IQueryParameter> params = qp.get(key);
				IQueryParameter param = params.get(0);

				Object paramValue = param.getValue();

				if (paramValue instanceof BaseEnum) {
					paramValue = ((BaseEnum) paramValue).getValue();
				}

				q.setParameter(key, paramValue);
			}
		}

		return q;
	}

	private boolean isSelectDistinct(QueryParameters qp) {

		for (String key : qp.keySet()) {
			List<IQueryParameter> RdbmsQueryParameters = qp.get(key);

			for (IQueryParameter queryParameter : RdbmsQueryParameters) {

				if (queryParameter.getComparator() == QueryComparatorEnum.MEMBER_OF) {
					return true;
				}
			}
		}

		return false;
	}

	private void buildInCollectionClause(QueryParameters qp, StringBuilder hql, QueryParameters newQp,
			String resultName) {

		hql.append(" and ");


		List<?> partitions = ListUtils.partition(qp.getInCollection(), 500);

		if (partitions.size() == 1) {

			hql.append(resultName);
			hql.append(" in :inCollection");

			newQp.put("inCollection", qp.getInCollection());
		} else {

			hql.append("(");

			for (int i = 0; i < partitions.size(); i++) {

				if (i != 0) {
					hql.append(" or ");
				}

				List<?> partition = (List<?>) partitions.get(i);

				String inCollectionName = "inCollection" + i;

				hql.append(resultName);
				hql.append(" in (:" + inCollectionName + ")");
				newQp.put(inCollectionName, partition);

			}

			hql.append(")");
		}
	}

	private void buildNotInCollectionClause(QueryParameters qp, StringBuilder hql, QueryParameters newQp,
			String resultName) {

		hql.append(" and ");
		List<?> partitions = ListUtils.partition(qp.getInCollection(), 100);

		if (partitions.size() == 1) {

			hql.append(resultName);
			hql.append(" not in :notInCollection");

			newQp.put("notInCollection", qp.getNotInCollection());
		} else {

			hql.append("(");

			for (int i = 0; i < partitions.size(); i++) {

				if (i != 0) {
					hql.append(" and ");
				}

				List<?> partition = (List<?>) partitions.get(i);

				String notInCollectionName = "notInCollection" + i;

				hql.append(resultName);
				hql.append(" not in (:" + notInCollectionName + ")");
				newQp.put(notInCollectionName, partition);
			}

			hql.append(")");
		}
	}

	private void setResultSetLimits(Query q, QueryParameters qp) {

		if (qp != null && qp.getFirstRecord() != null) {
			q.setFirstResult(qp.getFirstRecord());
		}

		if (qp != null && qp.getMaxResults() != null) {
			q.setMaxResults(qp.getMaxResults());
		}
	}

	


	private <T> List<T> orderIndexedSearch(List<T> results, List<T> indexed) {

		List<T> ordered = new ArrayList<T>();

		for (T entity : indexed) {

			if (results.contains(entity)) {
				ordered.add(entity);
			}
		}

		return ordered;
	}

	public abstract EntityManager getEntityManager();

	public abstract Logger getLogger();
	

	
	
	
}
