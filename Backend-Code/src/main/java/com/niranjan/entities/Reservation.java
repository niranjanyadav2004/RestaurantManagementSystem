package com.niranjan.entities;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.niranjan.DTOS.ReservationDto;
import com.niranjan.enums.ReservationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String tableType;
	
	private String description;
	
	private Date dateTime;
	
	private ReservationStatus reservationStatus;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "user_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;
	
	public ReservationDto getReservationDto() {
		ReservationDto reservationDto = new ReservationDto();
		reservationDto.setId(id);
		reservationDto.setTableType(tableType);
		reservationDto.setReservationStatus(reservationStatus);
		reservationDto.setDescription(description);
		reservationDto.setDateTime(dateTime);
		reservationDto.setCustomerId(user.getId());
		reservationDto.setCustomerName(user.getName());
		
		return reservationDto;
	}
	
}
