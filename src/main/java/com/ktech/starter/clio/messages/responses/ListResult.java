package com.ktech.starter.clio.messages.responses;

import com.ktech.starter.clio.messages.Result;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @NoArgsConstructor @ToString
public class ListResult<T> extends Result<List<T>> {

}
