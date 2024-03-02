package com.qino.inteface;

import com.qino.exception.CustomException;

import java.util.Set;

public interface CastServiceInterface<T, K>{
    T saveOne(T DTO);
    T findOne(Long id) throws CustomException;
    T updateOne(Long id, T DTO) throws CustomException;
    void deleteOne(Long id) throws CustomException;
    Set<T> findAll();
    K findById(Long id) throws CustomException;
}
