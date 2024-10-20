package com.web.model.entity;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long id;
	private String roomType;
	private BigDecimal roomPrice;
	private boolean isBooked = false;
	private String roomName;
	private String description;
	
	@Lob
	private Blob image;

	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<BookedRoom> bookedRooms = new ArrayList<>();

	public void addBooking(BookedRoom booking) {
		if (bookedRooms == null) {
			bookedRooms = new ArrayList<>();
		}
		bookedRooms.add(booking);
		booking.setRoom(this);
		isBooked = true;
		int bookingCode = (int)(Math.random() * 999999999 + 1000000000);
		booking.setConfirmCode(bookingCode);
	}
}
