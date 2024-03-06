package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.CareerDTO;
import com.qino.model.entity.CareerEntity;
import com.qino.repository.CareerRepository;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CareerService {
    private final CareerRepository careerRepository;
    private final ModelMapper modelMapper;

    /**
     * @param careerDTO career to save
     * @return saved career
     * @throws CustomException if career name is already exists
     */
    public CareerDTO saveOne(CareerDTO careerDTO) throws CustomException {
        careerDTO.setName(careerDTO.getName().toUpperCase().trim());
        careerDTO.setName(careerDTO.getRuTranslation().toUpperCase().trim());
        validateCareerName(careerDTO.getName());
        CareerEntity careerEntity = modelMapper.map(careerDTO, CareerEntity.class);
        careerEntity = careerRepository.save(careerEntity);

        return modelMapper.map(careerEntity, CareerDTO.class);
    }

    /**
     * @param id career id
     * @return existing career
     * @throws CustomException if career not found
     */
    public CareerDTO findOne(Long id) throws CustomException {
        CareerEntity careerEntity = findById(id);

        return modelMapper.map(careerEntity, CareerDTO.class);
    }

    /**
     * @param id career id
     * @throws CustomException if career not found
     */
    public void deleteOne(Long id) throws CustomException {
        CareerEntity careerEntity = findById(id);

        careerRepository.delete(careerEntity);
    }

    /**
     * @param id career id
     * @return existing career
     * @throws CustomException if career not found
     */
    private CareerEntity findById(Long id) throws CustomException {
        return careerRepository.findById(id).orElseThrow(
            () -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.CAREER_NOT_FOUND.getText(id.toString()))
                .build());
    }


    /**
     * @param name career name
     * @throws CustomException if career already exists
     */
    private void validateCareerName(String name) throws CustomException {
        Optional<CareerEntity> careerEntity = careerRepository.findByName(name);

        if (careerEntity.isPresent()) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.CAREER_ALREADY_EXIST.getText(name))
                .build();
        }
    }
}
