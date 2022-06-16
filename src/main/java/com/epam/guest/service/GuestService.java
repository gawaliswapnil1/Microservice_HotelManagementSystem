package com.epam.guest.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.guest.exception.GuestNotFoundException;
import com.epam.guest.exception.NoDataFoundException;
import com.epam.guest.model.Guest;
import com.epam.guest.repository.GuestRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GuestService {

	@Autowired
	private GuestRepository guestRepository;

	public List<Guest> getAllGuests() {
		return guestRepository.findAll();
	}

	public Guest saveGuest(Guest guest) {
		return guestRepository.save(guest);
	}

	public Optional<Guest> findGuestById(Integer Id) {
		Optional<Guest> guest = guestRepository.findById(Id);

		if (guest.isEmpty()) {
			throw new GuestNotFoundException(Id);
		} else {
			return guest;
		}
	}

	public String deleteGuestById(Integer Id) {

		try {
			guestRepository.deleteById(Id);
			return "SUCCESS";
		} catch (NoDataFoundException ex) {
			log.debug("Data not found for ID " + ex.getMessage());
		}

		return "FAILED!!";
	}

	public Guest updateGuest(Integer Id, Guest guest) {

		Guest guestDB=new Guest();

		if (guestRepository.findById(Id).isPresent()) {
			
			guestDB = guestRepository.findById(Id).get();
			
			if (Objects.nonNull(guest.getAddress()) && !"".equalsIgnoreCase(guest.getAddress())) {
				guestDB.setAddress(guest.getAddress());
			}

			if (Objects.nonNull(guest.getDetails()) && !"".equalsIgnoreCase(guest.getDetails())) {
				guestDB.setDetails(guest.getDetails());
			}

			if (Objects.nonNull(guest.getEmail()) && !"".equalsIgnoreCase(guest.getEmail())) {
				guestDB.setEmail(guest.getEmail());
			}

			if (Objects.nonNull(guest.getFirstName()) && !"".equalsIgnoreCase(guest.getFirstName())) {
				guestDB.setFirstName(guest.getFirstName());
			}

			if (Objects.nonNull(guest.getLastName()) && !"".equalsIgnoreCase(guest.getLastName())) {
				guestDB.setLastName(guest.getLastName());
			}
		}
		return guestRepository.save(guestDB);
	}

	public Optional<Guest> findGuestByUserNameAndPassword(Guest guest) {
		
		return guestRepository.findGuestByUserNameAndPassword(guest.getFirstName(),guest.getPassword());
	}

}
