package com.sharding.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Mapper
@Component
public interface ShardingMapper {
    @Insert("insert into t_order(price,user_id,status) values(#{price},#{userId},#{status})")
    public void add(@Param("price")BigDecimal price, @Param("userId")Long userId,
                    @Param("status")String status);
}
