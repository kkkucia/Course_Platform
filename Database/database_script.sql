-- WIDOKI

--widok dostępnych kursów
create or replace view available_courses_view
as
select c.TITLE,
       ca.CATEGORY_NAME,
       c.START_DATE,
       c.END_DATE,
       c.AVAILABLE_PLACES,
       c.MAX_NO_PLACES,
       c.PRICE
from course c
         inner join category ca on c.CATEGORY_FK = ca.ID
where c.AVAILABLE_PLACES > 0 and c.START_DATE > current_date;

--widok kategorii kursów
create or replace view categories_view
as
select c.CATEGORY_NAME,
       c.DESCRIPTION
from category c;

--widok wszystkich kursów
create or replace view courses_view
as
select c.TITLE,
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
select r.id,
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
select r.id,
       c.TITLE,
       c.PRICE,
       r.STATUS,
       p.FIRST_NAME,
       p.LAST_NAME
from reservation r
inner join participant p on r.PARTICIPANT_FK = p.ID
inner join course c on r.COURSE_FK = c.ID
where STATUS = 1;


--OBIEKT
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