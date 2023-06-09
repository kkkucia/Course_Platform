import React, { useEffect, useState } from 'react'
import axios from 'axios';
import { useParams } from 'react-router';
import "../../Styles/OneElementExtended.css"

const OneElementExtended = ({ srcLink, text, ...props }) => {
  const { id } = useParams()
  const [course, setCourse] = useState([])

  useEffect(() => {
    axios.get(srcLink + id)
      .then((res) => {
        setCourse(res.data)
      })
      .catch((res) => {
        console.log("Error caught while fetching extended data for record: " + res)
        setCourse({})
      })
  }, [id, srcLink])

  const displayData = (data, key) => {
    if (data === null) {
      return "Not defined"
    }
    if (typeof (data) == 'object') {
      if (Array.isArray(data)) {
        if (data.length === 0) {
          return "[]"
        }
        return <ul>
          {data.map((el, num) => (
            <li key={key + "-" + num}>
              <b>{num}: </b>
              {displayData(el, key + "-" + num)}
            </li>
          ))}
        </ul>
      } else {
        return <ul>
          {Object.entries(data).map((el, num) => (
            <li key={key + "-" + num}>
              <b>{el[0]}: </b>
              {displayData(el[1], key + "-" + num)}
            </li>
          ))}
        </ul>
      }
    } else {
      return data
    }
  }

  return <>
    <div className='extendedCourse'>
      <h3>{text}</h3>
      <ul>
        {Object.entries(course).map((el, num) => (
          <li key={num}>
            <b>
              {el[0]}:
            </b>
            {displayData(el[1], num)}
          </li>
        ))}
      </ul>
    </div>
  </>
};

export default OneElementExtended;