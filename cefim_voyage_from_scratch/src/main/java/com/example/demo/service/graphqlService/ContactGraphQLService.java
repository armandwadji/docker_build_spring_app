package com.example.demo.service.graphqlService;

import com.example.demo.dto.ContactDTO;
import com.example.demo.entity.Activity;
import com.example.demo.entity.Contact;
import com.example.demo.exception.ActivityException;
import com.example.demo.exception.ContactException;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service @AllArgsConstructor
public class ContactGraphQLService {

    private ContactRepository contactRepository;
    private ActivityRepository activityRepository;

    public Contact contact(int id) throws ContactException {return contactRepository.findById(id).orElseThrow(() -> new ContactException("Contact Not Found", NOT_FOUND));}

    public Contact create(int activityId, ContactDTO contactDTO) throws ActivityException {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ActivityException("Activity Not Found", NOT_FOUND));
        Contact contact = Contact.builder()
                .name(contactDTO.getName())
                .email(contactDTO.getEmail())
                .phone(contactDTO.getPhone())
                .activity(activity)
                .build();

        return contactRepository.save(contact);
    }

    public Contact update(int id, ContactDTO contactDTO) throws ContactException {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ContactException("Contact Not Found", NOT_FOUND));
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contact.getPhone());

        return contactRepository.save(contact);
    }

    public String delete(int id) throws ContactException {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ContactException("Contact Not Found", NOT_FOUND));
        contactRepository.delete(contact);

        return "Contact delete Successfully";
    }
}
