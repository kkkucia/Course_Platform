import React from 'react';
import { Link } from "react-router-dom";

const OneLink = ({ route, label, ...props }) => {
    <>
        <Link to={route} >
        {label}
        </Link>
    </>
};

export default OneLink;