package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.Currency;
import com.qino.model.Occupation;
import com.qino.model.dto.CreateFilmDTO;
import com.qino.model.dto.FilmPreviewDTO;
import com.qino.model.dto.FilmResponseDTO;
import com.qino.model.dto.ProductionMemberDTO;
import com.qino.model.entity.FilmEntity;
import com.qino.model.entity.GenreEntity;
import com.qino.model.entity.PersonEntity;
import com.qino.repository.FilmRepository;
import com.qino.repository.GenreRepository;
import com.qino.repository.PersonRepository;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilmService {
	private FilmRepository filmRepository;
	private ProductionMemberService productionMemberService;
	private PersonRepository personRepository;
	private GenreRepository genreRepository;
	private ModelMapper modelMapper;
	
	/**
	 * @param createFilmDTO film to save
	 * @return saved film
	 * @throws CustomException if releaseYear out of bound
	 */
	@Transactional(rollbackFor = {RuntimeException.class, Error.class, CustomException.class})
	public FilmResponseDTO saveOne(CreateFilmDTO createFilmDTO) throws CustomException {
		FilmEntity filmEntity = setFilmDetails(createFilmDTO);
		filmEntity.setProductionMembers(productionMemberService.saveAll(filmEntity.getProductionMembers()));
		filmEntity.setActors(validatePersons(filmEntity.getActors()));
		filmEntity = filmRepository.save(filmEntity);
		
		FilmResponseDTO result = modelMapper.map(filmEntity, FilmResponseDTO.class);
		result.setProductionMembers(getProductionTeam(filmEntity));
		
		return result;
	}
	
	/**
	 * @param id film id
	 * @return existing film
	 * @throws CustomException if film not found
	 */
	public FilmResponseDTO getOne(Long id) throws CustomException {
		FilmEntity filmEntity = findById(id);
		filmEntity.setRating(filmRepository.getRating(id));
		filmEntity.setVotes(filmRepository.getVotes(id));
//        filmEntity.setProductionMembers();
		FilmResponseDTO dto = modelMapper.map(filmEntity, FilmResponseDTO.class);
		dto.setDuration(formatDuration(dto.getDuration()));
		dto.setBudget(formatAmount(dto.getBudget()));
		dto.setGrosses(formatAmount(dto.getGrosses()));
		
		return dto;
	}
	
	/**
	 * @return set of film previews
	 */
	public Set<FilmPreviewDTO> getAllFilmPreviews() {
		Comparator<FilmPreviewDTO> filmComparator = Comparator.comparing(FilmPreviewDTO::getId);
		Set<FilmPreviewDTO> sortedFilmPreviews = new TreeSet<>(filmComparator);
		sortedFilmPreviews.addAll(filmRepository.findAll()
			.stream()
			.map(FilmPreviewDTO::new)
			.collect(Collectors.toSet()));
		
		return sortedFilmPreviews;
	}
	
	
	/**
	 * @param id              film id
	 * @param filmResponseDTO film data to update
	 * @return updated film
	 * @throws CustomException if film not found
	 */
	public FilmResponseDTO updateOne(Long id, FilmResponseDTO filmResponseDTO) throws CustomException {
		FilmEntity filmEntity = modelMapper.map(filmResponseDTO, FilmEntity.class);
		filmEntity.setId(id);
		filmEntity.setTitle(filmEntity.getTitle().trim());
		filmEntity.setReleaseYear(validateReleaseYear(filmEntity.getReleaseYear()));
		filmEntity.setGenres(validateGenres(filmEntity.getGenres()));
		
		filmEntity.setActors(validatePersons(filmEntity.getActors()));
		filmEntity = filmRepository.save(filmEntity);
		
		return modelMapper.map(filmEntity, FilmResponseDTO.class);
	}
	
	/**
	 * @param id film id
	 * @throws CustomException if film not found
	 */
	public void deleteOne(Long id) throws CustomException {
		FilmEntity filmEntity = findById(id);
		filmRepository.delete(filmEntity);
	}
	
	/**
	 * @param id film id
	 * @return existing film
	 * @throws CustomException if film not found
	 */
	private FilmEntity findById(Long id) throws CustomException {
		return filmRepository.findById(id).orElseThrow(() -> CustomException.builder()
			.httpStatus(HttpStatus.NOT_FOUND)
			.message(MessageSource.FILM_NOT_FOUND.getText(id.toString()))
			.build());
	}
	
	
	/**
	 * @param year release year
	 * @return year
	 * @throws CustomException if year out of bound
	 */
	private Year validateReleaseYear(Year year) throws CustomException {
		if (year.isAfter(Year.now())) {
			throw CustomException.builder()
				.httpStatus(HttpStatus.BAD_REQUEST)
				.message(MessageSource.RELEASE_YEAR_OUT_OF_BOUND.getText(year.toString()))
				.build();
		}
		
		return year;
	}
	
	/**
	 * @param genreEntitySet set of genres id
	 * @return set of existing genres
	 * @throws CustomException if genre not found
	 */
	private Set<GenreEntity> validateGenres(Set<GenreEntity> genreEntitySet) throws CustomException {
		Set<GenreEntity> genres = new HashSet<>();
		for (GenreEntity genreEntity : genreEntitySet) {
			Long id = genreEntity.getId();
			genreEntity = genreRepository.findById(id).orElseThrow(() -> CustomException.builder()
				.httpStatus(HttpStatus.NOT_FOUND)
				.message(MessageSource.GENRE_NOT_FOUND.getText(id.toString()))
				.build());
			genres.add(genreEntity);
		}
		
		return genres;
	}
	
	/**
	 * @param personEntitySet of persons id
	 * @return set of existing persons
	 * @throws CustomException if persons not found
	 */
	private Set<PersonEntity> validatePersons(Set<PersonEntity> personEntitySet) throws CustomException {
		Set<PersonEntity> persons = new HashSet<>();
		for (PersonEntity personEntity : personEntitySet) {
			Long id = personEntity.getId();
			personEntity = personRepository.findById(id).orElseThrow(() -> CustomException.builder()
				.httpStatus(HttpStatus.NOT_FOUND)
				.message(MessageSource.PERSON_NOT_FOUND.getText(id.toString()))
				.build());
			persons.add(personEntity);
		}
		
		return persons;
	}
	
	/**
	 * @param amount amount
	 * @return formatted amount
	 */
	private String formatAmount(String amount) {
		if (amount == null) {
			return Currency.USD.getSymbol() + 0;
		}
		StringBuilder result = new StringBuilder();
		int numLength = amount.length();
		int count = 0;
		
		if (numLength < 3) {
			return amount;
		}
		
		for (int i = numLength - 1; i >= 0; i--) {
			result.insert(0, amount.charAt(i));
			count++;
			if (count % 3 == 0 && i != 0) {
				result.insert(0, " ");
			}
		}
		
		return Currency.USD.getSymbol() + result;
	}
	
	
	/**
	 * @param duration duration
	 * @return formatted duration
	 */
	private String formatDuration(String duration) {
		int numDuration = Integer.parseInt(duration);
		String hours = "0" + numDuration / 60;
		String minutes = String.valueOf(numDuration % 60);
		return duration + " мин." + "/ " + hours + ":" + minutes;
	}
	
	
	private Map<String, Set<ProductionMemberDTO>> getProductionTeam(FilmEntity filmEntity) {
		Map<String, Set<ProductionMemberDTO>> result = new HashMap<>();
		
		for (Occupation occupation : Occupation.values()) {
			Set<ProductionMemberDTO> productionMembers = filmEntity.getProductionMembers().stream()
				.filter(member -> member.getOccupation() == occupation)
				.map(ProductionMemberDTO::new)
				.collect(Collectors.toSet());
			
			String pluralName = occupation.getPluralName();
			result.put(pluralName, productionMembers);
		}
		
		return result;
	}
	
	private FilmEntity setFilmDetails(CreateFilmDTO createFilmDTO) throws CustomException {
		FilmEntity filmEntity = modelMapper.map(createFilmDTO, FilmEntity.class);
		filmEntity.setTitle(filmEntity.getTitle().trim());
		filmEntity.setDuration(Integer.parseInt(createFilmDTO.getDuration()));
		filmEntity.setReleaseYear(validateReleaseYear(filmEntity.getReleaseYear()));
		filmEntity.setGenres(validateGenres(filmEntity.getGenres()));
		
		return filmEntity;
	}
}