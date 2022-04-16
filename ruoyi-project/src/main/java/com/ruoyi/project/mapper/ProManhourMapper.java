package com.ruoyi.project.mapper;

import java.util.List;
import com.ruoyi.project.domain.ProManhour;

/**
 * 工时Mapper接口
 * 
 * @author horou
 * @date 2022-03-16
 */
public interface ProManhourMapper 
{

    /**
     * 查询工时
     * 
     * @param manhourId 工时主键
     * @return 工时
     */
    public ProManhour selectProManhourByManhourId(Long manhourId);

    /**
     * 查询工时列表
     * 
     * @param proManhour 工时
     * @return 工时集合
     */
    public List<ProManhour> selectProManhourList(ProManhour proManhour);

    /**
     * 新增工时
     * 
     * @param proManhour 工时
     * @return 结果
     */
    public int insertProManhour(ProManhour proManhour);

    /**
     * 修改工时
     * 
     * @param proManhour 工时
     * @return 结果
     */
    public int updateProManhour(ProManhour proManhour);

    /**
     * 删除工时
     * 
     * @param manhourId 工时主键
     * @return 结果
     */
    public int deleteProManhourByManhourId(Long manhourId);

    /**
     * 批量删除工时
     * 
     * @param manhourIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProManhourByManhourIds(Long[] manhourIds);
}
