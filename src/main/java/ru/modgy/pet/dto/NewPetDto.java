package ru.modgy.pet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.modgy.pet.model.Sex;
import ru.modgy.pet.model.TypeOfPet;

import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPetDto {
//todo    @NotNull(message = "Field: owner. Error: must not be blank.")
//todo    private long ownerId;
    @NotNull(message = "Не выбран вид питомца.")
    @NotNull(message = "Field: owner. Error: must not be blank.")
    private long ownerId;
    @NotNull(message = "Field: typeOfPet. Error: must not be blank.")
    private TypeOfPet type; // вид животного
    @Length(min = 1, max = 30, message = "Имя питомца должно быть не меньше {min} и не больше {max} символов.")
    @NotBlank(message = "Имя питомца не должно быть пустым.")
    private String name;
    @Length(min = 1, max = 30, message = "Порода питомца должна быть не меньше {min} и не больше {max} символов.")
    @NotBlank(message = "Порода питомца не должна быть пустой.")
    private String breed; // порода
    @Past(message = "Питомец ещё не родился?")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotNull(message = "Обязательно указывать дату рождения питомца.")
    private LocalDate birthDate;
    @NotNull(message = "Не указан пол питомца.")
    private Sex sex;
    @Length(max = 30, message = "Цвет питомца нужно уместить в {max} символов.")
    private String color;
    @Length(max = 150, message = "Кличка питомца длиннее {max} символов недопустимо.")
    private String sign;
    private Boolean isExhibition;
    @PastOrPresent(message = "Нужна указать уже свершившийся визит к ветеринару.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate vetVisitDate;
    @Length(max = 250, message = "Повод визита к ветеринару должен быть не больше {max} символов.")
    private String vetVisitReason;
    @Length(max = 250, message = "Запись о вакцинации должна быть не больше {max} символов.")
    private String vaccine;
    @Length(max = 250, message = "Запись о паразитах не больше {max} символов.")
    private String parasites;
    @Length(max = 250, message = "Запись о клещах не больше {max} символов.")
    private String fleaMite;
    @Length(max = 250, message = "Запись об операциях не больше {max} символов.")
    private String surgery;
    @Length(max = 500, message = "Запись о перенесённых болезнях не больше {max} символов.")
    private String pastDisease;
    @Length(max = 500, message = "Запись о здоровье не больше {max} символов.")
    private String healthCharacteristic;
    @Length(max = 250, message = "Запись о анализах мочи не больше {max} символов.")
    private String urineAnalysis;
    private Boolean isAllergy;
    @Length(max = 250, message = "Запись об аллергии не больше {max} символов.")
    private String allergyType;
    private Boolean isChronicDisease;
    @Length(max = 500, message = "Запись о хронических болезнях не больше {max} символов.")
    private String chronicDiseaseType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate heatDate;
    @Length(max = 500, message = "Запись о посещении ветеринана не больше {max} символов.")
    private String vetData;
    @Length(max = 500, message = "Запись об опыте разлуки с хозяином не больше {max} символов.")
    private String stayWithoutMaster;
    @Length(max = 250, message = "Запись об умении оставаться одному не больше {max} символов.")
    private String stayAlone;
    @Length(max = 500, message = "Запись о спец. уходе не больше {max} символов.")
    private String specialCare;
    @Length(max = 250, message = "Запись о лае не больше {max} символов.")
    private String barkHowl;
    @Length(max = 250, message = "Запись о порче вещей не больше {max} символов.")
    private String furnitureDamage;
    @Length(max = 250, message = "Запись о воровстве еды не больше {max} символов.")
    private String foodFromTable;
    @Length(max = 250, message = "Запись о справлении нужды не больше {max} символов.")
    private String defecateAtHome;
    @Length(max = 250, message = "Запись о метках не больше {max} символов.")
    private String markAtHome;
    @Length(max = 500, message = "Запись об отношении к незнакомцам не больше {max} символов.")
    private String newPeople;
    private Boolean isBitePeople;
    @Length(max = 250, message = "Запись о причинах укусов не больше {max} символов.")
    private String reasonOfBite;
    @Length(max = 500, message = "Запись об играх с собаками не больше {max} символов.")
    private String playWithDogs;
    private Boolean isDogTraining;
    @Length(max = 500, message = "Запись о названии курсов не больше {max} символов.")
    private String trainingName;
    @Length(max = 500, message = "Запись о любимом не больше {max} символов.")
    private String like;
    @Length(max = 500, message = "Запись о нелюбимом не больше {max} символов.")
    private String notLike;
    @Length(max = 500, message = "Запись об игрушках не больше {max} символов.")
    private String toys;
    @Length(max = 250, message = "Запись о вредных привычках не больше {max} символов.")
    private String badHabit;
    @Length(max = 250, message = "Запись о прогулках не больше {max} символов.")
    private String walking;
    @Length(max = 150, message = "Запись о времени утренних прогулок не больше {max} символов.")
    private String morningWalking;
    @Length(max = 150, message = "Запись о времени дневных прогулок не больше {max} символов.")
    private String dayWalking;
    @Length(max = 150, message = "Запись о времени вечерних прогулок не больше {max} символов.")
    private String eveningWalking;
    @Min(1)
    @Max(9)
    private Integer feedingQuantity;
    @Length(max = 250, message = "Запись о виде корма не больше {max} символов.")
    private String feedType;
    @Length(max = 250, message = "Запись о названии корма не больше {max} символов.")
    private String feedName;
    @Length(max = 250, message = "Запись о составе корма не больше {max} символов.")
    private String feedComposition;
    @Length(max = 250, message = "Запись о норме кормления не больше {max} символов.")
    private String feedingRate;
    @Length(max = 500, message = "Запись об особенностях кормления не больше {max} символов.")
    private String feedingPractice;
    @Length(max = 250, message = "Запись о разрешённых лакомствах не больше {max} символов.")
    private String treat;
    private Boolean isMedicine;
    @Length(max = 500, message = "Запись о режиме приёма лекарств не больше {max} символов.")
    private String medicineRegimen;
    @Length(max = 1000, message = "Запись о дополнительных особенностях не больше {max} символов.")
    private String additionalData;
}