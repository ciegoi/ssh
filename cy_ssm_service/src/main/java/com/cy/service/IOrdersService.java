package com.cy.service;

import com.cy.domain.Orders;

import java.util.List;

public interface IOrdersService {
    //查询所有
    List<Orders> findAll(Integer page,Integer size) throws Exception;

    //根据id查询
    Orders findById(String id) throws Exception;
}
