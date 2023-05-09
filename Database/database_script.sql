-- WIDOKI

--widok dostępnych kursów
create or replace view available_courses_view
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
create or replace view categories_view
as
select c.ID,
       c.CATEGORY_NAME,
       c.DESCRIPTION
from category c;

--widok wszystkich kursów
create or replace view courses_view
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
create or replace view invoices_view
as
select i.ID,
       i.ALLPRICE,
       i.TRANSACTION_DATE
from invoice i;

--widok wszystkich rezerwacji
create or replace view reservation_view
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
create or replace view canceled_reservation_view
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
create or replace view participant_view
as
select p.ID,
       p.FIRST_NAME,
       p.LAST_NAME,
       p.PHONE_NUMBER,
       p.EMAIL
from participant p;


--OBIEKTY

-- informacje o kursie
create or replace type course_details as OBJECT
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

create or replace type course_details_table is table of course_details;

-- informacje o rezerwacji
create or replace type reservation_details as OBJECT
(
    reservation_id number(19),
    status         number(10),
    price          number(19, 2),
    course_id      number(19),
    course_title   varchar2(255 char),
    first_name     varchar2(255 char),
    last_name      varchar2(255 char)
);

create or replace type reservation_details_table is table of reservation_details;

-- informacje o użytkowniku
create or replace type user_details as OBJECT
(
    id           number(19),
    first_name   varchar2(255 char),
    last_name    varchar2(255 char),
    email        varchar2(255 char),
    phone_number varchar2(255 char)
);

create or replace type user_details_table is table of user_details;

-- informacje o fakturze
create or replace type invoice_details as OBJECT
(
    id    number(19),
    date  date,
    price number(19, 2)
);

create or replace type invoice_details_table is table of invoice_details;


-- PROCEDURY kontroli argumentów

create or replace procedure reservation_exist(r_ID RESERVATION.ID%type)
as
    tmp char(1);
begin
    select 1 into tmp from RESERVATION r where r.ID = r_ID;
exception
    when NO_DATA_FOUND then
        raise_application_error(-20001, 'ERROR: Reservation not found');
end;


create or replace procedure participant_exist(p_ID PARTICIPANT.ID%type)
as
    tmp char(1);
begin
    select 1 into tmp from PARTICIPANT p where p.ID = p_ID;
exception
    when NO_DATA_FOUND then
        raise_application_error(-20001, 'ERROR: Participant not found');
end;


create or replace procedure course_exist(c_ID COURSE.ID%type)
as
    tmp char(1);
begin
    select 1 into tmp from COURSE c where c.ID = c_ID;
exception
    when NO_DATA_FOUND then
        raise_application_error(-20001, 'ERROR: Course not found');
end;


create or replace procedure category_exist(c_ID CATEGORY.ID%type)
as
    tmp char(1);
begin
    select 1 into tmp from CATEGORY c where c.ID = c_ID;
exception
    when NO_DATA_FOUND then
        raise_application_error(-20001, 'ERROR: Category not found');
end;


create or replace procedure mentor_exist(m_ID MENTOR.ID%type)
as
    tmp char(1);
begin
    select 1 into tmp from MENTOR m where m.ID = m_ID;
exception
    when NO_DATA_FOUND then
        raise_application_error(-20001, 'ERROR: Mentor not found');
end;


--FUNKCJE

-- lista kursów dostępnych (które zaczynają się i kończą pomiędzy podanymi datami, te które mają dostępne miejsca)
create or replace function f_available_courses_on_time(start_date_course date, end_date_course date)
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
create or replace function f_available_courses_by_category_on_time(
    start_date_course date,
    end_date_course date,
    category_id CATEGORY.ID%type)
    return course_details_table
as
    result course_details_table;
begin
    category_exist(category_id);
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
create or replace function f_reservations_from_course(course_id COURSE.ID%type)
    return reservation_details_table
as
    result reservation_details_table;
begin
    course_exist(course_id);
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
create or replace function f_reservations_for_participant(participant_id PARTICIPANT.ID%type)
    return reservation_details_table
as
    result reservation_details_table;
begin
    participant_exist(participant_id);
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
create or replace function f_unpaid_reservations_for_participant(participant_id PARTICIPANT.ID%type)
    return reservation_details_table
as
    result reservation_details_table;
begin
    participant_exist(participant_id);
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
      and (r.status = 1 or r.status = 2);

    return result;
end;

-- podaj klientów/ kontakt do wszystkich klientów danego kursu
create or replace function f_participants_from_course(course_id COURSE.ID%type)
    return user_details_table
as
    result user_details_table;
begin
    course_exist(course_id);
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
create or replace function f_mentors_from_course(course_id COURSE.ID%type)
    return user_details_table
as
    result user_details_table;
begin
    course_exist(course_id);
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
create or replace function f_invoices_for_participant(participant_id PARTICIPANT.ID%type)
    return invoice_details_table
as
    result invoice_details_table;
begin
    participant_exist(participant_id);
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


