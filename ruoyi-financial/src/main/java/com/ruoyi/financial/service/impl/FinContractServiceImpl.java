package com.ruoyi.financial.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Validator;

import com.ruoyi.common.core.domain.entity.FinContract;
import com.ruoyi.financial.mapper.FinContractMapper;
import com.ruoyi.financial.service.IFinContractService;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.domain.SysUserPost;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysPostMapper;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserPostMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.financial.service.IFinContractService;
import com.ruoyi.system.service.ISysUserService;

/**
 * 合同 业务层处理
 *
 * @author ruoyi
 */
@Service
public class FinContractServiceImpl implements IFinContractService
{
    private static final Logger log = LoggerFactory.getLogger(FinContractServiceImpl.class);


    @Autowired
    private FinContractMapper contractMapper;

    @Override
    public List<FinContract> selectContractList(FinContract contract)
    {
        return  contractMapper.selectContractList(contract);
    }

    @Override
    public List<FinContract> selectAllocatedList(FinContract contract) {
        return null;
    }

    @Override
    public List<FinContract> selectUnallocatedList(FinContract contract) {
        return null;
    }

    @Override
    public FinContract selectContractByContractName(String contractName) {
        return null;
    }

    @Override
    public FinContract selectContractById(Long contractId) {
        return null;
    }

    @Override
    public String selectContractRoleGroup(String contractName) {
        return null;
    }

    @Override
    public String selectContractPostGroup(String contractName) {
        return null;
    }

    @Override
    public String checkContractNameUnique(String contractName) {
        return null;
    }

    @Override
    public String checkPhoneUnique(FinContract contract) {
        return null;
    }

    @Override
    public String checkEmailUnique(FinContract contract) {
        return null;
    }

    @Override
    public void checkContractAllowed(FinContract contract) {

    }

    @Override
    public void checkContractDataScope(Long contractId) {

    }

    @Override
    public int insertContract(FinContract contract) {
        return 0;
    }

    @Override
    public boolean registerContract(FinContract contract) {
        return false;
    }

    @Override
    public int updateContract(FinContract contract) {
        return 0;
    }

    @Override
    public void insertContractAuth(Long contractId, Long[] roleIds) {

    }

    @Override
    public int updateContractStatus(FinContract contract) {
        return 0;
    }

    @Override
    public int updateContractProfile(FinContract contract) {
        return 0;
    }

    @Override
    public boolean updateContractAvatar(String contractName, String avatar) {
        return false;
    }

    @Override
    public int resetPwd(FinContract contract) {
        return 0;
    }

    @Override
    public int resetContractPwd(String contractName, String password) {
        return 0;
    }

    @Override
    public int deleteContractById(Long contractId) {
        return 0;
    }

    @Override
    public int deleteContractByIds(Long[] contractIds) {
        return 0;
    }

    @Override
    public String importContract(List<FinContract> contractList, Boolean isUpdateSupport, String operName) {
        return null;
    }

}
