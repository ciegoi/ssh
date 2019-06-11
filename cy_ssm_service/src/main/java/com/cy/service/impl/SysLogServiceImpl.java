package com.cy.service.impl;

import com.cy.dao.ISysLogDao;
import com.cy.domain.SysLog;
import com.cy.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysLogServiceImpl implements ISysLogService {

    @Autowired
    private ISysLogDao sysAopDao;

    @Override
    public void save(SysLog sysLog) throws Exception {
        sysAopDao.save(sysLog);
    }

    @Override
    public List<SysLog> findAll() throws Exception {
        return sysAopDao.findAll();
    }
}
