import React, {useEffect, useState} from 'react'
import OneCourseSimple from './Utils/OneCourseSimple'
import axios from 'axios'

const Courses = () => {
  const [courses, setCourses] = useState([])

  useEffect(() => {
    console.log("Fetching courses...")
    axios.get('http://localhost:8080/courses/available')
      .then((res)=> {
        setCourses(res.data)
        console.log("Got data -------------------------------------------")
        console.log(res.data)
        setCourses(res.data)
      })
      .catch((res)=> {
        console.log(res)
      })
  }, [])

  return (
    <div>
      <h2>List of courses:</h2>
      {courses.map(course => (
        <OneCourseSimple key={course[0]} data={course}></OneCourseSimple>
      ))}
    </div>
  )
}

export default Courses;