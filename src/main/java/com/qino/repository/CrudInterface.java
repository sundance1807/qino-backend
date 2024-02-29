package com.qino.repository;

import com.qino.exception.CustomException;

import java.util.Set;

public interface CrudInterface<T, K>{
    T saveOne(T DTO);
    T findOne(Long id) throws CustomException;
    void deleteOne(Long id) throws CustomException;
    T updateOne(Long id, T DTO) throws CustomException;
    Set<T> saveAll(Set<T> DTOSet);
    Set<T> findAll();
    K findById(Long id) throws CustomException;
}
