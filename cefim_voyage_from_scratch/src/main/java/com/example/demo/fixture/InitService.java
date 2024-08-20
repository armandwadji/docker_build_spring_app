package com.example.demo.fixture;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.utils.DateConvert;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

@Slf4j
@Service
@AllArgsConstructor
public class InitService {

    private static final Faker FAKER = new Faker(new Locale("fr"));
    private final TravelRepository travelRepository;
    private final AccommodationRepository accommodationRepository;
    private final ActivityRepository activityRepository;
    private final ExtraRepository extraRepository;
    private final ContactRepository contactRepository;

    public void initTravel() {
        final List<Travel> travels = IntStream.range(2, 26).mapToObj(index ->
                Travel.builder()
                        .title(FAKER.name().title())
                        .description(FAKER.lorem().sentence(10))
                        .location(FAKER.address().fullAddress())
                        .start(LocalDate.now())
                        .end(LocalDate.now().plusDays((long) (Math.random() * 10)))
                        .build()
        ).toList();
        travelRepository.saveAll(travels);
        log.info("TRAVEL_SAVE {}",travels.size());
    }

    public void initAccommodation() {
        List<Accommodation> accommodations = new ArrayList<>();
        this.travelRepository.findAll().forEach(travelItem ->
                {
                    Date start = DateConvert.convertLocalDateToDate(travelItem.getStart());
                    Date end = DateConvert.convertLocalDateToDate(travelItem.getEnd());

                    for (int i = 0; i < (int) (Math.random() * 3 + 1) ; i++) {


                        Accommodation accommodation = new Accommodation();
                        accommodation.setName(FAKER.name().title());
                        accommodation.setAddress(FAKER.address().fullAddress());
                        accommodation.setPhone(FAKER.phoneNumber().cellPhone());
                        accommodation.setPrice((50 + (int) (Math.random() * 2000)) * 100);
                        accommodation.setStart(DateConvert.convertDateToLocalDateTime(FAKER.date().between( start , end)) );
                        accommodation.setEnd(DateConvert.convertDateToLocalDateTime(FAKER.date().between(DateConvert.convertLocalDateTimeToDate(accommodation.getStart()), end)));
                        accommodation.setTravel(travelItem);
                        accommodations.add(accommodation);
                    }
                }
        );
        accommodationRepository.saveAll(accommodations);
        log.info("ACCOMMODATIONS_SAVE {}",accommodations.size());
    }

    public void initActivity(){
        List<Activity> activities = new ArrayList<>();
        this.travelRepository.findAll().forEach(travelItem ->
                {
                    Date start = DateConvert.convertLocalDateToDate(travelItem.getStart());
                    Date end = DateConvert.convertLocalDateToDate(travelItem.getEnd());

                    for (int i = 0; i < (int) (Math.random() * 100); i++) {
                        Activity activity = new Activity();
                        activity.setTitle(FAKER.name().title());
                        activity.setDescription(FAKER.lorem().sentence(2));
                        activity.setPrice((50 + (int) (Math.random() * 200)) * 100);
                        activity.setStart(DateConvert.convertDateToLocalDateTime(FAKER.date().between( start , end)) );
                        activity.setEnd(DateConvert.convertDateToLocalDateTime(FAKER.date().between(DateConvert.convertLocalDateTimeToDate(activity.getStart()), end)));
                        activity.setTravel(travelItem);
                        activities.add(activity);
                    }
                }
        );

        activityRepository.saveAll(activities);
        log.info("ACTIVITIES_SAVE {}",activities.size());
    }

    public void initExtra() {
        List<Extra> extras = new ArrayList<>();

        this.travelRepository.findAll().forEach(travelItem ->
                {
                    Date start = DateConvert.convertLocalDateToDate(travelItem.getStart());
                    Date end = DateConvert.convertLocalDateToDate(travelItem.getEnd());

                    for (int i = 0; i < (int) (Math.random() * 100); i++) {
                        final Extra extra = new Extra();
                        extra.setName(FAKER.name().title());
                        extra.setPrice((50 + (int) (Math.random() * 200)) * 100);
                        extra.setCreatedat(DateConvert.convertDateToLocalDateTime(FAKER.date().between( start , end)) );
                        extra.setTravel(travelItem);
                        extras.add(extra);
                    }
                }
        );

        extraRepository.saveAll(extras);
        log.info("EXTRAS_SAVE {}",extras.size());

    }


    public void initContact() {
        List<Contact> contacts = new ArrayList<>();

        this.activityRepository.findAll().forEach(activityItem ->
        {
            for (int i = 0; i < (int) (Math.random() * 5); i++) {
                final Contact contact = new Contact();
                contact.setName(FAKER.name().name());
                contact.setPhone(FAKER.phoneNumber().phoneNumber());
                contact.setEmail(FAKER.internet().emailAddress());
                contact.setActivity(activityItem);
                contacts.add(contact);
            }
        });

        contactRepository.saveAll(contacts);
        log.info("CONTACTS_SAVE {}", contacts.size());
    }
}
