//package com.qino.service;
//
//import com.qino.exception.CustomException;
//import com.qino.model.dto.ComposerDTO;
//import com.qino.model.entity.cast.ComposerEntity;
//import com.qino.repository.ComposerRepository;
//import com.qino.repository.CrudInterface;
//import com.qino.util.Generator;
//import com.qino.util.MessageSource;
//import lombok.AllArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class ComposerService implements CrudInterface<ComposerDTO, ComposerEntity> {
//    private ComposerRepository composerRepository;
//    private ModelMapper modelMapper;
//    private Generator nameGenerator;
//
//
//    @Override
//    public ComposerDTO saveOne(ComposerDTO composerDTO) {
//        ComposerEntity composerEntity = modelMapper.map(composerDTO, ComposerEntity.class);
//        composerEntity.setFullName(nameGenerator.generateFullName(composerEntity.getFirstName(), composerEntity.getSecondName()));
//        composerEntity = composerRepository.save(composerEntity);
//
//        return modelMapper.map(composerEntity, ComposerDTO.class);
//    }
//
//    @Override
//    public ComposerDTO getOne(Long id) throws CustomException {
//        ComposerEntity composerEntity = findById(id);
//
//        return modelMapper.map(composerEntity, ComposerDTO.class);
//    }
//
//    @Override
//    public void deleteOne(Long id) throws CustomException {
//        ComposerEntity composerEntity = findById(id);
//
//        composerRepository.delete(composerEntity);
//    }
//
//    @Override
//    public void deleteAll() {
//        composerRepository.deleteAll();
//    }
//
//    @Override
//    public ComposerDTO updateOne(Long id, ComposerDTO DTO) throws CustomException {
//        ComposerEntity composerEntity = findById(id);
//        composerEntity.setFirstName(DTO.getFirstName());
//        composerEntity.setSecondName(DTO.getSecondName());
//        composerEntity.setFullName(nameGenerator.generateFullName(composerEntity.getFirstName(), composerEntity.getSecondName()));
//        composerEntity.setAge(DTO.getAge());
//        composerEntity = composerRepository.save(composerEntity);
//
//        return modelMapper.map(composerEntity, ComposerDTO.class);
//    }
//
//    @Override
//    public ComposerEntity findById(Long id) throws CustomException {
//        return composerRepository.findById(id)
//            .orElseThrow(() -> CustomException.builder()
//                .httpStatus(HttpStatus.NOT_FOUND)
//                .message(MessageSource.COMPOSER_NOT_FOUND.getText(String.valueOf(id)))
//                .build());
//    }
//
//}
