package com.examenSPDO.reservation_service.mid;

import com.examenSPDO.reservation_service.dto.RoomResponse;

public interface IRoomClientHttp {
    RoomResponse getRoomById(Long roomId);
    void updateRoomAvailability(Long roomId, boolean available);
}

