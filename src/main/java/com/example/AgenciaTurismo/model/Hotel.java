package com.example.AgenciaTurismo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hotel {
    private String hotelCode;
    private String hotelName;
    private String destination;
    private String roomType;
    private Integer priceForNight;
    private Date dateFrom;
    private Date dateTo;

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getPriceForNight() {
        return priceForNight;
    }

    public void setPriceForNight(Integer priceForNight) {
        this.priceForNight = priceForNight;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    private boolean reserved;

}
