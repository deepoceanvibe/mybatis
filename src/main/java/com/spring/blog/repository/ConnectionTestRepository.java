package com.spring.blog.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper     // 빈 컨테이너에 'mybatis 관리 파일'이라는 걸 명시하는 거임
public interface ConnectionTestRepository {

    // getNow() 실행시 호출할 SQL 구문은 xml 파일 내부에 작성한다.
    String getNow();

}
