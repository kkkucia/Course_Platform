import React from 'react';
// import OneLink from './Utils/OneLink';
import { Link } from "react-router-dom";
//import "../styles/Navbar.css";

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
            </ul>
          </label>
          <label>
            <h4>Views</h4>
            <ul>
              <li><Link to="/views/availableCourses">Available Course list</Link></li>
              <li><Link to="/views/invoices">Invoices list</Link></li>
            </ul>
          </label>
          <label>
            <h4>Procedures</h4>
            <ul>
              <li><Link to="/procedures/addCourse">Add course</Link></li>
            </ul>
          </label>
          <label>
            <h4>Functions</h4>
            <ul>
              <li></li>
            </ul>
          </label>  
      </nav>
    </>
  );
};

export default Navbar;
