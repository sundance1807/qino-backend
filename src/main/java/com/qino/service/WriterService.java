package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.WriterDTO;
import com.qino.model.entity.cast.WriterEntity;
import com.qino.repository.WriterRepository;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WriterService {
    private WriterRepository writerRepository;
    private ModelMapper modelMapper;

    public WriterDTO saveOne(WriterDTO writerDTO) {
        WriterEntity writerEntity = modelMapper.map(writerDTO, WriterEntity.class);
        writerEntity.setFullName((writerDTO.getFirstName() + " " + writerDTO.getSecondName()).trim());
        writerEntity = writerRepository.save(writerEntity);

        return modelMapper.map(writerEntity, WriterDTO.class);
    }

    public WriterDTO findOne(Long id) throws CustomException {
        WriterEntity writerEntity = writerRepository.findById(id)
            .orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.WRITER_NOT_FOUND.getText(String.valueOf(id)))
                .build());

        return modelMapper.map(writerEntity, WriterDTO.class);
    }

    public Set<WriterDTO> findAll() {
        return writerRepository.findAll()
            .stream()
            .map(WriterDTO::new)
            .collect(Collectors.toSet());
    }

    public void deleteOne(Long id) throws CustomException {
        WriterEntity writerEntity = writerRepository.findById(id)
            .orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.WRITER_NOT_FOUND.getText(String.valueOf(id)))
                .build());
        writerRepository.deleteById(id);
    }

    public void deleteAll() {
        writerRepository.deleteAll();
    }

    public WriterDTO updateOne(Long id, WriterDTO writer) throws CustomException {
        WriterEntity writerEntity = writerRepository.findById(id)
            .orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.WRITER_NOT_FOUND.getText(String.valueOf(id)))
                .build());
        writerEntity.setFirstName(writer.getFirstName());
        writerEntity.setSecondName(writer.getSecondName());
        writerEntity.setFullName(writer.getFirstName() + " " + writer.getSecondName());
        writerEntity.setAge(writer.getAge());
        writerEntity = writerRepository.save(writerEntity);

        return modelMapper.map(writerEntity, WriterDTO.class);
    }
}
