package com.qino.repository;

import com.qino.exception.CustomException;

public interface CrudInterface<T, K>{
    T saveOne(T DTO);
    T findOne(Long id) throws CustomException;
    void deleteOne(Long id) throws CustomException;
    void deleteAll();
    T updateOne(Long id, T DTO) throws CustomException;
    K findById(Long id) throws CustomException;
}
