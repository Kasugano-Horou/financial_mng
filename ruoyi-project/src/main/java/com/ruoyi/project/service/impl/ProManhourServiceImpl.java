package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProManhourMapper;
import com.ruoyi.project.domain.ProManhour;
import com.ruoyi.project.service.IProManhourService;

/**
 * 工时Service业务层处理
 * 
 * @author horou
 * @date 2022-03-16
 */
@Service
public class ProManhourServiceImpl implements IProManhourService 
{
    @Autowired
    private ProManhourMapper proManhourMapper;

    /**
     * 查询工时
     * 
     * @param manhourId 工时主键
     * @return 工时
     */
    @Override
    public ProManhour selectProManhourByManhourId(Long manhourId)
    {
        return proManhourMapper.selectProManhourByManhourId(manhourId);
    }

    /**
     * 查询工时列表
     * 
     * @param proManhour 工时
     * @return 工时
     */
    @Override
    public List<ProManhour> selectProManhourList(ProManhour proManhour)
    {
        return proManhourMapper.selectProManhourList(proManhour);
    }

    /**
     * 新增工时
     * 
     * @param proManhour 工时
     * @return 结果
     */
    @Override
    public int insertProManhour(ProManhour proManhour)
    {
        proManhour.setCreateTime(DateUtils.getNowDate());
        return proManhourMapper.insertProManhour(proManhour);
    }

    /**
     * 修改工时
     * 
     * @param proManhour 工时
     * @return 结果
     */
    @Override
    public int updateProManhour(ProManhour proManhour)
    {
        proManhour.setUpdateTime(DateUtils.getNowDate());
        return proManhourMapper.updateProManhour(proManhour);
    }

    /**
     * 批量删除工时
     * 
     * @param manhourIds 需要删除的工时主键
     * @return 结果
     */
    @Override
    public int deleteProManhourByManhourIds(Long[] manhourIds)
    {
        return proManhourMapper.deleteProManhourByManhourIds(manhourIds);
    }

    /**
     * 删除工时信息
     * 
     * @param manhourId 工时主键
     * @return 结果
     */
    @Override
    public int deleteProManhourByManhourId(Long manhourId)
    {
        return proManhourMapper.deleteProManhourByManhourId(manhourId);
    }
}
