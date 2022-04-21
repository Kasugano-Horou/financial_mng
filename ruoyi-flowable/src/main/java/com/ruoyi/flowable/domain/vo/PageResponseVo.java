package com.ruoyi.flowable.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * @author yuwei
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseVo<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 数据条数
	 */
	private Integer size;
	/**
	 * 数据项
	 */
	private List<T> list;
}
