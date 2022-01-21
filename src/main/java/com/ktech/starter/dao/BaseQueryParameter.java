package com.ktech.starter.dao;


import com.ktech.starter.enums.QueryComparatorEnum;

public class BaseQueryParameter<T> implements IQueryParameter {

	private T						value;
	private QueryComparatorEnum comparator;
	private String					join;
	private String					joinName;
	private boolean					selectDistinct	= false;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public QueryComparatorEnum getComparator() {
		return comparator;
	}

	public void setComparator(QueryComparatorEnum comparator) {
		this.comparator = comparator;
	}

	/**
	 * @param join
	 *            the complete join(s) lines needed to use your variable
	 * @param joinVariableName
	 *            the complete HQL argument to use in the where. So, if the join
	 *            table is j1 and you want the name column, put j1.name
	 */
	public void setJoinElements(String join, String joinVariableName) {
		this.join = join;
		this.joinName = joinVariableName;
	}

	@Override
	public String getJoin() {
		return join;
	}

	public String getJoinVariableName() {
		return joinName;
	}

	public String getJoinName() {
		return joinName;
	}

	public void setJoinName(String joinName) {
		this.joinName = joinName;
	}

	public boolean isSelectDistinct() {
		return selectDistinct;
	}

	public void setSelectDistinct(boolean selectDistinct) {
		this.selectDistinct = selectDistinct;
	}

	public void setJoin(String join) {
		this.join = join;
	}

	@Override
	public String toString() {

		if (comparator == null) {
			return "QP {" + value + "}";
		}
		else {
			return "QP {" + value + ", " + comparator.getValue() + "}";
		}
	}

	@Override
	public void buildParameterClause(StringBuilder hql, String resultName, String field, int qpCount,
			QueryParameters newQp) {

		if (getJoinVariableName() != null) {
			resultName = getJoinVariableName();
		}

		if (getComparator() == QueryComparatorEnum.NOT_NULL || getComparator() == QueryComparatorEnum.NULL) {

			buildClauseForNullComparators(hql, resultName, field, qpCount);
		}
		else if (getComparator() == QueryComparatorEnum.MEMBER_OF) {

			buildClauseForMemberOf(hql, resultName, field, qpCount, newQp);
		}
		else if (value != null && !"".equals(value)) {

			buildClauseForComparator(hql, resultName, field, qpCount, newQp);
		}
	}

	public void buildJoinClause(StringBuilder hql, String resultName, String field) {

		if (getJoin() != null) {
			hql.append(" ").append(getJoin());
		}
		else if (getComparator() == QueryComparatorEnum.MEMBER_OF) {

			String joinVariableName = field.replaceAll("\\.", "");

			String join = " join " + resultName + "." + field + " " + joinVariableName;
			setJoinElements(join, joinVariableName);

			hql.append(getJoin());
		}
	}

	protected void buildClauseForNullComparators(StringBuilder hql, String resultName, String field, int qpCount) {

		hql.append(" and ");

		if (getJoinVariableName() != null) {
			hql.append(getJoinVariableName());
		}
		else {
			hql.append(resultName).append(".").append(field);
		}

		hql.append(" ").append(getComparator().getValue());
	}

	protected void buildClauseForMemberOf(StringBuilder hql, String resultName, String field, int qpCount,
			QueryParameters newQp) {

		hql.append(" and :");
		String parameterName = cleanParameterName(field, qpCount);
		hql.append(parameterName);

		hql.append(" ").append(QueryComparatorEnum.IN.getValue()).append(" ");
		appendParameterName(hql, resultName, field, qpCount);

		newQp.put(parameterName, getValue(), getComparator());
	}

	protected void buildClauseForComparator(StringBuilder hql, String resultName, String field, int qpCount,
			QueryParameters newQp) {

		hql.append(" and ");
		String parameterName = appendParameterName(hql, resultName, field, qpCount);

		QueryComparatorEnum comparator = getComparator();
		if (comparator == null) {
			throw new IllegalArgumentException("No JpaQueryComparatorEnum set for resultName[" + resultName + "] field["
					+ field + "] " + getClass().getSimpleName() + ": " + this);
		}

		hql.append(" ").append(comparator.getValue()).append(" :");
		hql.append(parameterName);

		newQp.put(parameterName, getValue(), getComparator());
	}

	protected String appendParameterName(StringBuilder hql, String rName, String field, int qpCount) {

		if (getJoinVariableName() != null) {
			hql.append(getJoinVariableName());
		}
		else {
			hql.append(rName).append(".").append(field);
		}

		return cleanParameterName(field, qpCount);
	}

	protected String cleanParameterName(String field, int qpCount) {

		if (getJoinVariableName() != null) {
			return getJoinVariableName().replaceAll("\\.", "") + qpCount;
		}
		else {
			return field.replaceAll("\\.", "") + qpCount;
		}
	}

}
