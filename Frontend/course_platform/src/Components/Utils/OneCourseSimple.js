import React from 'react';
import { Link } from "react-router-dom";

const OneCourseSimple = ({ data, ...props }) => {
    return <>
        <div className='simpleCourse'>
            <h4>
                {data.slice(0, 3).join(" ")}
            </h4>
        </div>
    </>
};

export default OneCourseSimple;