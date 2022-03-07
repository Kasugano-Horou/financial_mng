package com.ruoyi.financial.mapper;

import com.ruoyi.common.core.domain.entity.FinContract;
import java.util.List;

public interface FinContractMapper {

    /**
     * 根据条件分页查询合同列表
     *
     * @param finContract 合同信息
     * @return 合同信息集合信息
     */
    public List<FinContract> selectContractList(FinContract finContract);


}
