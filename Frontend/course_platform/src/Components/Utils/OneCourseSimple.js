import React from 'react';
import { Link } from "react-router-dom";

const OneCourseSimple = ({ data, ...props }) => {
    return <>
        <div key={data[0]} className='simpleCourse'>
            <h4>
                {Object.values(data).join(" ")}
            </h4>
        </div>
    </>
};

export default OneCourseSimple;