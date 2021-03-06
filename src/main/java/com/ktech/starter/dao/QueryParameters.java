package com.ktech.starter.dao;

import com.ktech.starter.enums.QueryComparatorEnum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;



/**
 * This class encapsulates all the values you need to pass back to the
 * {@link DaoService} in order to run a query. This object can be used to pass
 * back the values for your query whether it's a hand-written query or it's
 * being generated by the Dao.
 * 
 * @author bjestis
 *
 */

public class QueryParameters {

	/**
	 * The maximum number of records that will be pulled from the database
	 */
	private Integer maxResults = null;

	/**
	 * The first record that will be pulled from the database (this is important for
	 * search result pagination)
	 */
	private Integer firstRecord = 0;

	/**
	 * The full-text search value which will be passed to the Lucene search engine
	 */
	private String fullTextSearch;

	/**
	 * The columns which will be used to sort the search results
	 */
	private List<String> orderByColumns;

	/**
	 * The columns which will be used to group the search results
	 */
	private List<String> groupByColumns;

	/**
	 * Flag to indicate you want to perform a "select distinct" rather than just a
	 * "select"
	 */
	private boolean selectDistinct = false;

	/**
	 * A map of parameters to be populated on the query at the time it runs.
	 */
	private SortedMap<String, List<IQueryParameter>> params = new TreeMap<String, List<IQueryParameter>>(
			new ParameterComparator());

	/**
	 * Restrain the query results to only return records that are in this collection
	 */
	private List<?> inCollection;

	/**
	 * Restrain the query results to only return records that are not in this
	 * collection
	 */
	private List<?> notInCollection;

	/**
	 * A list of sub query parameters that will be used to create different or
	 * clauses.
	 */
	private List<QueryParameters> orClauses = new ArrayList<>();

	public QueryParameters() {

	}

	public QueryParameters(QueryParameters qp) {
		qp.copyTo(this);
	}

	public QueryParameters(String key, Object value) {
		put(key, value);
	}

	public QueryParameters(String key, Object value, QueryComparatorEnum comparator) {
		put(key, value, comparator);
	}

	public QueryParameters(Map<String, Object> valueMap) {

		for (String key : valueMap.keySet()) {
			put(key, valueMap.get(key));
		}
	}

	public boolean isEmpty() {
		return params.isEmpty();
	}

	public void remove(String key) {
		params.remove(key);
	}

	public boolean containsKey(String key) {
		return params.containsKey(key);
	}

	public void put(String key, IQueryParameter qp) {

		List<IQueryParameter> qpList;

		if (params.containsKey(key)) {
			qpList = params.get(key);
		} else {
			qpList = new ArrayList<IQueryParameter>();
		}

		if (qp.isSelectDistinct()) {
			setSelectDistinct(true);
		}

		qpList.add(qp);
		params.put(key, qpList);
	}

	public void put(String key, List<IQueryParameter> qpList) {

		if (!selectDistinct) {

			for (IQueryParameter param : qpList) {

				if (param.isSelectDistinct()) {
					setSelectDistinct(true);
					break;
				}
			}
		}

		params.put(key, qpList);
	}

	public void put(String key, Object value) {
		// Always use equals by default
		put(key, value, QueryComparatorEnum.EQ);
	}

	public void put(String key, Object value, QueryComparatorEnum comparator) {

		List<IQueryParameter> qpList;

		if (params.containsKey(key)) {
			qpList = params.get(key);
		} else {
			qpList = new ArrayList<IQueryParameter>();
		}

		qpList.add(new QueryParameter(value, comparator));
		params.put(key, qpList);
	}

	public List<IQueryParameter> get(String key) {

		return params.get(key);
	}

	public Set<String> keySet() {

		return params.keySet();
	}

	public Set<Entry<String, List<IQueryParameter>>> entrySet() {
		return params.entrySet();
	}

	public QueryParameters nextPage() {

		this.firstRecord += this.maxResults;
		return this;

	}

	public void copyTo(QueryParameters qp) {

		for (String key : params.keySet()) {
			qp.put(key, params.get(key));
		}

		qp.setFirstRecord(firstRecord);
		qp.setFullTextSearch(fullTextSearch);
		qp.setInCollection(intersection(qp.getInCollection(), inCollection));
		qp.setNotInCollection(intersection(qp.getNotInCollection(), notInCollection));
		qp.setMaxResults(maxResults);
		qp.setOrderByColumns(orderByColumns);
		qp.setSelectDistinct(selectDistinct);

		for (QueryParameters orQP : orClauses) {
			qp.addOrClause(orQP);
		}
	}

	private List<?> intersection(List<?> toCollection, List<?> fromCollection) {
		if (fromCollection == null) {
			return toCollection;
		} else if (toCollection == null) {
			return fromCollection;
		} else {
			toCollection.retainAll(fromCollection);

			return toCollection;
		}
	}

	public void clear() {

		maxResults = null;
		firstRecord = null;
		fullTextSearch = null;
		orderByColumns = null;
		groupByColumns = null;
		selectDistinct = false;
		params.clear();
		inCollection = null;
		notInCollection = null;
		orClauses = new ArrayList<>();
	}

	public void clearParameters() {
		params = new TreeMap<String, List<IQueryParameter>>(new ParameterComparator());
		orClauses = new ArrayList<>();
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public Integer getFirstRecord() {
		return firstRecord;
	}

	public void setFirstRecord(Integer firstRecord) {
		this.firstRecord = firstRecord;
	}

	public String getFullTextSearch() {
		return fullTextSearch;
	}

	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}

	public List<String> getOrderByColumns() {
		return orderByColumns;
	}

	public void setOrderByColumns(List<String> orderByColumns) {
		this.orderByColumns = orderByColumns;
	}

	public void setOrderByColumns(String... orderByColumns) {

		this.orderByColumns = new ArrayList<String>();

		if (orderByColumns != null) {

			for (String column : orderByColumns) {

				if (column != null) {
					this.orderByColumns.add(column);
				}
			}
		}
	}

	public List<String> getGroupByColumns() {
		return groupByColumns;
	}

	public void setGroupByColumns(List<String> groupByColumns) {
		this.groupByColumns = groupByColumns;
	}

	public void setGroupByColumns(String... groupByColumns) {

		this.groupByColumns = new ArrayList<String>();

		if (groupByColumns != null) {
			for (String column : groupByColumns) {

				if (column != null) {
					this.groupByColumns.add(column);
				}
			}
		}
	}

	/**
	 * This method will set the "default" order-by columns to use when generating
	 * HQL. If the order-by columns have already been set, this method won't replace
	 * the value that's already there. An example usage would be if you have a page
	 * that displays search results and you want the first search to sort by a
	 * specific column or columns, but want to preserve the column the user has
	 * chosen to sort by on subsequent searches.
	 * 
	 * @param defaultOrderByColumns
	 */
	public void setDefaultOrderByColumns(String... defaultOrderByColumns) {

		if (orderByColumns == null || orderByColumns.isEmpty()) {

			setOrderByColumns(defaultOrderByColumns);
		}
	}

	public boolean isSelectDistinct() {
		return selectDistinct;
	}

	public void setSelectDistinct(boolean selectDistinct) {
		this.selectDistinct = selectDistinct;
	}

	public List<?> getInCollection() {
		return inCollection;
	}

	public void setInCollection(List<?> inCollection) {
		this.inCollection = inCollection;
	}

	public List<?> getNotInCollection() {
		return notInCollection;
	}

	public void setNotInCollection(List<?> notInCollection) {
		this.notInCollection = notInCollection;
	}

	public List<QueryParameters> getOrClauses() {
		return orClauses;
	}

	public void setOrClauses(List<QueryParameters> orClauses) {
		this.orClauses = orClauses;
	}

	public void addOrClause(QueryParameters qp) {
		orClauses.add(qp);
	}

	public void addOrClause(String key, Object value, QueryComparatorEnum comparator) {
		orClauses.add(new QueryParameters(key, value, comparator));
	}

	/**
	 * Set the values to be used for search paging.
	 * 
	 * @param pageNumber
	 *            The first page of search results you want to display
	 * @param pageSize
	 *            The number of search results you want to display
	 */
	public void setSearchPaging(int pageNumber, int pageSize) {

		setFirstRecord(pageNumber * pageSize);
		setMaxResults(pageSize);
	}


	private class ParameterComparator implements Comparator<String> {

		public int compare(String key1, String key2) {
			int returnValue = 0;

			if (isDigit(key1) && isDigit(key2)) {

				Long keyLong1 = Long.parseLong(key1);
				Long keyLong2 = Long.parseLong(key2);
				if (keyLong1 > keyLong2)
					returnValue = 1;
				else if (keyLong1 < keyLong2)
					returnValue = -1;
				else
					returnValue = 0;

			} else {

				returnValue = key1.compareTo(key2);

			}
			return returnValue;
		}

		private boolean isDigit(String str) {

			if (str == null || str.length() == 0)
				return false;

			for (int i = 0; i < str.length(); i++) {

				if (!Character.isDigit(str.charAt(i)))
					return false;
			}

			return true;
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("QueryParameter");
		if (!fullTextSearch.isEmpty()) {
			sb.append(" :fullTextSearch=").append(fullTextSearch);
		}
		if (!params.isEmpty()) {
			sb.append(" :params=").append(params.toString());
		}

		return sb.toString();
	}

}
