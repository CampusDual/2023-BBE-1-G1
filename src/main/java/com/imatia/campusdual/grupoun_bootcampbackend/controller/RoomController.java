package com.imatia.campusdual.grupoun_bootcampbackend.controller;

import com.imatia.campusdual.grupoun_bootcampbackend.model.dto.RoomDTO;
import com.imatia.campusdual.grupoun_bootcampbackend.service.RoomService;
import com.imatia.campusdual.grupoun_bootcampbackend.service.exception.InvalidAssignedHotelException;
import com.imatia.campusdual.grupoun_bootcampbackend.service.exception.InvalidRoomNumberException;
import com.imatia.campusdual.grupoun_bootcampbackend.service.exception.RoomDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, ?>> addRoom(@RequestBody RoomDTO roomDTO) {
        try {
            HashMap<String, Integer> responseBody = new HashMap<>();
            responseBody.put("insertedId", roomService.insertRoom(roomDTO));

            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("error", e.getMessage());

            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, ?>> deleteRoom(@RequestBody RoomDTO roomDTO) {
        try {
            Map<String, Integer> responseBody = new HashMap<>();
            responseBody.put("deletedId", roomService.deleteRoom(roomDTO));
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map <String,?>> updateRoom(@RequestBody RoomDTO roomDTO){

        try{
            Map<String,Integer> responsebody = new HashMap<>();
            responsebody.put("updatedId", roomService.updateRoom(roomDTO));
            return new ResponseEntity<>(responsebody, HttpStatus.OK);

        }catch(RoomDoesNotExistException e){
            Map<String,String> responseBody = new HashMap<>();
            responseBody.put("error", e.getMessage());
            return new ResponseEntity<>(responseBody,HttpStatus.NOT_FOUND);

        }catch (InvalidRoomNumberException | InvalidAssignedHotelException e){
            Map<String,String> responseBody = new HashMap<>();
            responseBody.put("error", e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }
    }


}