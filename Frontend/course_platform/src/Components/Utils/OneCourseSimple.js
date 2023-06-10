import React from 'react';
import { useNavigate } from 'react-router-dom';

const OneCourseSimple = ({ data, ...props }) => {
    const navigate = useNavigate()
    return <>
        <div key={data[0]} className='simpleCourse' onClick={() => navigate("/basic/courses/" + data.id)}>
            <h5>
                {Object.values(data).splice(0, 3).join(" ")}
            </h5>
        </div>
    </>
};

export default OneCourseSimple;