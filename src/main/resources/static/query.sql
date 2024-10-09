use hotel_booking;
select h.hotel_id, h.name as hotelName, h.address, c.name as cityName, r.room_id, r.room_number, r.type, r.price, b.check_in_date, b.check_out_date
from hotels h
join cities c on h.city_id = c.city_id
join rooms r on r.hotel_id = h.hotel_id
left join bookings b on r.room_id = b.room_id
where c.name = 'Hà Nội' and r.availability_status = 'AVAILABLE'
and (b.check_out_date <= '2024-10-10' or b.check_in_date >= '2024-10-15');

select h.hotel_id, h.name as hotelName, h.address, r.room_id, r.room_number, r.type, r.price
FROM hotels h
JOIN rooms r ON h.hotel_id = r.hotel_id
WHERE h.city_id = 1
AND r.availability_status = 'AVAILABLE'
    AND NOT EXISTS (
    SELECT 1
    FROM bookings b
    WHERE b.room_id = r.room_id
      AND b.check_in_date <= '2024-10-15T14:00:00'
      AND b.check_out_date >= '2024-10-10T14:00:00'
);