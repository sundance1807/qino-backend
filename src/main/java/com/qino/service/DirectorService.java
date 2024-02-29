package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.inteface.CastServiceInterface;
import com.qino.model.dto.DirectorDTO;
import com.qino.model.entity.cast.DirectorEntity;
import com.qino.repository.DirectorRepository;
import com.qino.util.Generator;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DirectorService implements CastServiceInterface<DirectorDTO, DirectorEntity> {
    private final DirectorRepository directorRepository;
    private final ModelMapper modelMapper;
    private final Generator generator;

    @Override
    public DirectorDTO saveOne(DirectorDTO directorDTO) {
        DirectorEntity directorEntity = modelMapper.map(directorDTO, DirectorEntity.class);
        directorEntity.setFullName(generator.generateFullName(directorDTO.getFirstName().trim(), directorDTO.getSecondName().trim()));
        directorEntity = directorRepository.save(directorEntity);

        return modelMapper.map(directorEntity, DirectorDTO.class);
    }

    @Override
    public DirectorDTO findOne(Long id) throws CustomException {
        DirectorEntity directorEntity = findById(id);

        return modelMapper.map(directorEntity, DirectorDTO.class);
    }

    public Set<DirectorDTO> findAll() {
        return directorRepository.findAll()
            .stream()
            .map(DirectorDTO::new)
            .collect(Collectors.toSet());
    }

    @Override
    public DirectorDTO updateOne(Long id, DirectorDTO directorDTO) throws CustomException {
        DirectorEntity directorEntity = findById(id);
        directorEntity.setFirstName(directorDTO.getFirstName());
        directorEntity.setSecondName(directorDTO.getSecondName());
        directorEntity.setFullName(generator.generateFullName(directorDTO.getFirstName(), directorDTO.getSecondName()));
        directorEntity.setAge(directorDTO.getAge());

        return modelMapper.map(directorEntity, DirectorDTO.class);
    }

    @Override
    public void deleteOne(Long id) throws CustomException {
        DirectorEntity directorEntity = findById(id);

        directorRepository.delete(directorEntity);
    }

    @Override
    public DirectorEntity findById(Long id) throws CustomException {
        return directorRepository.findById(id)
            .orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.DIRECTOR_NOT_FOUND.getText(String.valueOf(id)))
                .build());
    }
}
