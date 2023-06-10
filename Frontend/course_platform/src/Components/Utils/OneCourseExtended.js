import React, {useEffect, useState} from 'react'
import axios from 'axios';
import { useParams } from 'react-router';

const OneCourseExtended = ({ ...props }) => {
    const {id} = useParams()
    const [course, setCourse] = useState([])

    useEffect(() => {
      axios.get('http://localhost:8080/courses/id/' + id)
        .then((res)=> {
          setCourse(res.data)
        })
        .catch((res)=> {
          console.log("Error caught: " + res)
        })
    }, [id])

    return <>
        <div className='extendedCourse'>
                {Object.entries(course).map((el, num) => (
                    <h2 key={num}>
                        {el.join(": ")}
                    </h2>
                ))}
        </div>
    </>
};

export default OneCourseExtended;