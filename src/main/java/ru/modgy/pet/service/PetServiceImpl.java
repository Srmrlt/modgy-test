package ru.modgy.pet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.modgy.exception.ConflictException;
import ru.modgy.exception.NotFoundException;
import ru.modgy.owner.dto.OwnerShortDto;
import ru.modgy.owner.dto.mapper.OwnerMapper;
import ru.modgy.owner.model.Owner;
import ru.modgy.pet.dto.NewPetDto;
import ru.modgy.pet.dto.PetDto;
import ru.modgy.pet.dto.PetFilterParams;
import ru.modgy.pet.dto.UpdatePetDto;
import ru.modgy.pet.mapper.PetMapper;
import ru.modgy.pet.model.Pet;
import ru.modgy.pet.repository.PetRepository;
import ru.modgy.utility.EntityService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.modgy.pet.dto.PetDto.getComparator;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final OwnerMapper ownerMapper;
    private final EntityService entityService;

    @Override
    @Transactional
    public PetDto addPet(Long requesterId, NewPetDto newPetDto) {
        entityService.getUserIfExists(requesterId);
        Owner owner = entityService.getOwnerIfExists(newPetDto.getOwnerId());
        checkPet(newPetDto);
        Pet newPet = petMapper.toPet(newPetDto);
        newPet.setOwner(owner);
        Pet savedPet = petRepository.save(newPet);
        log.info("PetService: addPet, requesterId={}, petId={}", requesterId, savedPet.getId());
        OwnerShortDto ownerShortDto = ownerMapper.toOwnerShortDto(owner);
        PetDto petDto = petMapper.toPetDto(savedPet);
        petDto.setOwnerShortDto(ownerShortDto);
        return petDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PetDto getPetById(Long requesterId, Long petId) {
        entityService.getUserIfExists(requesterId);
        Pet pet = entityService.getPetIfExists(petId);
        PetDto petDto = petMapper.toPetDto(pet);
        petDto.setOwnerShortDto(ownerMapper.toOwnerShortDto(pet.getOwner()));
        log.info("PetService: getPetById, requesterId={}, petId={}", requesterId, petId);
        return petDto;
    }

    @Override
    @Transactional
    public PetDto updatePet(Long requesterId, Long petId, UpdatePetDto updatePetDto) {
        Pet oldPet = entityService.getPetIfExists(petId);
        Pet newPet = petMapper.toPet(updatePetDto);
        newPet.setId(oldPet.getId());
        newPet.setOwner(oldPet.getOwner());
        checkPet(oldPet, updatePetDto);
        if (Objects.isNull(updatePetDto.getType())) {
            newPet.setType(oldPet.getType());
        }
        if (Objects.isNull(updatePetDto.getName()) || updatePetDto.getName().isBlank()) {
            newPet.setName(oldPet.getName());
        }
        if (Objects.isNull(updatePetDto.getBreed()) || updatePetDto.getBreed().isBlank()) {
            newPet.setBreed(oldPet.getBreed());
        }
        if (Objects.isNull(updatePetDto.getBirthDate())) {
            newPet.setBirthDate(oldPet.getBirthDate());
        }
        if (Objects.isNull(updatePetDto.getSex())) {
            newPet.setSex(oldPet.getSex());
        }
        if (Objects.isNull(updatePetDto.getColor()) || updatePetDto.getColor().isBlank()) {
            newPet.setColor(oldPet.getColor());
        }
        if (Objects.isNull(updatePetDto.getSign()) || updatePetDto.getSign().isBlank()) {
            newPet.setSign(oldPet.getSign());
        }
        if (Objects.isNull(updatePetDto.getIsExhibition())) {
            newPet.setIsExhibition(oldPet.getIsExhibition());
        }
        if (Objects.isNull(updatePetDto.getVetVisitDate())) {
            newPet.setVetVisitDate(oldPet.getVetVisitDate());
        }
        if (Objects.isNull(updatePetDto.getVetVisitReason()) || updatePetDto.getVetVisitReason().isBlank()) {
            newPet.setVetVisitReason(oldPet.getVetVisitReason());
        }
        if (Objects.isNull(updatePetDto.getVaccine()) || updatePetDto.getVaccine().isBlank()) {
            newPet.setVaccine(oldPet.getVaccine());
        }
        if (Objects.isNull(updatePetDto.getParasites()) || updatePetDto.getParasites().isBlank()) {
            newPet.setParasites(oldPet.getParasites());
        }
        if (Objects.isNull(updatePetDto.getFleaMite()) || updatePetDto.getFleaMite().isBlank()) {
            newPet.setFleaMite(oldPet.getFleaMite());
        }
        if (Objects.isNull(updatePetDto.getSurgery()) || updatePetDto.getSurgery().isBlank()) {
            newPet.setSurgery(oldPet.getSurgery());
        }
        if (Objects.isNull(updatePetDto.getPastDisease()) || updatePetDto.getPastDisease().isBlank()) {
            newPet.setPastDisease(oldPet.getPastDisease());
        }
        if (Objects.isNull(updatePetDto.getHealthCharacteristic()) || updatePetDto.getHealthCharacteristic().isBlank()) {
            newPet.setHealthCharacteristic(oldPet.getHealthCharacteristic());
        }
        if (Objects.isNull(updatePetDto.getUrineAnalysis()) || updatePetDto.getUrineAnalysis().isBlank()) {
            newPet.setUrineAnalysis(oldPet.getUrineAnalysis());
        }
        if (Objects.isNull(updatePetDto.getIsAllergy())) {
            newPet.setIsAllergy(oldPet.getIsAllergy());
        }
        if (Objects.isNull(updatePetDto.getAllergyType()) || updatePetDto.getAllergyType().isBlank()) {
            newPet.setAllergyType(oldPet.getAllergyType());
        }
        if (Objects.isNull(updatePetDto.getIsChronicDisease())) {
            newPet.setIsChronicDisease(oldPet.getIsChronicDisease());
        }
        if (Objects.isNull(updatePetDto.getChronicDiseaseType()) || updatePetDto.getChronicDiseaseType().isBlank()) {
            newPet.setChronicDiseaseType(oldPet.getChronicDiseaseType());
        }
        if (Objects.isNull(updatePetDto.getHeatDate())) {
            newPet.setHeatDate(oldPet.getHeatDate());
        }
        if (Objects.isNull(updatePetDto.getVetData()) || updatePetDto.getVetData().isBlank()) {
            newPet.setVetData(oldPet.getVetData());
        }
        if (Objects.isNull(updatePetDto.getStayWithoutMaster()) || updatePetDto.getStayWithoutMaster().isBlank()) {
            newPet.setStayWithoutMaster(oldPet.getStayWithoutMaster());
        }
        if (Objects.isNull(updatePetDto.getStayAlone()) || updatePetDto.getStayAlone().isBlank()) {
            newPet.setStayAlone(oldPet.getStayAlone());
        }
        if (Objects.isNull(updatePetDto.getSpecialCare()) || updatePetDto.getSpecialCare().isBlank()) {
            newPet.setSpecialCare(oldPet.getSpecialCare());
        }
        if (Objects.isNull(updatePetDto.getBarkHowl()) || updatePetDto.getBarkHowl().isBlank()) {
            newPet.setBarkHowl(oldPet.getBarkHowl());
        }
        if (Objects.isNull(updatePetDto.getFurnitureDamage()) || updatePetDto.getFurnitureDamage().isBlank()) {
            newPet.setFurnitureDamage(oldPet.getFurnitureDamage());
        }
        if (Objects.isNull(updatePetDto.getFoodFromTable()) || updatePetDto.getFoodFromTable().isBlank()) {
            newPet.setFoodFromTable(oldPet.getFoodFromTable());
        }
        if (Objects.isNull(updatePetDto.getDefecateAtHome()) || updatePetDto.getDefecateAtHome().isBlank()) {
            newPet.setDefecateAtHome(oldPet.getDefecateAtHome());
        }
        if (Objects.isNull(updatePetDto.getAllergyType()) || updatePetDto.getAllergyType().isBlank()) {
            newPet.setAllergyType(oldPet.getAllergyType());
        }
        if (Objects.isNull(updatePetDto.getMarkAtHome()) || updatePetDto.getMarkAtHome().isBlank()) {
            newPet.setMarkAtHome(oldPet.getMarkAtHome());
        }
        if (Objects.isNull(updatePetDto.getNewPeople()) || updatePetDto.getNewPeople().isBlank()) {
            newPet.setNewPeople(oldPet.getNewPeople());
        }
        if (Objects.isNull(updatePetDto.getIsBitePeople())) {
            newPet.setIsBitePeople(oldPet.getIsBitePeople());
        }
        if (Objects.isNull(updatePetDto.getReasonOfBite()) || updatePetDto.getReasonOfBite().isBlank()) {
            newPet.setReasonOfBite(oldPet.getReasonOfBite());
        }
        if (Objects.isNull(updatePetDto.getPlayWithDogs()) || updatePetDto.getPlayWithDogs().isBlank()) {
            newPet.setPlayWithDogs(oldPet.getPlayWithDogs());
        }
        if (Objects.isNull(updatePetDto.getIsDogTraining())) {
            newPet.setIsDogTraining(oldPet.getIsDogTraining());
        }
        if (Objects.isNull(updatePetDto.getTrainingName()) || updatePetDto.getTrainingName().isBlank()) {
            newPet.setTrainingName(oldPet.getTrainingName());
        }
        if (Objects.isNull(updatePetDto.getLike()) || updatePetDto.getLike().isBlank()) {
            newPet.setLike(oldPet.getLike());
        }
        if (Objects.isNull(updatePetDto.getNotLike()) || updatePetDto.getNotLike().isBlank()) {
            newPet.setNotLike(oldPet.getNotLike());
        }
        if (Objects.isNull(updatePetDto.getToys()) || updatePetDto.getToys().isBlank()) {
            newPet.setToys(oldPet.getToys());
        }
        if (Objects.isNull(updatePetDto.getBadHabit()) || updatePetDto.getBadHabit().isBlank()) {
            newPet.setBadHabit(oldPet.getBadHabit());
        }
        if (Objects.isNull(updatePetDto.getWalking()) || updatePetDto.getWalking().isBlank()) {
            newPet.setWalking(oldPet.getWalking());
        }
        if (Objects.isNull(updatePetDto.getMorningWalking()) || updatePetDto.getMorningWalking().isBlank()) {
            newPet.setMorningWalking(oldPet.getMorningWalking());
        }
        if (Objects.isNull(updatePetDto.getDayWalking()) || updatePetDto.getDayWalking().isBlank()) {
            newPet.setDayWalking(oldPet.getDayWalking());
        }
        if (Objects.isNull(updatePetDto.getEveningWalking()) || updatePetDto.getEveningWalking().isBlank()) {
            newPet.setEveningWalking(oldPet.getEveningWalking());
        }
        if (Objects.isNull(updatePetDto.getFeedingQuantity())) {
            newPet.setFeedingQuantity(oldPet.getFeedingQuantity());
        }
        if (Objects.isNull(updatePetDto.getFeedType()) || updatePetDto.getFeedType().isBlank()) {
            newPet.setFeedType(oldPet.getFeedType());
        }
        if (Objects.isNull(updatePetDto.getFeedName()) || updatePetDto.getFeedName().isBlank()) {
            newPet.setFeedName(oldPet.getFeedName());
        }
        if (Objects.isNull(updatePetDto.getFeedComposition()) || updatePetDto.getFeedComposition().isBlank()) {
            newPet.setFeedComposition(oldPet.getFeedComposition());
        }
        if (Objects.isNull(updatePetDto.getFeedingRate()) || updatePetDto.getFeedingRate().isBlank()) {
            newPet.setFeedingRate(oldPet.getFeedingRate());
        }
        if (Objects.isNull(updatePetDto.getFeedingPractice()) || updatePetDto.getFeedingPractice().isBlank()) {
            newPet.setFeedingPractice(oldPet.getFeedingPractice());
        }
        if (Objects.isNull(updatePetDto.getTreat()) || updatePetDto.getTreat().isBlank()) {
            newPet.setTreat(oldPet.getTreat());
        }
        if (Objects.isNull(updatePetDto.getIsMedicine())) {
            newPet.setIsMedicine(oldPet.getIsMedicine());
        }
        if (Objects.isNull(updatePetDto.getMedicineRegimen()) || updatePetDto.getMedicineRegimen().isBlank()) {
            newPet.setMedicineRegimen(oldPet.getMedicineRegimen());
        }
        if (Objects.isNull(updatePetDto.getAdditionalData()) || updatePetDto.getAdditionalData().isBlank()) {
            newPet.setAdditionalData(oldPet.getAdditionalData());
        }
        newPet.setRegistrationDate(oldPet.getRegistrationDate());
        Pet savedPet = petRepository.save(newPet);
        log.info("PetService: updatePet, requesterId={}, petId={}, updatePetDto={}", requesterId, petId, updatePetDto);
        PetDto petDto = petMapper.toPetDto(savedPet);
        petDto.setOwnerShortDto(ownerMapper.toOwnerShortDto(oldPet.getOwner()));
        return petDto;

    }

    @Override
    @Transactional
    public void deletePetById(Long requesterId, Long petId) {
        int result = petRepository.deletePetById(petId);

        if (result == 0) {
            throw new NotFoundException(String.format("pet with id=%d not found", petId));
        }
        log.info("PetService: deletePetById, requesterId={}, petId={}", requesterId, petId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PetDto> getPetsBySearch(Long requesterId, String text, Integer page, Integer size) {
        entityService.getUserIfExists(requesterId);

        Pageable pageable = PageRequest.of(page, size);

        if (text == null) {
            Pageable pageableIfTextNull =
                    PageRequest.of(0, size, Sort.by(Sort.Order.desc("registrationDate")));

            Page<Pet> pets = petRepository.findAll(pageableIfTextNull);
            Map<Long, Owner> owners = pets.stream()
                    .collect(Collectors.toMap(Pet::getId, Pet::getOwner));

            List<PetDto> petsDto = petMapper.toListPetDto(pets.getContent());
            for (PetDto petDto : petsDto) {
                petDto.setOwnerShortDto(ownerMapper.toOwnerShortDto(owners.get(petDto.getId())));
            }

            List<PetDto> answer = petsDto
                    .stream()
                    .sorted(getComparator())
                    .toList();

            log.info("PetService: getPetsBySearch, requesterId={}, page={}, size={}", requesterId, page, size);
            return new PageImpl<>(answer, pageableIfTextNull, answer.size());
        } else {
            PetFilterParams params = PetFilterParams.builder()
                    .name(text)
                    .build();

            List<Pet> pets = petRepository.findAllPetsByParams(params);
            Map<Long, Owner> owners = pets.stream()
                    .collect(Collectors.toMap(Pet::getId, Pet::getOwner));

            List<PetDto> petsDto = petMapper.toListPetDto(pets);
            for (PetDto petDto : petsDto) {
                petDto.setOwnerShortDto(ownerMapper.toOwnerShortDto(owners.get(petDto.getId())));
            }

            List<PetDto> answer = petsDto
                    .stream()
                    .sorted(getComparator())
                    .toList();

            PagedListHolder<PetDto> pagedListHolder = new PagedListHolder<>(answer);
            pagedListHolder.setPageSize(size);
            pagedListHolder.setPage(page);

            log.info("PetService: getPetsBySearch, requesterId={}, text={}, page={}, size={}", requesterId, text, page, size);
            return new PageImpl<>(pagedListHolder.getPageList(), pageable, pets.size());
        }

    }

    private void checkPet(NewPetDto newPetDto) {
        try {
            Pet pet = petRepository.findByOwnerIdAndName(newPetDto.getOwnerId(), newPetDto.getName());
            if (pet != null) {
                throw new ConflictException(String.format("The client with id = %d already has a pet with name = %s.", newPetDto.getOwnerId(), newPetDto.getName()));
            }

        } catch (NullPointerException exception) {
            return;
        }
    }

    private void checkPet(Pet oldPet, UpdatePetDto updatePetDto) {
        try {
            Pet pet = petRepository.findByOwnerIdAndName(oldPet.getOwner().getId(), updatePetDto.getName());
            if (pet != null) {
                throw new ConflictException(String.format("The client with id = %d already has a pet with name = %s.", oldPet.getOwner().getId(), updatePetDto.getName()));
            }

        } catch (NullPointerException exception) {
            return;
        }
    }
}
