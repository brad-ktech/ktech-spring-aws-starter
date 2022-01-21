package com.ktech.starter.dao;


import com.ktech.starter.enums.QueryComparatorEnum;

public interface IQueryParameter {

	public Object getValue();
	public QueryComparatorEnum getComparator();
	public String getJoin();
	public boolean isSelectDistinct();

	public void buildParameterClause(StringBuilder hql, String resultName,
                                     String field, int qpCount, QueryParameters newQp);
	public void buildJoinClause(StringBuilder hql, String resultName, String field);
}
