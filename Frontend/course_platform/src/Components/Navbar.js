import React from 'react';
import OneLink from './Utils/OneLink';
import { Link } from "react-router-dom";
//import "../styles/Navbar.css";

const Navbar = () => {

  return (
    <>
      <nav >
          <Link to="/" >Home</Link>
          <Link to="/courses" >Course list</Link>

          {/* <OneLink route="/coś tam" label="" /> */}
         
      </nav>
    </>
  );
};

export default Navbar;
