package com.ktech.starter.dao;


import com.ktech.starter.enums.QueryComparatorEnum;



public class QueryParameter extends BaseQueryParameter<Object> {

	public QueryParameter(Object value) {

		setValue(value);
		setComparator(QueryComparatorEnum.EQ);


	}

	public QueryParameter(Object value, QueryComparatorEnum comparator) {

		setValue(value);
		setComparator(comparator);
	}

	@Override
	protected void buildClauseForComparator(StringBuilder hql, String resultName, String field, int qpCount,
			QueryParameters newQp) {

		Object queryParameterValue = getValue();

		if (queryParameterValue instanceof String && queryParameterValue != null
				&& !queryParameterValue.toString().isEmpty() && getComparator() == QueryComparatorEnum.LIKE) {

			setValue("%" + queryParameterValue.toString() + "%");
		}

		super.buildClauseForComparator(hql, resultName, field, qpCount, newQp);
	}

}
