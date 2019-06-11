package com.cy.dao;

import com.cy.domain.Member;
import com.cy.domain.Traveller;
import org.apache.ibatis.annotations.Select;

public interface ITravellerDao {
    @Select("select * from member where id in (select traveller from order_traveller where orderId = #{orderId})")
    public Traveller findById(String id) throws Exception;
}
