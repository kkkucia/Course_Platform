-- WIDOKI

--widok dostępnych kursów
create
or replace view available_courses_view
as
select c.ID,
       c.TITLE,
       ca.CATEGORY_NAME,
       c.START_DATE,
       c.END_DATE,
       c.AVAILABLE_PLACES,
       c.MAX_NO_PLACES,
       c.PRICE
from course c
         inner join category ca on c.CATEGORY_FK = ca.ID
where c.AVAILABLE_PLACES > 0
  and c.START_DATE > current_date;


--widok kategorii kursów
create
or replace view categories_view
as
select c.ID,
       c.CATEGORY_NAME,
       c.DESCRIPTION
from category c;


--widok wszystkich kursów
create
or replace view courses_view
as
select c.ID,
       c.TITLE,
       ca.CATEGORY_NAME,
       c.START_DATE,
       c.END_DATE,
       c.AVAILABLE_PLACES,
       c.MAX_NO_PLACES,
       c.PRICE
from course c
         inner join category ca on c.CATEGORY_FK = ca.ID;


--widok wszytstkich faktur
create
or replace view invoices_view
as
select i.ID,
       i.ALLPRICE,
       i.TRANSACTION_DATE,
       p.FIRST_NAME,
       p.LAST_NAME
from invoice i
         inner join RESERVATION r on i.ID = r.CONNECTED_RESERVATION_FK
         inner join PARTICIPANT P on P.ID = r.PARTICIPANT_FK;


--widok wszystkich rezerwacji
create
or replace view reservation_view
as
select r.ID,
       c.TITLE,
       c.PRICE,
       r.STATUS,
       p.FIRST_NAME,
       p.LAST_NAME
from reservation r
         inner join participant p on r.PARTICIPANT_FK = p.ID
         inner join course c on r.COURSE_FK = c.ID;


--widok anulowanych rezerwacji
create
or replace view canceled_reservation_view
as
select r.ID,
       c.TITLE,
       c.PRICE,
       r.STATUS,
       p.FIRST_NAME,
       p.LAST_NAME
from reservation r
         inner join participant p on r.PARTICIPANT_FK = p.ID
         inner join course c on r.COURSE_FK = c.ID
where STATUS = 1;


--widok wszystkich uczestników:
create
or replace view participant_view
as
select p.ID,
       p.FIRST_NAME,
       p.LAST_NAME,
       p.PHONE_NUMBER,
       p.EMAIL
from participant p;


--OBIEKTY

-- informacje o kursie
create
or replace type course_details as OBJECT
(
    id               number(19),
    title            varchar2(255 char),
    category_name    varchar2(255 char),
    start_date       date,
    end_date         date,
    available_places number(10),
    max_no_places    number(10),
    price            number(19, 2)
);

create
or replace type course_details_table is table of course_details;


-- informacje o rezerwacji
create
or replace type reservation_details as OBJECT
(
    reservation_id number(19),
    status         number(10),
    price          number(19, 2),
    course_id      number(19),
    course_title   varchar2(255 char),
    first_name     varchar2(255 char),
    last_name      varchar2(255 char)
);

create
or replace type reservation_details_table is table of reservation_details;


-- informacje o użytkowniku
create
or replace type user_details as OBJECT
(
    id           number(19),
    first_name   varchar2(255 char),
    last_name    varchar2(255 char),
    email        varchar2(255 char),
    phone_number varchar2(255 char)
);

create
or replace type user_details_table is table of user_details;


-- informacje o fakturze
create
or replace type invoice_details as OBJECT
(
    id    number(19),
    date  date,
    price number(19, 2)
);

create
or replace type invoice_details_table is table of invoice_details;


-- PROCEDURY kontroli argumentów

create
or replace procedure reservation_exist(r_ID RESERVATION.ID%type)
as
    tmp char(1);
begin
select 1
into tmp
from RESERVATION r
where r.ID = r_ID;
exception
    when NO_DATA_FOUND then
        raise_application_error(-20001, 'ERROR: Reservation not found');
end;


create
or replace procedure participant_exist(p_ID PARTICIPANT.ID%type)
as
    tmp char(1);
begin
select 1
into tmp
from PARTICIPANT p
where p.ID = p_ID;
exception
    when NO_DATA_FOUND then
        raise_application_error(-20001, 'ERROR: Participant not found');
end;


create
or replace procedure course_exist(c_ID COURSE.ID%type)
as
    tmp char(1);
begin
select 1
into tmp
from COURSE c
where c.ID = c_ID;
exception
    when NO_DATA_FOUND then
        raise_application_error(-20001, 'ERROR: Course not found');
end;


create
or replace procedure category_exist(c_ID CATEGORY.ID%type)
as
    tmp char(1);
begin
select 1
into tmp
from CATEGORY c
where c.ID = c_ID;
exception
    when NO_DATA_FOUND then
        raise_application_error(-20001, 'ERROR: Category not found');
end;


create
or replace procedure mentor_exist(m_ID MENTOR.ID%type)
as
    tmp char(1);
begin
select 1
into tmp
from MENTOR m
where m.ID = m_ID;
exception
    when NO_DATA_FOUND then
        raise_application_error(-20001, 'ERROR: Mentor not found');
end;


--FUNKCJE

-- lista kursów dostępnych (które zaczynają się i kończą pomiędzy podanymi datami, te które mają dostępne miejsca)
create
or replace function f_available_courses_on_time(start_date_course date, end_date_course date)
    return course_details_table
as
    result course_details_table;
begin
select course_details(
               acv.id,
               acv.title,
               acv.category_name,
               acv.start_date,
               acv.end_date,
               acv.available_places,
               acv.max_no_places,
               acv.price) bulk collect
into result
from available_courses_view acv
where acv.START_DATE >= start_date_course
  and acv.END_DATE <= end_date_course;

return result;
end;


-- lista kursów dla kategorii (dostępnych w konkretnych terminach i te które mają dostępne miejsca)
create
or replace function f_available_courses_by_category_on_time(
    start_date_course date,
    end_date_course date,
    category_id CATEGORY.ID%type)
    return course_details_table
as
    result course_details_table;
begin
    category_exist
(category_id);
select course_details(
               c.id,
               c.title,
               ca.category_name,
               c.start_date,
               c.end_date,
               c.available_places,
               c.max_no_places,
               c.price) bulk collect
into result
from course c
         inner join category ca on c.CATEGORY_FK = ca.ID
where c.START_DATE >= start_date_course
  and c.END_DATE <= end_date_course
  and ca.ID = category_id;

return result;
end;


-- dla danego kursu pokaż rezerwacje
create
or replace function f_reservations_from_course(course_id COURSE.ID%type)
    return reservation_details_table
as
    result reservation_details_table;
begin
    course_exist
(course_id);
select reservation_details(
               r.id,
               r.status,
               c.price,
               course_id,
               c.title,
               p.first_name,
               p.last_name) bulk collect
into result
from reservation r
         inner join course c on c.id = r.COURSE_FK
         inner join participant p on p.id = r.PARTICIPANT_FK
where c.ID = course_id;

return result;
end;


-- dla danego klienta pokaż wszystkie rezerwacje
create
or replace function f_reservations_for_participant(participant_id PARTICIPANT.ID%type)
    return reservation_details_table
as
    result reservation_details_table;
begin
    participant_exist
(participant_id);
select reservation_details(
               r.id,
               r.status,
               c.price,
               c.id,
               c.title,
               p.first_name,
               p.last_name) bulk collect
into result
from reservation r
         inner join course c on c.id = r.COURSE_FK
         inner join participant p on p.id = r.PARTICIPANT_FK
where p.id = participant_id;

return result;
end;


-- dla danego klienta pokaż nieopłacone rezerwacje
create
or replace function f_unpaid_reservations_for_participant(participant_id PARTICIPANT.ID%type)
    return reservation_details_table
as
    result reservation_details_table;
begin
    participant_exist
(participant_id);
select reservation_details(
               r.id,
               r.status,
               c.price,
               c.id,
               c.title,
               p.first_name,
               p.last_name) bulk collect
into result
from reservation r
         inner join course c on c.id = r.COURSE_FK
         inner join participant p on p.id = r.PARTICIPANT_FK
where p.id = participant_id
  and r.status = 2; --NEW

return result;
end;


-- podaj klientów/ kontakt do wszystkich klientów danego kursu
create
or replace function f_participants_from_course(course_id COURSE.ID%type)
    return user_details_table
as
    result user_details_table;
begin
    course_exist
(course_id);
select user_details(
               p.id,
               p.first_name,
               p.last_name,
               p.email,
               p.phone_number) bulk collect
into result
from participant p
         inner join reservation r on p.id = r.PARTICIPANT_FK
         inner join course c on c.id = r.COURSE_FK
where c.ID = course_id;

return result;
end;


-- podaj kontakt do mentora/ mentorów danego kursu
create
or replace function f_mentors_from_course(course_id COURSE.ID%type)
    return user_details_table
as
    result user_details_table;
begin
    course_exist
(course_id);
select user_details(
               m.id,
               m.first_name,
               m.last_name,
               m.email,
               m.phone_number) bulk collect
into result
from mentor m
         inner join MENTOR_COURSE mc on mc.MENTORS_ID = m.ID
         inner join course c on mc.COURSES_ID = c.id
where c.ID = course_id;

return result;
end;


-- pokaż faktury danego klienta
create
or replace function f_invoices_for_participant(participant_id PARTICIPANT.ID%type)
    return invoice_details_table
as
    result invoice_details_table;
begin
    participant_exist
(participant_id);
select invoice_details(
               i.id,
               i.transaction_date,
               i.allPrice) bulk collect
into result
from invoice i
         inner join reservation r on i.id = r.CONNECTED_RESERVATION_FK
         inner join participant p on p.id = r.PARTICIPANT_FK
where p.id = participant_id;

return result;
end;


-- daj sume nieopłaconych rezerwacji dla danego klienta
create
or replace function f_amount_to_pay_for_participant(participant_id PARTICIPANT.ID%type)
    return INVOICE.ALLPRICE%TYPE
as
    total_amount INVOICE.ALLPRICE%TYPE := 0;
begin
    participant_exist
(participant_id);

select sum(c.price)
into total_amount

from reservation r
         inner join course c on c.id = r.COURSE_FK
where r.PARTICIPANT_FK = participant_id
  and r.status = 2 --NEW
group by r.PARTICIPANT_FK;

return total_amount;
end;


-- TRIGGERY

--zabronione usuwanie rezerwacji
create
or replace trigger tr_forbidden_remove_reservation
    before delete
on RESERVATION
    for each row
begin
    raise_application_error
(-20001, 'ERROR: Removing reservations is forbidden.');
end;


--zabronione robienie rezerwacji na nieistniejący kurs, przez nieistniejącego klienta i robienie rezerwacji w przeszłości
create
or replace trigger tr_forbidden_make_reservation
    before insert
    on RESERVATION
    for each row
declare
start_date_course COURSE.start_date%type;
begin
    course_exist
(:new.COURSE_FK);
    participant_exist
(:new.PARTICIPANT_FK);

select c.start_date
into start_date_course
from COURSE c
where c.ID = :new.COURSE_FK;

if
start_date_course < current_date then
        raise_application_error(-20001, 'ERROR: Reservation for this course is not available.');
end if;
end;


-- zmiana miejsc na kursie  (po zmianie statusu w rezerwacji)
create or replace trigger TR_CHANGE_PLACES_RESERVATION
    before insert or update of STATUS
    on RESERVATION
    for each row
declare
    available_places_course COURSE.AVAILABLE_PLACES%type;
    places                  COURSE.AVAILABLE_PLACES%type;

begin
    select c.AVAILABLE_PLACES
    into available_places_course
    from COURSE c
    where c.ID = :new.COURSE_FK;

    if available_places_course <= 0 and :new.status = 2 then
        raise_application_error(-20001, 'ERROR: Course is fully booked');
    end if;

    if :new.status = 1 --REJECTED
    then
        places := 1;
    elsif :new.status = 2 --NEW
    then
        places := -1;
    else --PAID
        places := 0;
    end if;

    update COURSE c
    set c.AVAILABLE_PLACES = available_places_course + places
    where :new.COURSE_FK = c.ID;
end;


-- aktualizacja liczby dostępnych miejsc na kursie  (po zmianie maksymalnej liczby miejsc)

--funkcja potrzebna triggerowi do zmainy available_places
create
or replace function f_update_available_places(new_max_places COURSE.MAX_NO_PLACES%type,
                                                     old_max_places COURSE.MAX_NO_PLACES%type,
                                                     p_old_available_places COURSE.AVAILABLE_PLACES%type)
    return COURSE.AVAILABLE_PLACES%type
    is
    new_available_places COURSE.AVAILABLE_PLACES%type;
begin
    if
new_max_places < 0 then
        raise_application_error(-20001, 'ERROR: Number of places cannot be less than zero.');
end if;

    new_available_places
:= p_old_available_places + (new_max_places - old_max_places);

    if
new_available_places < 0 then
        raise_application_error(-20001, 'ERROR: Number of available places cannot be less than zero.');
end if;

return new_available_places;
end;


--trigger wyłąpujący zmiane max_no_places
create
or replace trigger tr_modify_no_places
    before
update of max_no_places
on COURSE
    for each row
declare
new_available_places COURSE.AVAILABLE_PLACES%type;
begin
    new_available_places
:= f_update_available_places(:new.max_no_places, :old.max_no_places, :old.AVAILABLE_PLACES);

    :new
.AVAILABLE_PLACES := new_available_places;
end;

-- PROCEDURY

-- dodaj log do tablicy log
create
or replace PROCEDURE add_log_reservation(new_status LOG_TABLE.STATUS%type,
    reservation_id LOG_TABLE.RESERVATION_FK%type)
AS
    log_id LOG_TABLE.ID%type;
BEGIN

SELECT LOG_SEQ.NEXTVAL
INTO log_id
FROM dual;

insert into LOG_TABLE (ID, log_date, status, reservation_fk)
values (log_id, current_date, new_status, reservation_id);
commit;
exception
    when others then
        rollback;
        raise;
end;

-- zarezerwuj miejsce na kursie dla danej osoby(stwórz rezerwacje)
create
or replace procedure make_reservation(
    course_id course.id%type,
    participant_id participant.id%type
)
as
    reservation_id reservation.id%type;
begin
    course_exist
(course_id);
    participant_exist
(participant_id);

SELECT reservation_seq.NEXTVAL
INTO reservation_id
FROM dual;

insert into reservation (ID, STATUS, COURSE_FK, PARTICIPANT_FK)
values (reservation_id, 2, course_id, participant_id);

ADD_LOG_RESERVATION
(2, reservation_id);

commit;
exception
    when others then
        rollback;
        raise;
end;

-- zapłać za konkretną rezerwacje (stworzy fakture dla pojedyńczej rezerwacji)
create PROCEDURE pay_for_reservation(reservation_id IN RESERVATION.ID%TYPE, payment_type INVOICE.TYPE%type)
AS
    course_price       COURSE.PRICE%TYPE;
    invoice_id         INVOICE.ID%TYPE;
    reservation_status RESERVATION.STATUS%TYPE;
BEGIN
    reservation_exist(reservation_id);

    SELECT c.PRICE, r.STATUS
    INTO course_price, reservation_status
    FROM RESERVATION r
             INNER JOIN COURSE c on c.ID = r.COURSE_FK
    WHERE r.ID = reservation_id;

    if reservation_status != 2 then
        raise_application_error(-20001, 'ERROR: This reservation was already paid.');
    end if;

    SELECT invoice_seq.NEXTVAL INTO invoice_id FROM dual;

    INSERT INTO INVOICE (ID, ALLPRICE, TRANSACTION_DATE, "TYPE")
    VALUES (invoice_id, course_price, current_date, payment_type);

    update RESERVATION r
    set r.STATUS = 0, --PAID
     r.CONNECTED_RESERVATION_FK = invoice_id
    where reservation_id = r.ID;

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        raise;
END;


-- przypisz kurs do danej kategorii
create or replace procedure add_course_to_category(
    course_id course.id%type,
    category_id CATEGORY.id%type
)
as
begin
    course_exist(course_id);
    CATEGORY_EXIST(category_id);

    UPDATE COURSE c
    SET c.CATEGORY_FK = category_id
    WHERE c.ID = course_id;

    commit;
exception
    when others then
        rollback;
        raise;
end;


--stwórz podsumowanie paragonu - główna 'invoice'
create PROCEDURE make_main_invoice(
    invoice_price INVOICE.ALLPRICE%TYPE,
    payment_type INVOICE.TYPE%type)
AS
    invoice_id         INVOICE.ID%TYPE;
BEGIN

    SELECT invoice_seq.NEXTVAL INTO invoice_id FROM dual;

    INSERT INTO INVOICE (ID, ALLPRICE, TRANSACTION_DATE, "TYPE")
    VALUES (invoice_id, invoice_price, current_date, payment_type);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        raise;
END;


-- zapłać za wszystkie nieopłacone kursy dla danego klienta(stwórz fakture)
create PROCEDURE pay_for_all_unpaid_reservations(participant_id IN PARTICIPANT.ID%TYPE, payment_type INVOICE.TYPE%TYPE)
AS
  reservation_id RESERVATION.ID%TYPE;
  invoice_price  INVOICE.ALLPRICE%TYPE;
BEGIN
    invoice_price := f_amount_to_pay_for_participant(participant_id);

  FOR rec IN (SELECT r.ID
              FROM RESERVATION r
              INNER JOIN COURSE c ON c.ID = r.COURSE_FK
              WHERE r.STATUS = 2
              AND r.PARTICIPANT_FK = participant_id)
  LOOP
    reservation_id := rec.ID;
    pay_for_reservation(reservation_id, payment_type);
  END LOOP;

  COMMIT;
    make_main_invoice(invoice_price, payment_type );
    COMMIT;

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;

--Odrzuć daną prezentacje  (ustawia status rezerwacji na anulowany)
create or replace PROCEDURE cancel_reservation(reservation_id IN RESERVATION.ID%TYPE)
AS
    reservation_status RESERVATION.STATUS%TYPE;
BEGIN
    reservation_exist(reservation_id);

    SELECT r.STATUS
    INTO reservation_status
    FROM RESERVATION r
    WHERE r.ID = reservation_id;

    if reservation_status != 2 then
        raise_application_error(-20001, 'ERROR: This reservation was already paid or canceled.');
    end if;

    update RESERVATION r
    set r.STATUS = 1 --REJECTED
    where reservation_id = r.ID;

    ADD_LOG_RESERVATION(1, reservation_id);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        raise;
END;


-- dodaj mentora do danego kursu
create
or replace procedure add_mentor_to_course(
    add_course_id course.id%type,
    add_mentor_id mentor.id%type
)
as
begin
    course_exist
(add_course_id);
    mentor_exist
(add_mentor_id);

insert into MENTOR_COURSE(mentors_id, courses_id)
values (add_mentor_id, add_course_id);
commit;
exception
    when others then
        rollback;
        raise;
end;


-- sekwencje
CREATE SEQUENCE reservation_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE invoice_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE log_seq
    START WITH 1
    INCREMENT BY 1;
