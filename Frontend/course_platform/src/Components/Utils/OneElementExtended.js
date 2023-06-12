import React, {useEffect, useState} from 'react'
import axios from 'axios';
import { useParams } from 'react-router';

const OneElementExtended = ({srcLink, ...props }) => {
    const {id} = useParams()
    const [course, setCourse] = useState([])

    useEffect(() => {
      axios.get(srcLink + id)
        .then((res)=> {
          setCourse(res.data)
        })
        .catch((res)=> {
          console.log("Error caught: " + res)
        })
    }, [id, srcLink])
    
    const parseData = (el) => {
      if (typeof(el) == 'object') {
        return JSON.stringify(el)
      } else {
        return el
      }
    }

    return <>
        <div className='extendedCourse'>
                {Object.entries(course).map((el, num) => (
                    <p key={num}>
                      <b>
                        {el[0]}: 
                      </b>
                      {parseData(el[1])}
                    </p>
                ))}
        </div>
    </>
};

export default OneElementExtended;