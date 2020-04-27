package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PaymentDao {

//    @Insert("insert into payment(serial) values(#{serial})")
    int create(Payment payment);

//    @Select("select * from payment where id = #{id}")
    Payment getPaymentById(@Param("id") long id);
}
