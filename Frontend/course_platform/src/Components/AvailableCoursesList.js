import React, {useEffect, useState} from 'react'
import OneCourseSimple from './Utils/OneCourseSimple'
import axios from 'axios'

const AvailableCoursesList = () => {
  const [courses, setCourses] = useState([])

  useEffect(() => {
    axios.get('http://localhost:8080/courses/available')
      .then((res)=> {
        console.log(res.data)
        setCourses(res.data)
      })
      .catch((res)=> {
        console.log("Error caught: " + res)
      })
  }, [])

  return (
    <div>
      <h2>List of available courses:</h2>
      {courses.map(course => (
        <OneCourseSimple key={course[0]} data={course}></OneCourseSimple>
      ))}
    </div>
  )
}

export default AvailableCoursesList;