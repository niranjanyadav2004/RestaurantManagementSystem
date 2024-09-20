package com.niranjan.DTOS;

import java.util.Date;

import com.niranjan.enums.ReservationStatus;

import lombok.Data;

@Data
public class ReservationDto {

private Long id;
	
	private String tableType;
	
	private String description;
	
	private Date dateTime;
	
	private ReservationStatus reservationStatus;
	
	private Long customerId;
	
	private String customerName;
	
}
