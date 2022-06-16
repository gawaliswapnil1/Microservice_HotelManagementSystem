package com.epam.guest.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.guest.model.Guest;
import com.epam.guest.repository.GuestRepository;
import com.epam.guest.service.GuestService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestGuestService {

	@Autowired
	private GuestService guestService;

	@MockBean
	private GuestRepository guestRepository;
	
	Guest guestInput = Guest.builder().guestId(2)
			.firstName("Nilesh")
			.lastName("walake")
			.email("nilesh@gmail.com")
			.address("pune")
			.build();

	@BeforeEach
	void setUp() {
		when(guestRepository.save(any(Guest.class))).thenReturn(guestInput);
	}
	
	@AfterEach
	void tearDown() {
		guestRepository.deleteAll();
	}
	
	@Test
	void createGuest() {

		when(guestRepository.save(any(Guest.class))).thenReturn(guestInput);
		
		Guest savedGuest = guestService.saveGuest(guestInput);
		assertEquals(guestInput.getFirstName(), savedGuest.getFirstName());
	}

	
	void getAllGuests() {
		List<Guest> guestList=new ArrayList<>();
		
		guestService.saveGuest(Guest.builder().guestId(1)
				.firstName("Swapnil")
				.lastName("Gawali")
				.email("xyz@gmail.com")
				.address("Ahmednagar")
				.build());
		guestService.saveGuest(Guest.builder().guestId(3)
				.firstName("Amit")
				.lastName("Shinde")
				.email("AMit@gmail.com")
				.address("pune")
				.build());
		guestService.saveGuest(Guest.builder().guestId(2)
				.firstName("Nilesh")
				.lastName("walake")
				.email("nilesh@gmail.com")
				.address("pune")
				.build());
		
		List<Guest> guestOutput = guestService.getAllGuests();
		assertEquals(3,guestOutput.size());
	}

	@Test
	void findGuestById() {
		
		Guest savedGuest = guestService.saveGuest(guestInput);
		
		when(guestRepository.findById(savedGuest.getGuestId())).thenReturn(Optional.of(savedGuest));
		Optional<Guest> guestFound = guestService.findGuestById(savedGuest.getGuestId());
		assertEquals(guestInput.getFirstName(), guestFound.get().getFirstName());
	}

	@Test
	void deleteGuestById() {
		Guest savedGuest = guestService.saveGuest(guestInput);
		
		String isSuccessful = guestService.deleteGuestById(savedGuest.getGuestId());
		assertEquals("SUCCESS", isSuccessful);
	}

	@Test
	void updateGuest() {
		Guest savedGuest = guestService.saveGuest(guestInput);

		when(guestRepository.save(any(Guest.class))).thenReturn(savedGuest);
		savedGuest.setDetails("new details");
		Guest updateGuest = guestService.updateGuest(savedGuest.getGuestId(), savedGuest);

		assertEquals(guestInput.getDetails(), updateGuest.getDetails());

	}

}
