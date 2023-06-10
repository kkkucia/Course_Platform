import React from 'react';
// import OneLink from './Utils/OneLink';
import { Link } from "react-router-dom";
import "../Styles/Navbar.css";

const Navbar = () => {

  return (
    <>
      <nav >
          <Link to="/" >Home</Link>
          <label id='basic'>
            <h4>Basic lists</h4>
            <ul>
              <li><Link to="/basic/courses">All Courses list</Link></li>
              <li><Link to="/basic/categories">All Categories list</Link></li>
              <li><Link to="/basic/reservations">All Reservations list</Link></li>
              <li><Link to="/basic/participants">All Participants list</Link></li>
            </ul>
          </label>
          <label>
            <h4>Views</h4>
            <ul>
              <li><Link to="/views/availableCourses">Available Course list</Link></li>
              <li><Link to="/views/invoices">Invoices list</Link></li>
              <li><Link to="/views/cancelledReservations">Cancelled reservations list</Link></li>
            </ul>
          </label>
          <label>
            <h4>Procedures</h4>
            <ul>
              <li><Link to="/procedures/addCourse">Add course</Link></li>
              <li><Link to="/procedures/addMentorToCourse">Add mentor to course</Link></li>
              <li><Link to="/procedures/payForReservation">Pay for reservation</Link></li>
              <li><Link to="/procedures/payForParticipantReservations">Pay for participant reservations</Link></li>
              <li><Link to="/procedures/makeReservation">Make a reservation</Link></li>
            </ul>
          </label>
          <label>
            <h4>Functions</h4>
            <ul>
              <li><Link to="/functions/availableCoursesBetweenDates">Available courses between dates</Link></li>
              <li><Link to="/functions/availableCoursesBetweenDatesByCategory">Available courses between dates by category</Link></li>
              <li><Link to="/functions/getReservationsFromCourses">Reservations from course</Link></li>
            </ul>
          </label>  
      </nav>
    </>
  );
};

export default Navbar;
