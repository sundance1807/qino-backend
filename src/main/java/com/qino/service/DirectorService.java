package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.DirectorDTO;
import com.qino.model.entity.cast.DirectorEntity;
import com.qino.repository.DirectorRepository;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DirectorService {
    private DirectorRepository directorRepository;
    private ModelMapper modelMapper;

    public DirectorDTO saveOne(DirectorDTO directorDTO) {
        DirectorEntity directorEntity = modelMapper.map(directorDTO, DirectorEntity.class);
        directorEntity.setFullName((directorDTO.getFirstName() + " " + directorDTO.getSecondName()).trim());
        directorEntity = directorRepository.save(directorEntity);

        return modelMapper.map(directorEntity, DirectorDTO.class);
    }

    public DirectorDTO findOne(Long id) throws CustomException {
        DirectorEntity directorEntity = directorRepository.findById(id)
            .orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.DIRECTOR_NOT_FOUND.getText(String.valueOf(id)))
                .build());

        return modelMapper.map(directorEntity, DirectorDTO.class);
    }

    public Set<DirectorDTO> findAll() {
        return directorRepository.findAll()
            .stream()
            .map(DirectorDTO::new)
            .collect(Collectors.toSet());
    }

    public void deleteOne(Long id) throws CustomException {
        DirectorEntity directorEntity = directorRepository.findById(id)
            .orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.DIRECTOR_NOT_FOUND.getText(String.valueOf(id)))
                .build());
        directorRepository.deleteById(id);
    }

    public void deleteAll() {
        directorRepository.deleteAll();
    }

    public DirectorDTO updateOne(Long id, DirectorDTO directorDTO) throws CustomException {
        DirectorEntity directorEntity = directorRepository.findById(id)
            .orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.DIRECTOR_NOT_FOUND.getText(String.valueOf(id)))
                .build());
        directorEntity.setFirstName(directorDTO.getFirstName());
        directorEntity.setSecondName(directorDTO.getSecondName());
        directorEntity.setFullName(directorDTO.getFirstName() + " " + directorDTO.getSecondName());
        directorEntity.setAge(directorDTO.getAge());
        directorEntity = directorRepository.save(directorEntity);

        return modelMapper.map(directorEntity, DirectorDTO.class);
    }
}
