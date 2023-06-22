package com.ontimize.hr.model.core;

import com.ontimize.hr.api.core.service.exception.UserDoesNotExistException;
import com.ontimize.hr.model.core.dao.RoomDAO;
import com.ontimize.hr.model.core.dao.UserDAO;
import com.ontimize.hr.model.core.service.RoomService;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.server.dao.DefaultOntimizeDaoHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @InjectMocks
    RoomService roomService;
    @Mock
    DefaultOntimizeDaoHelper daoHelper;
    @Mock
    RoomDAO roomDAO;

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class insertRoomTest{
        Map<Object,Object> attrMap = new HashMap<>();

        @Test
        void insertRoom_validRoom_roomIsSaved(){
            attrMap.put(RoomDAO.HOTEL_ID,1);
            attrMap.put(RoomDAO.ROOM_NUMBER,101);
            attrMap.put(RoomDAO.NUMBER_OF_BEDS,2);
            attrMap.put(RoomDAO.BASE_PRICE, 105);



           assertDoesNotThrow(()->roomService.roomInsert(attrMap));


        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class deleteRoomTest {

        Map<Object, Object> keymap = new HashMap<>();

        @Test
        void deleteRoom_validRoom_roomIsDeleted() {

            keymap.put(RoomDAO.ID, 1);

            EntityResult er = new EntityResultMapImpl();
            er.setCode(EntityResult.OPERATION_SUCCESSFUL_SHOW_MESSAGE);

            when(roomService.roomDelete(keymap)).thenReturn(er);
            assertDoesNotThrow(() -> roomService.roomDelete(keymap));

        }

        @Test
        void deleteRoom_invalidRoom_roomIsNotDeleted() {
            keymap.put(RoomDAO.ID, 1);

            EntityResult er = new EntityResultMapImpl();
            er.setCode(EntityResult.OPERATION_WRONG);
            er.setMessage("No rooms with this id");

            when(roomService.roomDelete(keymap)).thenReturn(er);

            EntityResult result = roomService.roomDelete(keymap);
            assertEquals(EntityResult.OPERATION_WRONG, result.getCode());
            assertEquals("Cannot delete this room", result.getMessage());
        }
    }


}
