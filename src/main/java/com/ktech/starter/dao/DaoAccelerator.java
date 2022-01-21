package com.ktech.starter.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.ktech.starter.enums.QueryComparatorEnum;
import com.ktech.starter.utilities.Reflectotron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;


@Service
public class DaoAccelerator {

	
	private AutoDaoService dao;
	private QueryParameters qp = new QueryParameters();
	private Class<?> clazz;
	private String key = null;
	private String query = null;
	
	

	public DaoAccelerator(@Autowired AutoDaoService ads) {

		this.dao = ads;

	}

	public DaoAccelerator select() {
		reset();
		qp.setSelectDistinct(false);
		return this;
	}

	public DaoAccelerator selectDistict() {
		reset();
		qp.setSelectDistinct(true);
		return this;
	}

	public <T> DaoAccelerator from(Class<T> clazz) {

		this.clazz = clazz;
		return this;
	}

	public DaoAccelerator where(String key) {

		this.key = key;
		return this;
	}

	public DaoAccelerator and(String key) {

		return where(key);

	}

	public DaoAccelerator eq(Object value) {
		
		put(value, QueryComparatorEnum.EQ);
		return this;
	}
	
	public DaoAccelerator gt(Object value) {
		
		put(value, QueryComparatorEnum.GT);
		return this;
	}
	
	public DaoAccelerator gte(Object value) {
		
		put(value, QueryComparatorEnum.GTE);
		return this;
	}
	
	public DaoAccelerator lt(Object value) {
		
		put(value, QueryComparatorEnum.LT);
		return this;
	}
	
	public DaoAccelerator lte(Object value) {
		
		put(value, QueryComparatorEnum.LTE);
		return this;
	}
	
	public DaoAccelerator like(Object value) {
		
		put(value, QueryComparatorEnum.LIKE);
		return this;
	}
	


	public DaoAccelerator nul() {
		
		put(null, QueryComparatorEnum.NULL);
		return this;
	}
	
	public DaoAccelerator notnul() {
		
		put(null, QueryComparatorEnum.NOT_NULL);
		return this;
	}

	public DaoAccelerator groupBy(String... groupByColumns) {
		qp.setGroupByColumns(groupByColumns);
		return this;
	}

	public DaoAccelerator groupBy(List<String> groupByColumns) {

		qp.setGroupByColumns(groupByColumns);
		return this;
	}

	public DaoAccelerator orderBy(String... orderByColumns) {

		qp.setOrderByColumns(orderByColumns);
		return this;
	}

	public DaoAccelerator orderBy(List<String> orderByColumns) {

		qp.setGroupByColumns(orderByColumns);
		return this;

	}

	public DaoAccelerator in(List<?> inCollection) {

		qp.setInCollection(inCollection);
		return this;
	}

	public DaoAccelerator notIn(List<?> notInCollection) {

		qp.setNotInCollection(notInCollection);
		return this;
	}

	public DaoAccelerator setPaging(Integer pageNumber, Integer pageSize) {

		qp.setSearchPaging(pageNumber, pageSize);
		return this;
	}

	public DaoAccelerator setMaxResults(Integer maxSize) {

		qp.setMaxResults(maxSize);
		return this;
	}

	public DaoAccelerator withQuery(String query){
		this.query = query;
		return this;
	}
	

	public <T> Optional<Stream<T>> stream(){
		
		Optional<Stream<T>> stream;
		Optional<List<T>> results = dao.findByParameters(clazz, qp);
		if(results.isPresent()) {
			stream = Optional.of(results.get().stream());
		}else {
			stream = Optional.empty();
		}
		
		return stream;
		
	}
	
	public <T> Optional<Stream<T>> nextStream() {
		
		qp.nextPage();
		return stream();
	}
	
	public <T> Optional<List<T>> query(){
		
		return dao.findByParameters(clazz, qp);
		
	}
	
	public <T> Optional<T> queryOne(){
		
		return dao.find(clazz, qp);
	}

	public <T> Optional<List<T>> executeSQL(){

		return dao.findByQuery(clazz, query, qp);

	}
	
	public <T> Optional<List<T>> next() {
		qp.nextPage();
		return query();
	}
	

	
	public Integer count() {
		return dao.count(clazz, qp);
	}
	
	public <T> T save(T entity) {
		return (T) dao.save(entity);
	}
	
	public <T> List<T> save(List<T> entities){
		
		List<T> updated = new ArrayList<>();
		entities.forEach(e -> {
		   updated.add(save(e));
		});
		
		return updated;
	}
		
    public <T> T update(T entity, String fieldName, Object value){
    	
    	try {
    		Field field = Reflectotron.getField(entity.getClass(), fieldName);
        	field.setAccessible(true);
			field.set(entity, value);
			field.setAccessible(false);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return save(entity);
    }
    
    public <T> List<T> updateAll(List<T> entities, String fieldName, Object value){
    	
    	List<T> updated = new ArrayList<>();
		entities.forEach(e -> {
		   updated.add(update(e, fieldName, value));
		});
		
		return updated;
    	
    }
    
    private void put(Object value, QueryComparatorEnum numm) {
    	qp.put(this.key, value, numm);
    }

	private void reset(){

		qp = new QueryParameters();
		clazz = null;
		key = null;
		query = null;
	}
}
