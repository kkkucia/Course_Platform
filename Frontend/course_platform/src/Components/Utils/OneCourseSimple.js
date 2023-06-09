import React from 'react';

const OneCourseSimple = ({ data, ...props }) => {
    return <>
        <div key={data[0]} className='simpleCourse'>
            <h5>
                {Object.values(data).join(" ")}
            </h5>
        </div>
    </>
};

export default OneCourseSimple;