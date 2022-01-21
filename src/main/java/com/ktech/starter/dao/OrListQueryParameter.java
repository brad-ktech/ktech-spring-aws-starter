package com.ktech.starter.dao;

import com.ktech.starter.enums.QueryComparatorEnum;

import java.util.List;




@SuppressWarnings("rawtypes")
public class OrListQueryParameter extends BaseQueryParameter<List> {

	public OrListQueryParameter(List value, QueryComparatorEnum comparator) {

		setValue(value);
		setComparator(comparator);
	}

	@Override
	protected void buildClauseForMemberOf(StringBuilder hql, String resultName, String field, int qpCount,
			QueryParameters newQp) {

		int orCount = 0;
		List queryValues = getValue();

		if (queryValues == null || queryValues.isEmpty()) {
			return;
		}

		hql.append(" and (");

		for (Object queryValue : queryValues) {

			if (orCount > 0) {
				hql.append(" or ");
			}

			orCount++;

			String parameterName = cleanParameterName(field, qpCount) + "orParam" + orCount;
			hql.append(":" + parameterName);

			hql.append(" ").append(QueryComparatorEnum.IN.getValue()).append(" ");
			appendParameterName(hql, resultName, field, qpCount);

			newQp.put(parameterName, queryValue, getComparator());
		}

		hql.append(")");
	}

	@Override
	protected void buildClauseForComparator(StringBuilder hql, String resultName, String field, int qpCount,
			QueryParameters newQp) {

		int orCount = 0;
		List queryValues = getValue();
		QueryComparatorEnum comparator = getComparator();
		if (queryValues == null || queryValues.isEmpty()) {
			return;
		}
		else if (comparator == null) {
			throw new IllegalArgumentException("No JpaQueryComparatorEnum set for resultName[" + resultName + "] field["
					+ field + "] " + getClass().getSimpleName() + ": " + this);
		}

		hql.append(" and (");

		for (Object queryValue : queryValues) {

			if (orCount > 0) {
				hql.append(" or ");
			}

			orCount++;

			String parameterName = appendParameterName(hql, resultName, field, qpCount) + "orParam" + orCount;

			hql.append(" ").append(getComparator().getValue()).append(" :");
			hql.append(parameterName);

			if (queryValue instanceof String && queryValue != null && !queryValue.toString().isEmpty()
					&& getComparator() == QueryComparatorEnum.LIKE) {

				newQp.put(parameterName, "%" + queryValue.toString() + "%", comparator);
			}
			else {
				newQp.put(parameterName, queryValue, comparator);
			}
		}

		hql.append(")");
	}
}
