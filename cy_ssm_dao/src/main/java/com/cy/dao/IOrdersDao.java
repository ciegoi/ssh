package com.cy.dao;

import com.cy.domain.Member;
import com.cy.domain.Orders;
import com.cy.domain.Product;
import com.cy.domain.Traveller;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IOrdersDao {

    //订单查询
    @Select("select * from orders")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product",javaType = Product.class,one = @One(select = "com.cy.dao.IProductDao.findById")),
    })
    List<Orders> findAll();

    //根据id查询
    @Select("select * from orders where id = #{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product",javaType = Product.class,one = @One(select = "com.cy.dao.IProductDao.findById")),
            @Result(column = "MemberId",property = "member",javaType = Member.class,one = @One(select = "com.cy.dao.IMemberDao.findById")),
            @Result(column = "TravellerId",property = "Traveller",javaType = Traveller.class,one = @One(select = "com.cy.dao.ITravellerDao.findById")),
    })
    Orders findById(String id) throws Exception;
}
