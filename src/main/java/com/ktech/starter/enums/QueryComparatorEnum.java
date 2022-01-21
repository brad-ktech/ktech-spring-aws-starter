package com.ktech.starter.enums;



public enum QueryComparatorEnum implements BaseEnum<String> {
	EQ("="),
	NEQ("!="),
	GT(">"),
	GTE(">="),
	LT("<"),
	LTE("<="),
	LIKE("like"),
	IN("in"),
	NOT_IN("not in"),
	NULL("is NULL"),
	NOT_NULL("is not NULL"),
	MEMBER_OF("member of");

	private String value;

	private QueryComparatorEnum(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	public static final QueryComparatorEnum getEnumFromValue(String value) {

		for (QueryComparatorEnum actionEnum : values()) {

			if (actionEnum.getValue().equalsIgnoreCase(value)) {
				return actionEnum;
			}
		}

		return null;
	}

}
