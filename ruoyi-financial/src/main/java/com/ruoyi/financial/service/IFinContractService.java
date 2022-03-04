package com.ruoyi.financial.service;


import java.util.List;
import com.ruoyi.common.core.domain.entity.FinContract;

/**
 * 合同 业务层
 *
 * @author horou
 */
public interface IFinContractService {
    /**
     * 根据条件分页查询合同列表
     *
     * @param contract 合同信息
     * @return 合同信息集合信息
     */
    public List<FinContract> selectContractList(FinContract contract);

    /**
     * 根据条件分页查询已分配合同角色列表
     *
     * @param contract 合同信息
     * @return 合同信息集合信息
     */
    public List<FinContract> selectAllocatedList(FinContract contract);

    /**
     * 根据条件分页查询未分配合同角色列表
     *
     * @param contract 合同信息
     * @return 合同信息集合信息
     */
    public List<FinContract> selectUnallocatedList(FinContract contract);

    /**
     * 通过合同名查询合同
     *
     * @param contractName 合同名
     * @return 合同对象信息
     */
    public FinContract selectContractByContractName(String contractName);

    /**
     * 通过合同ID查询合同
     *
     * @param contractId 合同ID
     * @return 合同对象信息
     */
    public FinContract selectContractById(Long contractId);

    /**
     * 根据合同ID查询合同所属角色组
     *
     * @param contractName 合同名
     * @return 结果
     */
    public String selectContractRoleGroup(String contractName);

    /**
     * 根据合同ID查询合同所属岗位组
     *
     * @param contractName 合同名
     * @return 结果
     */
    public String selectContractPostGroup(String contractName);

    /**
     * 校验合同名称是否唯一
     *
     * @param contractName 合同名称
     * @return 结果
     */
    public String checkContractNameUnique(String contractName);

    /**
     * 校验手机号码是否唯一
     *
     * @param contract 合同信息
     * @return 结果
     */
    public String checkPhoneUnique(FinContract contract);

    /**
     * 校验email是否唯一
     *
     * @param contract 合同信息
     * @return 结果
     */
    public String checkEmailUnique(FinContract contract);

    /**
     * 校验合同是否允许操作
     *
     * @param contract 合同信息
     */
    public void checkContractAllowed(FinContract contract);

    /**
     * 校验合同是否有数据权限
     *
     * @param contractId 合同id
     */
    public void checkContractDataScope(Long contractId);

    /**
     * 新增合同信息
     *
     * @param contract 合同信息
     * @return 结果
     */
    public int insertContract(FinContract contract);

    /**
     * 注册合同信息
     *
     * @param contract 合同信息
     * @return 结果
     */
    public boolean registerContract(FinContract contract);

    /**
     * 修改合同信息
     *
     * @param contract 合同信息
     * @return 结果
     */
    public int updateContract(FinContract contract);

    /**
     * 合同授权角色
     *
     * @param contractId 合同ID
     * @param roleIds 角色组
     */
    public void insertContractAuth(Long contractId, Long[] roleIds);

    /**
     * 修改合同状态
     *
     * @param contract 合同信息
     * @return 结果
     */
    public int updateContractStatus(FinContract contract);

    /**
     * 修改合同基本信息
     *
     * @param contract 合同信息
     * @return 结果
     */
    public int updateContractProfile(FinContract contract);

    /**
     * 修改合同头像
     *
     * @param contractName 合同名
     * @param avatar 头像地址
     * @return 结果
     */
    public boolean updateContractAvatar(String contractName, String avatar);

    /**
     * 重置合同密码
     *
     * @param contract 合同信息
     * @return 结果
     */
    public int resetPwd(FinContract contract);

    /**
     * 重置合同密码
     *
     * @param contractName 合同名
     * @param password 密码
     * @return 结果
     */
    public int resetContractPwd(String contractName, String password);

    /**
     * 通过合同ID删除合同
     *
     * @param contractId 合同ID
     * @return 结果
     */
    public int deleteContractById(Long contractId);

    /**
     * 批量删除合同信息
     *
     * @param contractIds 需要删除的合同ID
     * @return 结果
     */
    public int deleteContractByIds(Long[] contractIds);

    /**
     * 导入合同数据
     *
     * @param contractList 合同数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作合同
     * @return 结果
     */
    public String importContract(List<FinContract> contractList, Boolean isUpdateSupport, String operName);





}
